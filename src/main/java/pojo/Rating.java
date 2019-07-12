package pojo;

import java.util.ArrayList;

// An immutable passive data object (PDO) to represent the rating data
public class Rating implements Comparable<Rating> {
    private String item;
    private double value;

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    // Returns item being rated
    public String getItem () {
        return item;
    }

    // Returns the value of this rating (as a number so it can be used in calculations)
    public double getValue () {
        return value;
    }

    // Returns a string of all the rating information
    public String toString () {
        return "[" + getItem() + ", " + getValue() + "]";
    }

    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }

    public ArrayList<Rating> keepInArrayListWith(Rating other1, Rating other2){
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ratings.add(this);
        ratings.add(other2);
        ratings.add(other1);
        /*returns an unsorted list*/
        return ratings;
    }
}
