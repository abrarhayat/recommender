package initialization;
/**
 * Write a description of SecondRatings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 *
 * @author abrar
 * since 6/15/2019
 */

import pojo.Movie;
import pojo.Rater;
import pojo.Rating;

import java.io.IOException;
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings() throws IOException {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviesFileName, String ratingsFileName) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.loadMovies(moviesFileName);
        firstRatings.loadRaters(ratingsFileName);
        myMovies = firstRatings.getMovies();
        myRaters = firstRatings.getRaters();
    }

    public int getMovieSize() {
        return myMovies.size();
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageRatingById(String movieId, int minimalRaters) {
        double totalRating = 0;
        int numberOfRaters = 0;
        for (Rater currentRater : myRaters) {
            double currentRaterCurrentRating = currentRater.getRating(movieId);
            if (currentRaterCurrentRating != -1) {
                totalRating = totalRating + currentRaterCurrentRating;
                numberOfRaters++;
            }
        }
        if (numberOfRaters >= minimalRaters) {
            return totalRating / numberOfRaters;
        }
        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        for (Movie currentMovie : myMovies) {
            String currentMovieId = currentMovie.getID();
            double currentAverageRatingValue = getAverageRatingById(currentMovieId, minimalRaters);
            if (!(currentAverageRatingValue == 0.0)) {
                Rating currentAverageRating = new Rating(currentMovieId, currentAverageRatingValue);
                averageRatingList.add(currentAverageRating);
            }
        }
        return averageRatingList;
    }

    public String getTitle(String movieId) {
        for (Movie currentMovie : myMovies) {
            if (currentMovie.getID().equals(movieId)) {
                return currentMovie.getTitle();
            }
        }
        return "No title found for id: " + movieId;
    }

    public String getId(String title) {
        for (Movie currentMovie : myMovies) {
            if ((currentMovie.getTitle().toLowerCase()).equals(title.toLowerCase())) {
                return currentMovie.getID();
            }
        }
        return "No id found for title: " + title;
    }
}
