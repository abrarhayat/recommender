package filters;

import pojo.database.MovieDatabase;

/**
 * @author abrar
 * since 6/21/2019
 */

public class DirectorsFilter implements Filter {
    private String directors;

    public DirectorsFilter(String directors) {
        this.directors = directors;
    }

    @Override
    public boolean satisfies(String id) {
        //all the directors of the movie in a single string array
        String[] allCurrentMovieDirectors = MovieDatabase.getDirector(id).split(",");
        for (String currentDirector : allCurrentMovieDirectors) {
            if (directors.toLowerCase().contains(currentDirector.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
