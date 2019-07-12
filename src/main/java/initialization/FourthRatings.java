package initialization;

import filters.Filter;
import filters.TrueFilter;
import org.testng.annotations.Test;
import pojo.Rater;
import pojo.Rating;
import pojo.database.MovieDatabase;
import pojo.database.RaterDatabase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author abrar
 * since 6/25/2019
 */

public class FourthRatings {

    public FourthRatings() {
        // default constructor
        this("ratings.csv");
    }

    public FourthRatings(String ratingsFileName) {
        RaterDatabase.initialize(ratingsFileName);
    }

    public int getRaterSize() {
        return RaterDatabase.size();
    }

    private double getAverageRatingById(String movieId, int minimalRaters) {
        double totalRating = 0;
        int numberOfRaters = 0;
        ArrayList<Rater> allRaters = RaterDatabase.getRaters();
        for (Rater currentRater : allRaters) {
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
        ArrayList<String> movieIds = MovieDatabase.filterBy(new TrueFilter());
        for (String currentMovieId : movieIds) {
            double currentMovieAverageRating = getAverageRatingById(currentMovieId, minimalRaters);
            if (!(currentMovieAverageRating == 0.0)) {
                Rating currentAverageRating = new Rating(currentMovieId, currentMovieAverageRating);
                averageRatingList.add(currentAverageRating);
            }
        }
        return averageRatingList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filter) {
        ArrayList<Rating> averageRatingListWithFilter = new ArrayList<Rating>();
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(filter);
        for (String currentMovieId : filteredMovieIds) {
            double currentMovieAverageRating = getAverageRatingById(currentMovieId, minimalRaters);
            if (!(currentMovieAverageRating == 0.0)) {
                Rating currentAverageRating = new Rating(currentMovieId, currentMovieAverageRating);
                averageRatingListWithFilter.add(currentAverageRating);
            }
        }
        return averageRatingListWithFilter;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        //fetching all the movies
        ArrayList<String> allMovieIds = MovieDatabase.filterBy(new TrueFilter());
        //we will now return ratings of all movies by calculating the weighted total and then add them to similarRatings
        //this method returns a list of Ratings of movies with weighted average ratings
        return getAllSimilarRatingsFrom(id, allMovieIds, minimalRaters, numSimilarRaters);
        //return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getSimilarRatingsFromSource(ArrayList<String> source, String id, int numSimilarRaters, int minimalRaters) {
        //we will now return ratings of all movies by calculating the weighted total and then add them to similarRatings
        //this method returns a list of Ratings of movies with weighted average ratings
        return getAllSimilarRatingsFrom(id, source, minimalRaters, numSimilarRaters);
        //return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filter) {
        //fetching all the movies
        ArrayList<String> allMovieIds = MovieDatabase.filterBy(filter);
        //we will now return ratings of all movies by calculating the weighted total and then add them to similarRatings
        //this method returns a list of Ratings of movies with weighted average ratings
        return getAllSimilarRatingsFrom(id, allMovieIds, minimalRaters, numSimilarRaters);
    }

    private ArrayList<Rating> getAllSimilarRatingsFrom(String desiredRaterId, ArrayList<String> allMovieIds,
                                                       int minimalRaters, int numSimilarRaters) {
        //finding out the rater who are similar to the user whose id is provider as a param here
        ArrayList<Rating> similarRaters = getSimilarities(desiredRaterId);
        //System.out.println("similarRaters: " + similarRaters);
        //creating a list for similar ratings of movies to recommend
        ArrayList<Rating> similarRatings = new ArrayList<Rating>();
        Rater desiredUser = RaterDatabase.getRater(desiredRaterId);
        if (similarRaters.size() >= minimalRaters && numSimilarRaters >= minimalRaters) {
            for (String movieId : allMovieIds) {
                double totalWeightedRatingForMovie = 0.0;
                int totalRaters = 0;
                //for loop through the similar raters list from the top until the index of numSimilarRaters
                for (int index = 0; index < numSimilarRaters; index++) {
                    /*instantiating a Rater object from Rater DB by passing the rater id
                     * we get from the similarRaters list which consists of
                     * Rater objects which in turn contains a rater id and a dotProduct
                     * (as opposed to a movie id and rating)
                     */
                    if (index < similarRaters.size()) {
                        Rater currentRater = RaterDatabase.getRater(similarRaters.get(index).getItem());
                        if (currentRater.hasRating(movieId)) { //&& !desiredUser.hasRating(movieId) commenting this one out for a while
                            //multiplying the rating of the movie to the weight of the similar rater
                            double weightedRating = currentRater.getRating(movieId) * similarRaters.get(index).getValue();
                            totalWeightedRatingForMovie = totalWeightedRatingForMovie + weightedRating;
                            totalRaters++;
                        }
                    }
                }
/*                System.out.println("total weighted rating for movie with title " + MovieDatabase.getTitle(movieId)
                        + " " + totalWeightedRatingForMovie);*/
                if (totalRaters >= minimalRaters && totalWeightedRatingForMovie > 0.0) {
                    //adding the rating object with a movie id and a weighted average rating
                    //System.out.println("movie:" + MovieDatabase.getTitle(movieId) + " value: " + totalWeightedRatingForMovie / totalRaters);
                    similarRatings.add(new Rating(movieId, totalWeightedRatingForMovie / totalRaters));
                }
            }
        }
        //sorting in ASC order
        Collections.sort(similarRatings, Collections.reverseOrder());
        return similarRatings;
    }

    private double dotProduct(Rater me, Rater r) {
        ArrayList<Double> separateDotProducts = new ArrayList<Double>();
        double dotProduct = 0.0;
        ArrayList<String> allMovieIds = MovieDatabase.filterBy(new TrueFilter());
        for (String currentMovieId : allMovieIds) {
            if (me.hasRating(currentMovieId) && r.hasRating(currentMovieId)) {
                //rating are scaled from -5 to 5 and NOT 0 to 10
                separateDotProducts.add((me.getRating(currentMovieId) - 5) * (r.getRating(currentMovieId) - 5));
            }
        }
        for (double currentDotproduct : separateDotProducts) {
            dotProduct += currentDotproduct;
        }
        return dotProduct;
    }

    private ArrayList<Rating> getSimilarities(String id) {
        /*this list of ratings is NOT a list of rating of movies but rather a list of Rating
         * of Similar Raters each rating object inside this list will contains a rater id
         * along with the value of the dot product of the two raters
         */
        ArrayList<Rating> similarUserRating = new ArrayList<Rating>();
        ArrayList<Rater> allRaters = RaterDatabase.getRaters();
        Rater me = RaterDatabase.getRater(id);
        //calculating the dot product for each rater and then adding to the list
        for (Rater currentRater : allRaters) {
            if (!(currentRater.getID().equals(id))) {
                double currentDotProduct = dotProduct(me, currentRater);
                //since we only want the positive dot products as negative ones will convey no useful info
                if (currentDotProduct > 0.0) {
                    similarUserRating.add(new Rating(currentRater.getID(), currentDotProduct));
                }
            }
        }
        //ordering in descending order with the highest values of dot products at the top
        Collections.sort(similarUserRating, Collections.reverseOrder());
        return similarUserRating;
    }

    @Test
    public void testGetSimilarities() {
        System.out.println(getSimilarities("934"));
    }
}
