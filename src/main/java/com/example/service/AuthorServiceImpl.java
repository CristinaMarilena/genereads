package com.example.service;

import com.example.dao.AuthorDAO;
import com.example.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    public void addAuthor(Author author) {
        authorDAO.addAuthor(author);
    }

    public void updateAuthor(Author Author) {
        authorDAO.updateAuthor(Author);
    }

    public Author getAuthor(int id) {
        Author author = authorDAO.getAuthor(id);
        return author;
    }

    public void deleteAuthor(int id) {
        authorDAO.deleteAuthor(id);
    }

    public List<Author> getAuthors() {
        return authorDAO.getAuthors();
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorDAO.findByName(name);
    }
}