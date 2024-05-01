package com.anik.capstone.rec;

import com.anik.capstone.model.BookModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class RecommendationEngine {
    private List<BookModel> books;
    private int maxAuthorsBound;
    private int maxGenresBound;
    private Random random;
    private Map<String, List<String>> recommendations;

    public RecommendationEngine(List<BookModel> books) {
        recommendations = new HashMap<>();
        this.books = books;
        this.random = new Random();
        setBounds();
    }

    private void setBounds() {
        Set<String> allAuthors = new HashSet<>();
        Set<String> allGenres = new HashSet<>();
        for (BookModel book : books) {
            allAuthors.add(book.getAuthor());
            allGenres.addAll(book.getGenres());
        }

        int authorsSize = allAuthors.size();
        int genresSize = allGenres.size();
        this.maxAuthorsBound = (authorsSize > 1) ? random.nextInt(authorsSize / 2) + 1 : 1;
        this.maxGenresBound = (genresSize > 1) ? random.nextInt(genresSize / 2) + 1 : 1;
    }

    public String selectAuthorsAndGenres() {
        books.sort((book1, book2) -> Float.compare(book2.getRating().getOverallRating(), book1.getRating().getOverallRating()));

        Set<String> selectedAuthors = new HashSet<>();
        Set<String> selectedGenres = new HashSet<>();

        for (BookModel book : books) {
            if (selectedAuthors.size() < maxAuthorsBound) {
                selectedAuthors.add(book.getAuthor());
            }

            if (selectedGenres.size() < maxGenresBound) {
                selectedGenres.addAll(book.getGenres());
                if (selectedGenres.size() > maxGenresBound) {
                    selectedGenres = new HashSet<>(new ArrayList<>(selectedGenres).subList(0, maxGenresBound));
                }
            }

            if (selectedAuthors.size() == maxAuthorsBound && selectedGenres.size() == maxGenresBound) {
                break;
            }
        }

        // Prepare result to return
        recommendations.put("authors", new ArrayList<>(selectedAuthors));
        recommendations.put("genres", new ArrayList<>(selectedGenres));


        return buildSearchQuery();
    }

    private String buildSearchQuery() {
        // Extract authors and genres lists
        List<String> authors = recommendations.get("authors");
        List<String> genres = recommendations.get("genres");

        List<String> combined = new ArrayList<>(authors);
        combined.addAll(genres);

        return combined.stream()
                .collect(Collectors.joining(" OR "));
    }
}