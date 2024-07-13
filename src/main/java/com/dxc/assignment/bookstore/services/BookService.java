package com.dxc.assignment.bookstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.dxc.assignment.bookstore.models.BookDetails;
import com.dxc.assignment.bookstore.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepo;
    
    public void insertBookDetails(BookDetails bookDetails) throws DataAccessException{
        boolean recordInserted = bookRepo.insertBookDetails(bookDetails);
    }

    public void updateBookDetails(BookDetails bookDetails) throws DataAccessException{
        boolean recordUpdated = bookRepo.updateBookDetails(bookDetails);
    }

    public boolean getBookDetailsByIsbn(String isbn){
        return bookRepo.getBookDetailsByIsbn(isbn);
    } 

    public List<BookDetails> getBookDetailsByTitle(String title){
        return bookRepo.getBookDetailsByTitle(title);
    }

    public List<BookDetails> getBookDetailsByAuthors(List<String> authors){
        return bookRepo.getBookDetailsByAuthors(authors);
    }

    public List<BookDetails> getBookDetailsByAuthorsAndTitle(List<String> authors , String title){
        return bookRepo.getBookDetailsByAuthorsAndTitle(authors , title);
    }

    public void deleteBookDetailsByIsbn(String isbn) throws DataAccessException{
        bookRepo.deleteBookDetailsByIsbn(isbn);
    }
}
