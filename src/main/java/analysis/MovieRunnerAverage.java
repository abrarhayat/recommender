package analysis;

import initialization.SecondRatings;
import pojo.Rating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author abrar
 * since 6/16/2019
 */

public class MovieRunnerAverage {
    private int minimalRatings = 12;

    public void printAverageRatings(String moviesFileName, String ratingsFileName) throws IOException {

        SecondRatings secondRatings = new SecondRatings(moviesFileName, ratingsFileName);

        System.out.println("myMovies Size: " + secondRatings.getMovieSize());
        System.out.println("myRaters Size: " + secondRatings.getRaterSize() + "\n");

        //for printing the ratings follo
        ArrayList<Rating> allAverageRatings = secondRatings.getAverageRatings(minimalRatings);
        System.out.println("All ratings(" + allAverageRatings.size() + " in size) which have at least " + minimalRatings + " raters are: ");
        System.out.println(allAverageRatings);
        //sorting in ascending order
        Collections.sort(allAverageRatings);
        for (Rating currentRating : allAverageRatings) {
            String currentMovieId = currentRating.getItem();
            String currentMovieTitle = secondRatings.getTitle(currentMovieId);
            System.out.println(currentRating.getValue() + " " + currentMovieTitle);
        }
    }

    public void getAverageRatingOneMovie(String moviesFileName, String ratingsFileName, String desiredMovieTitle) throws IOException {
        SecondRatings secondRatings = new SecondRatings(moviesFileName, ratingsFileName);
        String desiredMovieId = secondRatings.getId(desiredMovieTitle);
        //System.out.println(desiredMovieId);
        ArrayList<Rating> allAverageRatings = secondRatings.getAverageRatings(minimalRatings);
        for (Rating currentRating : allAverageRatings) {
            if (currentRating.getItem().equals(desiredMovieId)) {
                System.out.println("\n" + "Rating for the title " + desiredMovieTitle + " is " + currentRating.getValue());
            }
        }
    }
}
