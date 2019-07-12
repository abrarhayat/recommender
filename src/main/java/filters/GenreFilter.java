package filters;

import pojo.database.MovieDatabase;

/**
 * @author abrar
 * since 6/21/2019
 */

public class GenreFilter implements Filter {
    //this represents one of the genres a movie may have
    private String genre;

    public GenreFilter(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean satisfies(String id) {
        //.contains() is used because it will return true if the given genre matches any of the genres of that movie
        return MovieDatabase.getGenres(id).toLowerCase().contains(genre.toLowerCase());
    }
}
