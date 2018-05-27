package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "books", method = RequestMethod.GET)
    public List<Book> list() {
        return bookService.getBooks();
    }

    @RequestMapping(value = "books", method = RequestMethod.POST)
    public Book create(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.GET)
    public Book get(@PathVariable int id) {
        return bookService.getBook(id);
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.PUT)
    public Book update(@PathVariable int id, @RequestBody Book book) {
        Book existingBook = bookService.getBook(id);

        if(book.getApiUrl() != null){
            existingBook.setApiUrl(book.getApiUrl());
        }
        if(book.getAuthors() != null){
            existingBook.setAuthors(book.getAuthors());
        }
        if(book.getBookImage() != null){
            existingBook.setBookImage(book.getBookImage());
        }
        if(book.getTitle() != null){
            existingBook.setTitle(book.getTitle());
        }

        bookService.updateBook(existingBook);
        return book;
    }

    @RequestMapping(value = "books/{id}", method = RequestMethod.DELETE)
    public Book delete(@PathVariable int id) {
        Book existingBook = bookService.getBook(id);
        bookService.deleteBook(id);
        return existingBook;
    }

}