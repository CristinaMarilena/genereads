package com.example.dao;

import com.example.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDAOImpl implements AuthorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addAuthor(Author author) {
        getCurrentSession().save(author);
    }

    public void updateAuthor(Author author) {
        Author authorToUpdate = getAuthor(author.getAuthorId());
        authorToUpdate.setName(author.getName());
        authorToUpdate.setBooks(author.getBooks());
        getCurrentSession().update(authorToUpdate);
    }

    public Author getAuthor(int id) {
        Author author = (Author) getCurrentSession().get(Author.class, id);
        return author;
    }

    public void deleteAuthor(int id) {
        Author Author = getAuthor(id);
        if (Author != null)
            getCurrentSession().delete(Author);
    }

    public List<Author> getAuthors() {
        return getCurrentSession().createQuery("from Author").list();
    }

    public Author findByName(String name){
        List<Author> authors = getCurrentSession().createQuery("from Author where name=:Name").setParameter("Name", name).list();
        if(!authors.isEmpty())
            return authors.get(0);
        else return null;
    }
}