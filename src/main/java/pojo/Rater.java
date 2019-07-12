package pojo;
/**
 * Write a description of class Operation.Rater here.
 *
 * @author (your name)
 * @version (a version number or a date)
 *
 *
 * @author abrar
 * since 6/18/2019
 *
 */

import java.util.*;

public interface Rater {

    public void addRating(String item, double rating);

    public boolean hasRating(String item);

    public String getID();

    public double getRating(String item);

    public int numRatings();

    public ArrayList<String> getItemsRated();
}
