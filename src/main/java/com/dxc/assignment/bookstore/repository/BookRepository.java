package com.dxc.assignment.bookstore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.dxc.assignment.bookstore.models.BookDetails;
import static com.dxc.assignment.bookstore.repository.DBQueries.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    
    @Autowired
    JdbcTemplate template;

    public boolean insertBookDetails(BookDetails bookDetails){
        String authorsString = String.join(",", bookDetails.getAuthors());
        int inserted = template.update(INSERT_BOOK_DETAILS, bookDetails.getIsbn(), bookDetails.getTitle(), authorsString, bookDetails.getYear(), bookDetails.getPrice(), bookDetails.getGenre());
        
        return inserted > 0;
    }

    public boolean getBookDetailsByIsbn(String isbn){
        boolean recordExist = false;
        SqlRowSet rs = template.queryForRowSet(SELECT_BOOK_DETAILS_BY_ISBN , isbn);

        if(rs.first()){
            recordExist = true;
        }

        return recordExist;
    }

    public boolean updateBookDetails(BookDetails bookDetails){
        String authorsString = String.join(",", bookDetails.getAuthors());
        int updatedRecords = template.update(UPDATE_BOOK_DETAILS, bookDetails.getTitle(), authorsString, bookDetails.getYear(), bookDetails.getPrice(), bookDetails.getGenre(), bookDetails.getIsbn());

        return updatedRecords > 0;
    }

    public List<BookDetails> getBookDetailsByTitle(String title){
        
        List<BookDetails> bookDetailsList = new ArrayList<>();
        SqlRowSet rs = template.queryForRowSet(SELECT_BOOK_DETAILS_BY_TITLE , title);

        while(rs.next()){
            bookDetailsList.add(BookDetails.createFromSqlRowSet(rs));
        }

        return bookDetailsList;
    }

    public List<BookDetails> getBookDetailsByAuthors(List<String> authors){
        
        List<BookDetails> bookDetailsList = new ArrayList<>();
        String authorsString = String.join(",", authors);
        SqlRowSet rs = template.queryForRowSet(SELECT_BOOK_DETAILS_BY_AUTHORS , authorsString);

        while(rs.next()){
            bookDetailsList.add(BookDetails.createFromSqlRowSet(rs));
        }

        return bookDetailsList;
    }

    public List<BookDetails> getBookDetailsByAuthorsAndTitle(List<String> authors , String title){
        
        List<BookDetails> bookDetailsList = new ArrayList<>();
        String authorsString = String.join(",", authors);
        SqlRowSet rs = template.queryForRowSet(SELECT_BOOK_DETAILS_BY_AUTHORS_AND_TITLE , authorsString , title);

        while(rs.next()){
            bookDetailsList.add(BookDetails.createFromSqlRowSet(rs));
        }

        return bookDetailsList;
    }

    public void deleteBookDetailsByIsbn(String isbn){
        template.update(DELETE_BOOK_DETAILS_BY_ISBN , isbn);
    }
}
