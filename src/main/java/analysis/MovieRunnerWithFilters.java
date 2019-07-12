package analysis;

import filters.*;
import initialization.ThirdRatings;
import pojo.Rating;
import pojo.database.MovieDatabase;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author abrar
 * since 6/19/2019
 */

public class MovieRunnerWithFilters {

    ThirdRatings thirdRatings;
    private int minimalRatings;
    private String moviesFileName;
    private String ratingsFileName;

    public MovieRunnerWithFilters(String moviesFileName, String ratingsFileName, int minimalRatings) throws IOException {
        thirdRatings = new ThirdRatings(ratingsFileName);
        //initialize the movie db static HashMap containing movies against movieId
        MovieDatabase.initialize(moviesFileName);
        this.moviesFileName = moviesFileName;
        this.ratingsFileName = ratingsFileName;
        this.minimalRatings = minimalRatings;
    }

    public void printAverageRatings() {
        System.out.println("myMovies Size: " + MovieDatabase.size());
        System.out.println("myRaters Size: " + thirdRatings.getRaterSize());
        //for printing the ratings followed by the movies vs their average ratings
        ArrayList<Rating> allAverageRatings = thirdRatings.getAverageRatings(minimalRatings);
        System.out.println("\n" + "All ratings(" + allAverageRatings.size() + " in size) which have at least " +
                minimalRatings + " raters are: ");
        //System.out.println(allAverageRatings);
        //sorting in ascending order
        Collections.sort(allAverageRatings);
        System.out.println("Unfiltered Results: ");
        for (Rating currentRating : allAverageRatings) {
            String currentMovieId = currentRating.getItem();
            String currentMovieTitle = MovieDatabase.getTitle(currentMovieId);
            System.out.println(currentRating.getValue() + " " + currentMovieTitle);
        }
    }

    public void printAverageRatingsByYear(int desiredYear) {
        //for printing the filtered ratings followed by the movies vs their average ratings
        ArrayList<Rating> filteredAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings,
                new YearAfterFilter(desiredYear));
        System.out.println("\n" + "All ratings(" + filteredAverageRatings.size() + " in size) which have at least " +
                minimalRatings + " raters are: ");
        //System.out.println(filteredAverageRatings);
        //sorting in ascending order
        Collections.sort(filteredAverageRatings);
        System.out.println("Filtered Results with year " + desiredYear + ":");
        for (Rating currentRating : filteredAverageRatings) {
            String currentMovieId = currentRating.getItem();
            String currentMovieTitle = MovieDatabase.getTitle(currentMovieId);
            int year = MovieDatabase.getYear(currentMovieId);
            System.out.println(currentRating.getValue() + " " + year + " " + currentMovieTitle);
        }
    }

    public void printAverageRatingsByGenre(String desiredGenre) {
        ArrayList<Rating> filteredByGenreAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings,
                new GenreFilter(desiredGenre));
        System.out.println("\n" + "All ratings(" + filteredByGenreAverageRatings.size()
                + " in size) which have at least " + minimalRatings + " raters are: ");
        //System.out.println(filteredByGenreAverageRatings);
        System.out.println("Filtered Results By Genre:" + "\n");
        Collections.sort(filteredByGenreAverageRatings);
        for (Rating currentRating : filteredByGenreAverageRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(currentRating.getValue() + " " + MovieDatabase.getTitle(currentMovieId));
            System.out.println("\t" + MovieDatabase.getGenres(currentMovieId) + "\n");
        }
    }

    public void printAverageRatingsByMinutes(int minMinutes, int maxMinutes) {
        ArrayList<Rating> filteredByMinutesAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings,
                new MinutesFilter(minMinutes, maxMinutes));
        System.out.println("\n" + "All ratings(" + filteredByMinutesAverageRatings.size()
                + " in size) which have at least " + minimalRatings + " raters are: ");
        //System.out.println(filteredByMinutesAverageRatings);
        System.out.println("Filtered Results By Minutes:");
        Collections.sort(filteredByMinutesAverageRatings);
        for (Rating currentRating : filteredByMinutesAverageRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(currentRating.getValue() + " Time: " + MovieDatabase.getMinutes(currentMovieId) +
                    " minutes" + MovieDatabase.getTitle(currentMovieId));
        }
    }

    public void printAverageRatingsByDirectors(String desiredDirectorNames) {
        ArrayList<Rating> filteredByDirectorsAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings,
                new DirectorsFilter(desiredDirectorNames));
        System.out.println("\n" + "All ratings(" + filteredByDirectorsAverageRatings.size()
                + " in size) which have at least " + minimalRatings + " raters are: ");
        //System.out.println(filteredByDirectorsAverageRatings);
        System.out.println("Filtered Results By Directors:" + "\n");
        Collections.sort(filteredByDirectorsAverageRatings);
        for (Rating currentRating : filteredByDirectorsAverageRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(currentRating.getValue() + " " + MovieDatabase.getTitle(currentMovieId));
            System.out.println("Directors: " + MovieDatabase.getDirector(currentMovieId) + "\n");
        }
    }

    public void printAverageRatingsByYearAfterAndGenre(int desiredYear, String desiredGenre) {
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(desiredYear));
        allFilters.addFilter(new GenreFilter(desiredGenre));
        ArrayList<Rating> filteredAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings, allFilters);
        System.out.println("\n" + "All ratings(" + filteredAverageRatings.size()
                + " in size) which have at least " + minimalRatings + " raters are: ");
        System.out.println("Filtered Results By Year and Genres :" + "\n");
        for (Rating currentRating : filteredAverageRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(currentRating.getValue() + " Year: " + MovieDatabase.getYear(currentMovieId) + " " +
                    MovieDatabase.getTitle(currentMovieId));
            System.out.println("Genres: " + MovieDatabase.getGenres(currentMovieId) + "\n");
        }
    }

    public void printAverageRatingsByMinutesAndDirectors(int minMinutes, int maxMinutes, String desiredDirectors) {
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
        allFilters.addFilter(new DirectorsFilter(desiredDirectors));
        ArrayList<Rating> filteredAverageRatings = thirdRatings.getAverageRatingsByFilter(minimalRatings, allFilters);
        System.out.println("\n" + "All ratings(" + filteredAverageRatings.size()
                + " in size) which have at least " + minimalRatings + " raters are: ");
        System.out.println("Filtered Results By Minutes and Directors :" + "\n");
        for (Rating currentRating : filteredAverageRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(currentRating.getValue() + " Time: " + MovieDatabase.getMinutes(currentMovieId) +
                    " minutes " + MovieDatabase.getTitle(currentMovieId));
            System.out.println("\t" + "Directors: " + MovieDatabase.getDirector(currentMovieId) + "\n");
        }
    }
}
