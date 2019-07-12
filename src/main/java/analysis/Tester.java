package analysis;

import filters.DirectorsFilter;
import filters.GenreFilter;
import filters.MinutesFilter;
import filters.YearAfterFilter;
import initialization.FirstRatings;
import initialization.SecondRatings;
import initialization.ThirdRatings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Rating;
import web.RecommendationRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author abrar
 * since 6/15/2019
 */

public class Tester {
    static Logger log = LoggerFactory.getLogger(Tester.class);
    static String RATED_MOVIES_SHORT = "ratedmovies_short.csv";
    static String RATED_MOVIES_FULL = "ratedmoviesfull.csv";
    static String RATINGS_SHORT = "ratings_short.csv";
    static String RATINGS_FULL = "ratings.csv";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            //testRatingSorting();
            //testFirstRatings();
            //testSecondRatings();
            //testMovieRunnerAverage();
            //testHashMapForNull();
            //testThirdRatings();
            //testMovieRunnerWithFilters();
            testMovieRunnerSimilarRatings();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.toString());
            System.exit(-1);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("\n" + "Time Taken For Completion = " + (endTime - startTime) + " ms");
        log.info("\n" + "Time Taken For Completion = " + (endTime - startTime) + " ms");
        System.exit(0);
    }

    public static void testRatingSorting() {
        Rating rating1 = new Rating("10", 2);
        Rating rating2 = new Rating("11", 5);
        Rating rating3 = new Rating("12", 1);
        ArrayList<Rating> ratings = rating1.keepInArrayListWith(rating2, rating3);
        System.out.println("Unsorted: " + ratings);
        Collections.sort(ratings);
        /*Collections.sort() method always sorts in ASC order*/
        System.out.println("Sorted Ascending: " + ratings);
    }

    private static void testFirstRatings() throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.testLoadMovies(RATED_MOVIES_FULL);
        firstRatings.testLoadRaters(RATINGS_FULL);
    }

    private static void testSecondRatings() throws IOException {
        SecondRatings secondRatings = new SecondRatings(RATED_MOVIES_SHORT, RATINGS_SHORT);
        System.out.println("myMovies Size: " + secondRatings.getMovieSize());
        System.out.println("myRaters Size: " + secondRatings.getRaterSize());
    }

    private static void testMovieRunnerAverage() throws IOException {
        String desiredMovieTitle = "Vacation";
        MovieRunnerAverage movieRunnerAverage = new MovieRunnerAverage();
        movieRunnerAverage.printAverageRatings(RATED_MOVIES_FULL, RATINGS_FULL);
        movieRunnerAverage.getAverageRatingOneMovie(RATED_MOVIES_FULL, RATINGS_FULL, desiredMovieTitle);
    }

    private static void testThirdRatings() throws IOException {
        ThirdRatings thirdRatings = new ThirdRatings(RATINGS_SHORT);
        System.out.println("myRaters Size: " + thirdRatings.getRaterSize());
    }

    private static void testMovieRunnerWithFilters() throws IOException {
        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters(RATED_MOVIES_FULL, RATINGS_FULL,
                3);
        movieRunnerWithFilters.printAverageRatings();
        movieRunnerWithFilters.printAverageRatingsByYear(2000);
        movieRunnerWithFilters.printAverageRatingsByGenre("Comedy");
        movieRunnerWithFilters.printAverageRatingsByMinutes(105, 135);
        movieRunnerWithFilters.printAverageRatingsByDirectors("Clint Eastwood,Joel Coen,Martin " +
                "Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        movieRunnerWithFilters.printAverageRatingsByYearAfterAndGenre(1990, "Drama");
        movieRunnerWithFilters.printAverageRatingsByMinutesAndDirectors(90, 180,
                "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
    }

    private static void testMovieRunnerSimilarRatings() throws IOException {
        MovieRunnerSimilarRatings movieRunnerSimilarRatings = new
                MovieRunnerSimilarRatings(RATED_MOVIES_FULL, RATINGS_FULL);
        movieRunnerSimilarRatings.printSimilarRatings("71", 20, 5);
        movieRunnerSimilarRatings.printSimilarRatingsByGenre("964", 20,
                new GenreFilter("Mystery"), 5);
        movieRunnerSimilarRatings.printSimilarRatingsByDirector("120", 10,
                new DirectorsFilter
                        ("Clint Eastwood,J.J. Abrams,Alfred Hitchcock," +
                                "Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"), 2);
        movieRunnerSimilarRatings.printSimilarRatingsByGenreAndMinutes("168", 10,
                new GenreFilter("Drama"), new MinutesFilter(80,160), 3);
        movieRunnerSimilarRatings.printSimilarRatingsByYearAfterAndMinutes("314", 10,
                new YearAfterFilter(1975), new MinutesFilter(70, 200), 5);
        RecommendationRunner recommendationRunner = new RecommendationRunner();
        movieRunnerSimilarRatings.printSimilarRatingsFromSource(recommendationRunner.getItemsToRate(),
                "1020", 10, 1);
    }

    private static void testHashMapForNull() {
        HashMap<String, Rating> test = new HashMap<String, Rating>();
        if (test.get("Test") != null) {
            System.out.println(test.get("Test") + " is not null");
        } else {
            System.out.println(test.get("Test") + " is null");
        }
    }
}
