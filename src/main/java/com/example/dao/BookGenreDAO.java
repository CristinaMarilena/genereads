package com.example.dao;

import com.example.model.BookGenre;

import java.util.List;

public interface BookGenreDAO {

    public void addBookGenre(BookGenre bookGenre);

    public void updateBookGenre(BookGenre bookGenre);

    public BookGenre getBookGenre(int id);

    public void deleteBookGenre(int id);

    public List<BookGenre> getBookGenres();

    public BookGenre getGenreByName(String genreName);
}
