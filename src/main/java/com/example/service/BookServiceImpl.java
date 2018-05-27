package com.example.service;

import com.example.dao.BookDAO;
import com.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDAO bookDAO;

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public void updateBook(Book Book) {
        bookDAO.updateBook(Book);
    }

    public Book getBook(int id) {
        Book book = bookDAO.getBook(id);
        return book;
    }

    public void deleteBook(int id) {
        bookDAO.deleteBook(id);
    }

    public List<Book> getBooks() {
        return bookDAO.getBooks();
    }
}