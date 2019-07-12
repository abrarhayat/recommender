package pojo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author abrar
 * since 6/18/2019
 */

public class EfficientRater implements Rater {

    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) {
        //now we will store the movie id along with the rating in the HashMap, String item is the movie id here
        myRatings.put(item, new Rating(item, rating));
    }

    public boolean hasRating(String item) {
        if (myRatings.get(item) != null) {
            return true;
        }
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if(hasRating(item)){
            return myRatings.get(item).getValue();
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (String currentItem : myRatings.keySet()) {
            list.add(currentItem);
        }
        return list;
    }
}
