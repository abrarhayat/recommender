package web;

import filters.AllFilters;
import filters.RatingAmountFilter;
import filters.YearAfterFilter;
import initialization.FourthRatings;
import org.testng.annotations.Test;
import pojo.Rating;
import pojo.database.MovieDatabase;
import utils.ArrayListProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author abrar
 * since 6/28/2019
 */

public class RecommendationRunner implements Recommender {
    ArrayList<String> selectedMovies;

    public RecommendationRunner() {
        selectedMovies = getItemsToRate();
    }

    @Override
    public ArrayList<String> getItemsToRate() {
        try {
            MovieDatabase.initialize("ratedmoviesfull.csv");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(1970));
        filters.addFilter(new RatingAmountFilter(10));
        ArrayList<String> source = MovieDatabase.filterBy(filters);
        if (selectedMovies == null) {
            selectedMovies = ArrayListProcessor.getSmallerArray(source, 50);
        }
        return selectedMovies;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {
        //System.out.println(selectedMovies.equals(this.selectedMovies));
        FourthRatings fourthRatings = new FourthRatings();
        ArrayList<Rating> recommendedMovies = fourthRatings.getSimilarRatingsFromSource(selectedMovies, webRaterID,
                10, 1);
        Collections.sort(recommendedMovies, Collections.reverseOrder());
        //System.out.println(recommendedMovies);
        //printing the initial html and meta tags
        printHTMLOpeningTag("!DOCTYPE html", 0);
        printHTMLOpeningTag("html", 0);
        //printing head tag for bootstrap css and title
        printHTMLOpeningTag("head", 1);
        printHTMLOpeningTag("link rel=\"stylesheet\" " +
                "href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"", 2);
        printSingleLineHTML("title", "Movie Recommender", 2);
        printHTMLClosingTag("head", 1);
        //printing the custom style tag
        printHTMLOpeningTag("style", 1);
        printCss(".text-center", "text-align", "center", 2);
        printCss("body", "background-color", "rgba(0, 10, 2, 0.7) !important",
                2);
        printCss("h1", "color", "black", 2);
        printHTMLClosingTag("style", 1);
        //printing the body initial body tag
        printHTMLOpeningTag("body", 1);
        //printing h1 header
        printSingleLineHTML("h1 class='text-center'", "Your Movie Recommendations", 2);
        //printing table
        printHTMLOpeningTag("table class='table table-striped table-dark table-bordered table-hover'",
                2);
        printHTMLOpeningTag("thead class='thead-dark'", 3);
        printHTMLOpeningTag("tr", 4);
        printSingleLineHTML("th", "Movie Title", 5);
        printSingleLineHTML("th", "Year", 5);
        printSingleLineHTML("th", "Genres", 5);
        printSingleLineHTML("th", "Directors", 5);
        printHTMLClosingTag("tr", 4);
        printHTMLClosingTag("thead", 3);
        printHTMLOpeningTag("tbody", 3);
        if (recommendedMovies.size() != 0) {
            for (Rating currentMovieRating : recommendedMovies) {
                String currentMovieId = currentMovieRating.getItem();
                String currentMovieTitle = MovieDatabase.getTitle(currentMovieId);
                int currentMovieYear = MovieDatabase.getYear(currentMovieId);
                String currentMovieGenre = MovieDatabase.getGenres(currentMovieId);
                String currentMovieDirectors = MovieDatabase.getDirector(currentMovieId);
                //printing the movies
                printHTMLOpeningTag("tr", 4);
                printSingleLineHTML("td", currentMovieTitle, 5);
                printSingleLineHTML("td", Integer.toString(currentMovieYear), 5);
                printSingleLineHTML("td", currentMovieGenre, 5);
                printSingleLineHTML("td", currentMovieDirectors, 5);
                printHTMLClosingTag("tr", 4);
            }
        } else {
            printSingleLineHTML("p class='text-center'", "Sorry there are not enough " +
                    "recommendations for you, please rate more movies.", 4);
        }
        printHTMLClosingTag("tbody", 3);
        printHTMLClosingTag("table", 2);
        printHTMLClosingTag("body", 1);
        printHTMLClosingTag("html", 0);
    }

    private void printSingleLineHTML(String tagName, String contents, int numOfTabs) {
        for (int num = 0; num < numOfTabs; num++) {
            System.out.print("\t");
        }
        String closingTagName = tagName.split(" ")[0];
        System.out.println("<" + tagName + ">" + contents + "</" + closingTagName + ">");
    }

    private void printHTMLOpeningTag(String tagName, int numOfTabs) {
        for (int num = 0; num < numOfTabs; num++) {
            System.out.print("\t");
        }
        System.out.println("<" + tagName + ">");
    }

    private void printHTMLClosingTag(String tagName, int numOfTabs) {
        for (int num = 0; num < numOfTabs; num++) {
            System.out.print("\t");
        }
        System.out.println("</" + tagName + ">");
    }

    private void printCss(String attributeName, String propertyName, String propertyValue, int numOfTabs) {
        for (int num = 0; num < numOfTabs; num++) {
            System.out.print("\t");
        }
        System.out.print(attributeName + " {" + "\n");
        for (int num = 0; num < numOfTabs + 1; num++) {
            System.out.print("\t");
        }
        System.out.print(propertyName + ": " + propertyValue + ";" + "\n");
        for (int num = 0; num < numOfTabs; num++) {
            System.out.print("\t");
        }
        System.out.print("}" + "\n");
    }


    @Test
    public void testGetItemsToRate() throws IOException {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> result = getItemsToRate();
        System.out.println("Result size: " + result.size());
        System.out.println("Result: " + "\n" + result);
        for (String movieId : result) {
            System.out.println(MovieDatabase.getTitle(movieId));
        }
    }

    @Test
    public void testPrintRecommendationsFor() {
        printRecommendationsFor("1024");
    }

    @Test
    public void testPrintHTML() {
        printSingleLineHTML("p", "This is a test", 0);
        printSingleLineHTML("p", "", 5);
        printHTMLOpeningTag("table", 2);
        printHTMLClosingTag("table", 2);
        printCss(".table", "text-align", "center", 0);
    }
}
