package com.example.dao;

import com.example.model.BookGenre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookGenreDAOImpl implements BookGenreDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addBookGenre(BookGenre bookGenre) {
        getCurrentSession().save(bookGenre);
    }

    public void updateBookGenre(BookGenre bookGenre) {
        BookGenre bookGenreToUpdate = getBookGenre(bookGenre.getGenreId());
        bookGenreToUpdate.setGenreName(bookGenre.getGenreName());
        bookGenreToUpdate.setBooks(bookGenre.getBooks());
        getCurrentSession().update(bookGenreToUpdate);
    }

    public BookGenre getBookGenre(int id) {
        BookGenre bookGenre = (BookGenre) getCurrentSession().get(BookGenre.class, id);
        return bookGenre;
    }

    public void deleteBookGenre(int id) {
        BookGenre BookGenre = getBookGenre(id);
        if (BookGenre != null)
            getCurrentSession().delete(BookGenre);
    }

    public List<BookGenre> getBookGenres() {
        return getCurrentSession().createQuery("from BookGenre").list();
    }

}