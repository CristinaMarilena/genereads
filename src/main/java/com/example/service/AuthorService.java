package com.example.service;

import com.example.model.Author;

import java.util.List;

public interface AuthorService {

    public void addAuthor(Author author);

    public void updateAuthor(Author author);

    public Author getAuthor(int id);

    public void deleteAuthor(int id);

    public List<Author> getAuthors();

    public Author getAuthorByName(String name);
}
