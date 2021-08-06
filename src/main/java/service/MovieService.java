package service;

import DAO.MovieDAO;
import model.Movie;
import org.bson.Document;

import java.util.List;

public class MovieService {

    final static int NUM_OF_MOVIE_ON_PAGE = 6;
    public List<Movie> searchMovies(String by, String value, int page, String text) {
        Document filter = new Document();
        Document sort = new Document();
        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));
        else
            sort.append("year", -1);

        return MovieDAO.searchMovies(filter, sort, NUM_OF_MOVIE_ON_PAGE, (page - 1) * NUM_OF_MOVIE_ON_PAGE);
    }

    public static Movie getMovieByID(String id) {
        return MovieDAO.getMovieByID(id);
    }

    public long getTotalPages(String by, String value, String text) {
        Document filter = new Document();
        if (by != null && value != null)
            filter.append(by, value);
        if (text != null)
            filter.append("$text", new Document("$search", text));
        long totalMovies = new MovieDAO().getMoviesNumber(filter);
        return (long) Math.ceil((float) totalMovies / NUM_OF_MOVIE_ON_PAGE);
    }
}
