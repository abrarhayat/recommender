package utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author abrar
 * since 6/15/2019
 */

public class DataProcessor {

    public static int getMaxPositive(HashMap<String, Integer> map) {
        int max = 0;
        for (String currentKey : map.keySet()) {
            int currentValue = map.get(currentKey);
            if (currentValue > max) {
                max = currentValue;
            }
        }
        return max;
    }

    public static void getMaxPositive(ArrayList<Integer> list) {
        int max = 0;
        for (int currentValue : list) {
            if (currentValue > max) {
                max = currentValue;
            }
        }
    }
}
