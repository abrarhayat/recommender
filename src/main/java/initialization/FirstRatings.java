package initialization;

import pojo.EfficientRater;
import pojo.Movie;
import pojo.Rater;
import utils.CSVFileParser;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import utils.DataProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author abrar
 * since 6/15/2019
 */
public class FirstRatings {

    private ArrayList<Movie> movies;
    private ArrayList<Rater> raters;

    public FirstRatings() {
        movies = new ArrayList<Movie>();
        raters = new ArrayList<Rater>();
    }

    public ArrayList<Movie> loadMovies(String fileName) throws IOException {
        CSVParser parser = CSVFileParser.getCSVParser(fileName);
        for (CSVRecord currentRecord : parser) {
            Movie currentMovie = new Movie(currentRecord.get("id"), currentRecord.get("title"),
                    currentRecord.get("year"), currentRecord.get("genre"), currentRecord.get("director"),
                    currentRecord.get("country"), currentRecord.get("poster"),
                    Integer.parseInt(currentRecord.get("minutes")));
            movies.add(currentMovie);
        }
        return movies;
    }

    public void loadRaters(String fileName) throws IOException {
        CSVParser parser = CSVFileParser.getCSVParser(fileName);
        for (CSVRecord currentRecord : parser) {
            String currentRaterId = currentRecord.get("rater_id");
            String currentMovieId = currentRecord.get("movie_id");
            int currentRating = Integer.parseInt(currentRecord.get("rating"));

            //checking for already existing rater
            if (raterAlreadyExists(currentRaterId)) {
                Rater existingRater = getRaterWithId(currentRaterId);
                existingRater.addRating(currentMovieId, currentRating);
            } else {
                //Rater newRater = new PlainRater(currentRaterId);
                Rater newRater = new EfficientRater(currentRaterId);
                newRater.addRating(currentMovieId, currentRating);
                raters.add(newRater);
            }
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Rater> getRaters() {
        return raters;
    }

    //Returns a Hashmap with key as Director name and their movie count as key
    private HashMap<String, Integer> getDirectorAndCounts() {
        //Total Number of movies by each director inside a HashMap
        HashMap<String, Integer> directorAndCounts = new HashMap<String, Integer>();
        for (Movie currentMovie : movies) {
            String[] directorNames = currentMovie.getDirector().split(",");
            for (String name : directorNames) {
                String directorName = name.trim(); //trimming for leading/trailing spaces
                //System.out.println(directorName);
                if (!directorAndCounts.containsKey(directorName)) {
                    directorAndCounts.put(directorName, 1);
                } else {
                    int currentCount = directorAndCounts.get(directorName);
                    directorAndCounts.put(directorName, currentCount + 1);
                }
            }
        }
        return directorAndCounts;
    }

    private ArrayList<Movie> getAllComedyMovies() {
        ArrayList<Movie> comedyMovies = new ArrayList<Movie>();
        for (Movie currentMovie : movies) {
            if (currentMovie.getGenres().toLowerCase().contains("comedy")) {
                comedyMovies.add(currentMovie);
            }
        }
        return comedyMovies;
    }

    private ArrayList<Movie> getAllMovieWithLengthGreaterThan(int minutes) {
        ArrayList<Movie> moviesGreaterThanLength = new ArrayList<Movie>();
        for (Movie currentMovie : movies) {
            if (currentMovie.getMinutes() > minutes) {
                //System.out.println(currentMovie.getMinutes());
                moviesGreaterThanLength.add(currentMovie);
            }
        }
        return moviesGreaterThanLength;
    }

    private void printMaxDirectors(HashMap<String, Integer> directorAndCounts) {
        int maxMovieCount = DataProcessor.getMaxPositive(directorAndCounts);
        for (String currentDirector : directorAndCounts.keySet()) {
            int currentMovieCount = directorAndCounts.get(currentDirector);
            if (currentMovieCount == maxMovieCount) {
                System.out.println("Director: " + currentDirector + ", Number of movies: " + currentMovieCount);
            }
        }
    }

    //checks if a Rater object is already present with the given rater id
    private boolean raterAlreadyExists(String raterId) {
        for (Rater existingRater : raters) {
            if (raterId.equals(existingRater.getID())) {
                return true;
            }
        }
        return false;
    }

    //get a Rater object by providing a rater id
    private Rater getRaterWithId(String raterId) {
        for (Rater rater : raters) {
            if (raterId.equals(rater.getID())) {
                return rater;
            }
        }
        return null;
    }

    private void printAllRaterAndRatingInfo() {
        //Raters and the amount of ratings they have along with the Movie id and rating given
        for (Rater rater : raters) {
            System.out.println("Rater Id: " + rater.getID() + " Number Of Ratings: " + rater.numRatings());
            System.out.println("The rated movies by this rater are: ");
            //acquiring all the movie ids rated by this rater
            ArrayList<String> itemsRated = rater.getItemsRated();
            for (String currentMovieId : itemsRated) {
                //rating for the current movie id
                double currentMovieRating = rater.getRating(currentMovieId);
                System.out.println("Movie Id: " + currentMovieId + " Rating: " + currentMovieRating);
            }
            System.out.println("\n");
        }
    }

    private void printNumberOfRatingsOf(String raterId) {
        for (Rater currentRater : raters) {
            if (currentRater.getID().equals(raterId)) {
                System.out.println("Number of ratings for Rater with id:" + raterId + " is " +
                        currentRater.numRatings());
                return;
            }
        }
        System.out.println("No Rater found with id " + raterId);
    }

    private int getMaxRatingCount() {
        int maxCount = 0;
        for (Rater rater : raters) {
            int currentRaterRatingCount = rater.numRatings();
            if (currentRaterRatingCount > maxCount) {
                maxCount = currentRaterRatingCount;
            }
        }
        return maxCount;
    }

    private ArrayList<Rater> getMaxRaters() {
        int maxRatingCount = getMaxRatingCount();
        ArrayList<Rater> maxRaters = new ArrayList<Rater>();
        for (Rater currentRater : raters) {
            if (currentRater.numRatings() == maxRatingCount) {
                maxRaters.add(currentRater);
            }
        }
        return maxRaters;
    }

    private ArrayList<Double> getMovieRatings(String movieId) {
        ArrayList<Double> movieRatings = new ArrayList<Double>();
        for (Rater currentRater : raters) {
            double currentRating = currentRater.getRating(movieId);
            if (!(currentRating == -1)) {
                movieRatings.add(currentRating);
            }
        }
        return movieRatings;
    }

    private ArrayList<String> getAllMoviesRatedByAllRaters() {
        ArrayList<String> movies = new ArrayList<String>();
        for (Rater currentRater : raters) {
            ArrayList<String> currentMovies = currentRater.getItemsRated();
            for (String currentMovieId : currentMovies) {
                if (!movies.contains(currentMovieId)) {
                    movies.add(currentMovieId);
                }
            }
        }
        return movies;
    }

    public void testLoadMovies(String fileName) throws IOException {
        loadMovies(fileName);
        //Total Number of Movies
        System.out.println("Total Number of movies: " + movies.size());

        //System.out.println(movies);
/*      Movie firstMovieInTheList = movies.get(0);
        System.out.println("First Movie: " + "Director: " + (firstMovieInTheList.getDirector()) + ", Country: " +
                firstMovieInTheList.getCountry() + ", Poster: " + firstMovieInTheList.getPoster() + ", Minutes" +
                firstMovieInTheList.getMinutes());*/

        //Total number of comedy movies
        ArrayList<Movie> comedyMovies = getAllComedyMovies();
        System.out.println("Number of comedy movies: " + comedyMovies.size());

        //Total movies with length greater than 150 minutes
        int minutes = 150;
        ArrayList<Movie> moviesGreaterThan150 = getAllMovieWithLengthGreaterThan(minutes);
        System.out.println("Number of movies of length greater than 150 mins: " + moviesGreaterThan150.size());

        //Total Number of movies by each director inside a HashMap
        HashMap<String, Integer> directorAndCounts = getDirectorAndCounts();
        //System.out.println(directorAndCounts);

        //looking for maxMovieCount
        int maxMovieCount = DataProcessor.getMaxPositive(directorAndCounts);
        System.out.println("Maximum number of movies by a director: " + maxMovieCount);

        //All the directors that have max movie counts
        printMaxDirectors(directorAndCounts);
    }

    public void testLoadRaters(String fileName) throws IOException {
        loadRaters(fileName);

        //Total Number of unique raters
        System.out.println("\n" + "Total Number of Raters: " + raters.size() + "\n");

        //Raters and the amount of ratings they have along with the Movie id and rating given
        //printRaterAndRatingInfo();

        //printing the number of rating by rater with id 2
        String raterId = "2";
        printNumberOfRatingsOf(raterId);

        //printing the maximum amount of ratings by any user
        System.out.println("The maximum amount of Ratings by any rater is : " + getMaxRatingCount());

        //printing the raters with maximum amount of ratings
        System.out.println("\n" + "Raters with maximum ratings are: ");
        ArrayList<Rater> maxRaters = getMaxRaters();
        for (Rater rater : maxRaters) {
            System.out.println("Rater Id : " + rater.getID() + ", Number of ratings: " + rater.numRatings());
        }

        //printing the number of rating for movie with id 1798709
        String movieId = "1798709";
        ArrayList<Double> movieRatings = getMovieRatings(movieId);
        System.out.println("Number of ratings for movie with id " + movieId + " is : " + movieRatings.size());

        //printing all the unique number of movies rated by all users
        ArrayList<String> allMoviesRated = getAllMoviesRatedByAllRaters();
        System.out.println("Number of movies rated by all users : " + allMoviesRated.size());
    }
}


