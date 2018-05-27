package com.example.dao;

import com.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addBook(Book book) {
        getCurrentSession().save(book);
    }

    public void updateBook(Book book) {
        Book bookToUpdate = getBook(book.getBookId());
        bookToUpdate.setApiUrl(book.getApiUrl());
        bookToUpdate.setBookImage(book.getBookImage());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthors(book.getAuthors());
        bookToUpdate.setGenres(book.getGenres());
        getCurrentSession().update(bookToUpdate);
    }

    public Book getBook(int id) {
        Book book = (Book) getCurrentSession().get(Book.class, id);
        return book;
    }

    public void deleteBook(int id) {
        Book Book = getBook(id);
        if (Book != null)
            getCurrentSession().delete(Book);
    }

    public List<Book> getBooks() {
        return getCurrentSession().createQuery("from Book").list();
    }

}