package com.example.dao;

import com.example.model.Book;
import com.example.model.BookCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
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
        bookToUpdate.setCategories(book.getCategories());
        bookToUpdate.setLanguage(book.getLanguage());
        bookToUpdate.setPageCount(book.getPageCount());
        bookToUpdate.setPublishedDate(book.getPublishedDate());
        bookToUpdate.setPreviewLink(book.getPreviewLink());
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

    @Override
    public Book findByUrl(String url) {
        List<Book> bookList = getCurrentSession().createQuery("from Book where apiurl=:Apiurl").setParameter("Apiurl", url).list();
       if(!bookList.isEmpty())
        return bookList.get(0);
       else return null;
    }

    @Override
    public BookCategory getCategoryByBookUrl(String url) {
        String bookId = getCurrentSession().createSQLQuery("select bookId from book where apiurl='" + url + "';").list().get(0).toString();

        List<Integer> categoryId = getCurrentSession().createSQLQuery("select categoryid from books_categories where bookId=" + bookId + ";").list();

        List<BookCategory> categories = new LinkedList<>();
        for(Integer categ:categoryId) {
            BookCategory bookCategory = new BookCategory();
            bookCategory.setCategoryName(getCurrentSession().createSQLQuery("select categoryname from bookcategory where categoryid=" + categ + ";").list().get(0).toString());
            categories.add(bookCategory);
        }
        if(!categories.isEmpty()){
            return categories.get(0);
        } else return null;    }
}