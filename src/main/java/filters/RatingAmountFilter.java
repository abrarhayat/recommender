package filters;

import pojo.Rater;
import pojo.database.RaterDatabase;

import java.util.ArrayList;

/**
 * @author abrar
 * since 6/29/2019
 */

public class RatingAmountFilter implements Filter {
    private int numOfRatings;
    private int numOfDesiredRatings;

    public RatingAmountFilter(int numOfDesiredRatings) {
        this.numOfDesiredRatings = numOfDesiredRatings;
        numOfRatings = 0;
    }

    @Override
    public boolean satisfies(String id) {
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rater> allRaters = RaterDatabase.getRaters();
        for (Rater currentRater : allRaters) {
            if (currentRater.hasRating(id)) {
                numOfRatings++;
            }
        }
        return numOfRatings >= numOfDesiredRatings;
    }

    public static void main(String[] args) {
        RatingAmountFilter ratingAmountFilter = new RatingAmountFilter(6);
        //testing the ratings for Hobbits which has 17 ratings by users
        System.out.println("Result of the satisfies method: " + ratingAmountFilter.satisfies("2310332"));
        System.out.println("Amount of Ratings = " + ratingAmountFilter.numOfRatings);
    }
}
