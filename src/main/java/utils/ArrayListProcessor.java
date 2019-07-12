package utils;

import filters.TrueFilter;
import org.testng.annotations.Test;
import pojo.database.MovieDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author abrar
 * since 6/28/2019
 */

public class ArrayListProcessor {
    private static ArrayList<String> result;
    private static int resultSize;


    public static ArrayList<String> getSmallerArray(ArrayList<String> inputArray, int desiredResultSize) {
        result = new ArrayList<String>();
        resultSize = desiredResultSize;
        Random random = new Random();
        //initial random number
        int randomIndexToLookForInInput = random.nextInt(inputArray.size());
        while (result.size() < desiredResultSize) {
            if (!result.contains(inputArray.get(randomIndexToLookForInInput))) {
                result.add(inputArray.get(randomIndexToLookForInInput));
                randomIndexToLookForInInput = random.nextInt(desiredResultSize);
            } else {
                randomIndexToLookForInInput = random.nextInt(desiredResultSize);
            }
        }
        return result;
    }

    @Test
    public void testGetSmallerArray() throws IOException {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> inputArray = MovieDatabase.filterBy(new TrueFilter());
        System.out.println("Source size: " + inputArray.size());
        ArrayList<String> result = ArrayListProcessor.getSmallerArray(inputArray, 50);
        System.out.println("Result size: " + result.size());
        System.out.println("Result: " + "\n" + result);
        for(String movieId : result){
            System.out.println(MovieDatabase.getTitle(movieId));
        }
    }
}
