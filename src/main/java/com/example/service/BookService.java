package com.example.service;

import com.example.model.Book;
import com.example.model.BookCategory;

import java.util.List;

public interface BookService {

    public void addBook(Book book);

    public void updateBook(Book book);

    public Book getBook(int id);

    public void deleteBook(int id);

    public List<Book> getBooks();

    public Book getBookByUrl(String url);

    public BookCategory getCategoryByBookUrl(String bookurl);
}
