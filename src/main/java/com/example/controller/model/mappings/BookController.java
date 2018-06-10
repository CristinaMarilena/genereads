package com.example.controller.model.mappings;

import com.example.model.Book;
import com.example.model.BookCategory;
import com.example.service.BookAuthorService;
import com.example.service.BookService;
import com.example.user.accesed.AccessedBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("api/v1/")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookAuthorService bookAuthorService;

    @Autowired
    private AccessedBook accessedBook;

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

    @RequestMapping(value = "books/byurl/{apiurlid}/**", method = RequestMethod.GET)
    public Book getByUrl(@PathVariable String apiurlid){
        return bookService.getBookByUrl(apiurlid);
    }

    @RequestMapping(value = "books/byaccessedurl", method = RequestMethod.GET)
    public Book getByUrl(){

        return bookService.getBookByUrl(accessedBook.getBookUrl());
    }

    @RequestMapping(value = "books/author/byurl/{apiurlid}", method = RequestMethod.GET)
    public List<String> getAuthorByBookUrl(@PathVariable String apiurlid){
       return bookAuthorService.getAuthorByBook(apiurlid);
    }

    @RequestMapping(value = "books/category/byurl/{bookurl}", method = RequestMethod.GET)
    public BookCategory getCategoryByBookUrl(@PathVariable String bookurl){
        return bookService.getCategoryByBookUrl(bookurl);
    }
}