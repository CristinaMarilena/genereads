package com.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class BookAuthorDAOImpl implements BookAuthorDAO{

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public List<String> getAuthorByBook(String bookUrl) {
      String bookId = getCurrentSession().createSQLQuery("select bookId from book where apiurl='" + bookUrl + "';").list().get(0).toString();

      List<Integer> authorsIds = getCurrentSession().createSQLQuery("select authorId from books_authors where bookId=" + bookId + ";").list();

      List<String> authors = new LinkedList<>();
      for(Integer author:authorsIds) {
          authors.add(getCurrentSession().createSQLQuery("select name from author where authorId=" + author + ";").list().get(0).toString());
      }
        if(!authors.isEmpty()){
            return authors;
        } else return null;
    }
}
