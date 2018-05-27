package com.example.service;

import com.example.model.Book;

import java.util.List;

public interface BookService {

    public void addBook(Book book);

    public void updateBook(Book book);

    public Book getBook(int id);

    public void deleteBook(int id);

    public List<Book> getBooks();
}
