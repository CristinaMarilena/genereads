package com.example.service;

import com.example.dao.BookGenreDAO;
import com.example.model.BookGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookGenreServiceImpl implements BookGenreService {

    @Autowired
    private BookGenreDAO bookGenreDAO;

    public void addBookGenre(BookGenre bookGenre) {
        bookGenreDAO.addBookGenre(bookGenre);
    }

    public void updateBookGenre(BookGenre BookGenre) {
        bookGenreDAO.updateBookGenre(BookGenre);
    }

    public BookGenre getBookGenre(int id) {
        BookGenre bookGenre = bookGenreDAO.getBookGenre(id);
        return bookGenre;
    }

    public void deleteBookGenre(int id) {
        bookGenreDAO.deleteBookGenre(id);
    }

    public List<BookGenre> getBookGenres() {
        return bookGenreDAO.getBookGenres();
    }

    @Override
    public BookGenre getGenreByName(String genreName) {
        return bookGenreDAO.getGenreByName(genreName);
    }
}