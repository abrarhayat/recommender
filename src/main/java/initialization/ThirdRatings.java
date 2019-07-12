package initialization;

import filters.Filter;
import filters.TrueFilter;
import pojo.Movie;
import pojo.Rater;
import pojo.Rating;
import pojo.database.MovieDatabase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author abrar
 * since 6/19/2019
 */

public class ThirdRatings {

    private ArrayList<Rater> myRaters;

    public ThirdRatings() throws IOException {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsFileName) throws IOException {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.loadRaters(ratingsFileName);
        myRaters = firstRatings.getRaters();
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
        ArrayList <String> movieIds = MovieDatabase.filterBy(new TrueFilter());
        for(String currentMovieId : movieIds){
            double currentMovieAverageRating = getAverageRatingById(currentMovieId, minimalRaters);
            if(!(currentMovieAverageRating == 0.0)){
                Rating currentAverageRating = new Rating(currentMovieId, currentMovieAverageRating);
                averageRatingList.add(currentAverageRating);
            }
        }
        return averageRatingList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filter){
        ArrayList<Rating> averageRatingListWithFilter = new ArrayList<Rating>();
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(filter);
        for(String currentMovieId : filteredMovieIds){
            double currentMovieAverageRating = getAverageRatingById(currentMovieId, minimalRaters);
            if(!(currentMovieAverageRating == 0.0)){
                Rating currentAverageRating = new Rating(currentMovieId, currentMovieAverageRating);
                averageRatingListWithFilter.add(currentAverageRating);
            }
        }
        return averageRatingListWithFilter;
    }
}
