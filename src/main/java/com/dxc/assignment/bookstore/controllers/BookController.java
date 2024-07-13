package com.dxc.assignment.bookstore.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dxc.assignment.bookstore.models.BookDetails;
import com.dxc.assignment.bookstore.models.UserRole;
import com.dxc.assignment.bookstore.services.BookService;
import com.dxc.assignment.bookstore.services.UserService;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@RestController
@RequestMapping(path = "/api")
public class BookController {
    
    @Autowired
    BookService bookSvc;
    
    @Autowired 
    UserService userSvc;

    @PostMapping("/addBook")
    public ResponseEntity<String> insertNewBookDetails(@RequestBody BookDetails bookDetails){
        
        System.out.println("BookDetails Record to Insert >>>>" + bookDetails);

        try{
            bookSvc.insertBookDetails(bookDetails);
        }catch(DataAccessException ex){
            return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build().toString());
        }

        return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("message", "record inserted successfully")
                        .build().toString());
    }

    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBookDetails(@RequestBody BookDetails bookDetails){
        
        System.out.println("BookDetails Record to update >>>>" + bookDetails);

        boolean recordByIsbnExist = bookSvc.getBookDetailsByIsbn(bookDetails.getIsbn());
        
        if(!recordByIsbnExist){
            String message = String.format("Book details with isbn %s is not found" , bookDetails.getIsbn());

            return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("error", message)
                        .build().toString());
        }

        try{
            bookSvc.updateBookDetails(bookDetails);
        }catch(DataAccessException ex){
            return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build().toString());
        }

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("message", "record updated successfully")
                        .build().toString());
    }

    @GetMapping(path="/findBooksByTitle")
    public ResponseEntity<String> findBooksByTitle(@RequestParam String title){
        
        List<BookDetails> bookDetailsList = bookSvc.getBookDetailsByTitle(title);

        if(bookDetailsList.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                    .add("error", "no book details found by this title")
                    .build().toString());
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for(BookDetails bookDetails : bookDetailsList){

            jab.add(bookDetails.toJson());
        }

        return ResponseEntity   
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jab.build().toString());
    }

    @GetMapping(path="/findBooksByAuthors")
    public ResponseEntity<String> findBooksByAuthors(@RequestBody BookDetails bookDetails){
        
        List<BookDetails> bookDetailsList = bookSvc.getBookDetailsByAuthors(bookDetails.getAuthors());

        if(bookDetailsList.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                    .add("error", "no book details found by this/these author(s)")
                    .build().toString());
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for(BookDetails bookDeets : bookDetailsList){

            jab.add(bookDeets.toJson());
        }

        return ResponseEntity   
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jab.build().toString());
    }

    @GetMapping(path="/findBooksByAuthorsAndTitle")
    public ResponseEntity<String> findBooksByAuthorsAndTitle(@RequestBody BookDetails bookDetails){
        
        List<BookDetails> bookDetailsList = bookSvc.getBookDetailsByAuthorsAndTitle(bookDetails.getAuthors() , bookDetails.getTitle());

        if(bookDetailsList.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                    .add("error", "no book details found by this/these author(s) and title")
                    .build().toString());
        }

        JsonArrayBuilder jab = Json.createArrayBuilder();

        for(BookDetails bookDeets : bookDetailsList){

            jab.add(bookDeets.toJson());
        }

        return ResponseEntity   
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jab.build().toString());
    }

    @DeleteMapping(path="/deleteBooksByIsbn")
    public ResponseEntity<String> deleteBooksByIsbn(@RequestParam String username, @RequestParam String isbn){
        
        String userRole = "";
        Optional<UserRole> userRoleOpt = userSvc.findUserRoleByUsername(username);
        if(userRoleOpt.isEmpty()){
            return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Error: Unable to delete record. User not registered.");
        }

        else{ userRole = userRoleOpt.get().getRole(); }

        if(!(userRole.equalsIgnoreCase("Role_Admin"))){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                    .add("error", "you are not authorized to perform delete action")
                    .build().toString());
        }

        boolean recordByIsbnExist = bookSvc.getBookDetailsByIsbn(isbn);
        
        if(!recordByIsbnExist){
            String message = String.format("Book details with isbn %s is not found. Thus no deletion performed" , isbn);

            return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("error", message)
                        .build().toString());
        }

        try{
            bookSvc.deleteBookDetailsByIsbn(isbn);
        }catch(DataAccessException ex){
            return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("error", ex.getMessage())
                        .build().toString());
        }

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Json.createObjectBuilder()
                        .add("message", "record deleted successfully")
                        .build().toString());

    }
}
