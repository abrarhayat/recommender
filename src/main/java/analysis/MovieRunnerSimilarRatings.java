package analysis;

import filters.*;
import initialization.FourthRatings;
import initialization.ThirdRatings;
import pojo.Rating;
import pojo.database.MovieDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author abrar
 * since 6/25/2019
 */

public class MovieRunnerSimilarRatings {

    private FourthRatings fourthRatings;
    private String moviesFileName;
    private String ratingsFileName;

    public MovieRunnerSimilarRatings(String moviesFileName, String ratingsFileName) throws IOException {
        fourthRatings = new FourthRatings(ratingsFileName);
        //initialize the movie db static HashMap containing movies against movieId
        MovieDatabase.initialize(moviesFileName);
        this.moviesFileName = moviesFileName;
        this.ratingsFileName = ratingsFileName;
    }

    public void printAverageRatings(int minimalRatings) {
        System.out.println("myMovies Size: " + MovieDatabase.size());
        System.out.println("myRaters Size: " + fourthRatings.getRaterSize());
        //for printing the ratings followed by the movies vs their average ratings
        ArrayList<Rating> allAverageRatings = fourthRatings.getAverageRatings(minimalRatings);
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

    public void printAverageRatingsByYear(int desiredYear, int minimalRatings) {
        //for printing the filtered ratings followed by the movies vs their average ratings
        ArrayList<Rating> filteredAverageRatings = fourthRatings.getAverageRatingsByFilter(minimalRatings,
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

    private void printNewLine() {
        System.out.println("\n");
    }

    public void printSimilarRatings(String desiredUserId, int numSimilarRatings, int minimalRatings) {
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(desiredUserId, numSimilarRatings,
                minimalRatings);
        //System.out.println(similarRatings);
        System.out.println("Movies With No Filter");
        for (Rating currentRating : similarRatings) {
            System.out.println(MovieDatabase.getTitle(currentRating.getItem()) + " " + currentRating.getValue());
        }
        printNewLine();
    }

    public void printSimilarRatingsFromSource( ArrayList<String> source, String desiredUserId, int numSimilarRatings, int minimalRatings) {
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsFromSource(source, desiredUserId, numSimilarRatings,
                minimalRatings);
        //System.out.println(similarRatings);
        System.out.println("Movies From Source With No Filter");
        for (Rating currentRating : similarRatings) {
            System.out.println(MovieDatabase.getTitle(currentRating.getItem()) + " " + currentRating.getValue());
        }
        printNewLine();
    }

    public void printSimilarRatingsByGenre(String desiredUserId, int numSimilarRatings, GenreFilter filter,
                                           int minimalRatings) {
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(desiredUserId, numSimilarRatings,
                minimalRatings, filter);
        //System.out.println(similarRatings);
        System.out.println("Movies With Genre Filter");
        for (Rating currentRating : similarRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(MovieDatabase.getTitle(currentMovieId) + " " + currentRating.getValue());
            System.out.println(MovieDatabase.getGenres(currentMovieId));
        }
        printNewLine();
    }

    public void printSimilarRatingsByDirector(String desiredUserId, int numSimilarRatings, DirectorsFilter filter,
                                              int minimalRatings) {
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(desiredUserId, numSimilarRatings,
                minimalRatings, filter);
        //System.out.println(similarRatings);
        System.out.println("Movies With Director Filter");
        for (Rating currentRating : similarRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(MovieDatabase.getTitle(currentMovieId) + " " + currentRating.getValue());
            System.out.println(MovieDatabase.getDirector(currentMovieId));
        }
        printNewLine();
    }

    public void printSimilarRatingsByGenreAndMinutes(String desiredUserId, int numSimilarRatings,
                                                     GenreFilter genreFilter, MinutesFilter minutesFilter,
                                                     int minimalRatings) {
        AllFilters filter = new AllFilters();
        filter.addFilter(genreFilter);
        filter.addFilter(minutesFilter);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(desiredUserId, numSimilarRatings,
                minimalRatings, filter);
        //System.out.println(similarRatings);
        System.out.println("Movies With Genre and Minutes Filter");
        for (Rating currentRating : similarRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(MovieDatabase.getTitle(currentMovieId) + " " + MovieDatabase.getMinutes(currentMovieId)
                    + " minutes " + currentRating.getValue());
            System.out.println(MovieDatabase.getGenres(currentMovieId));
        }
        printNewLine();
    }

    public void printSimilarRatingsByYearAfterAndMinutes(String desiredUserId, int numSimilarRatings,
                                                         YearAfterFilter yearAfterFilter, MinutesFilter minutesFilter,
                                                         int minimalRatings) {
        AllFilters filter = new AllFilters();
        filter.addFilter(yearAfterFilter);
        filter.addFilter(minutesFilter);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatingsByFilter(desiredUserId, numSimilarRatings,
                minimalRatings, filter);
        //System.out.println(similarRatings);
        System.out.println("Movies With Year After and Minutes Filter");
        for (Rating currentRating : similarRatings) {
            String currentMovieId = currentRating.getItem();
            System.out.println(MovieDatabase.getTitle(currentMovieId) + " " + MovieDatabase.getYear(currentMovieId)
                    + " " + MovieDatabase.getMinutes(currentMovieId) + " minutes " + currentRating.getValue());
        }
        printNewLine();
    }
}
