package com.dxc.assignment.bookstore.models;

import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class BookDetails {
    
    private String isbn;
    private String title;
    private List<String> authors;
    private Integer year;
    private Double price;
    private String genre;
    
    public BookDetails() {
    }

    public BookDetails(String isbn, String title, List<String> authors, Integer year, Double price, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.price = price;
        this.genre = genre;
    }


    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<String> getAuthors() {
        return authors;
    }
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    
    @Override
    public String toString() {
        return "BookDetails [isbn=" + isbn + ", title=" + title + ", authors=" + authors + ", year=" + year + ", price="
                + price + ", genre=" + genre + "]";
    }

    public JsonObject toJson(){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
                .add("isbn", this.getIsbn())
                .add("title", this.getTitle())
                .add("year", this.getYear())
                .add("price", this.getPrice())
                .add("genre", this.getGenre());

        JsonArrayBuilder jsonAuthors = Json.createArrayBuilder();
        for (String author : this.getAuthors()) {
            jsonAuthors.add(author);
        }
        jsonObjectBuilder.add("authors", jsonAuthors);

        return jsonObjectBuilder.build();
    }

    public static BookDetails createFromSqlRowSet(SqlRowSet rs){
        BookDetails bookDetails = new BookDetails();

        String authorsString = rs.getString("authors");
        List<String> authorsList = Arrays.asList(authorsString.split(","));

        bookDetails.setIsbn(rs.getString("isbn"));
        bookDetails.setTitle(rs.getString("title"));
        bookDetails.setAuthors(authorsList);
        bookDetails.setYear(rs.getInt("year"));
        bookDetails.setPrice(rs.getDouble("price"));
        bookDetails.setGenre(rs.getString("genre"));

        return bookDetails;
    }
}
