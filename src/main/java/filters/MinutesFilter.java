package filters;

import pojo.database.MovieDatabase;

/**
 * @author abrar
 * since 6/21/2019
 */

public class MinutesFilter implements Filter {
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes){
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }
    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= minMinutes && MovieDatabase.getMinutes(id) <= maxMinutes;
    }
}
