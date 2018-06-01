package com.example.dao;

import com.example.model.BookCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookCategoryDAOImpl implements BookCategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addBookCategory(BookCategory bookCategory) {
        getCurrentSession().save(bookCategory);
    }

    public void updateBookCategory(BookCategory bookCategory) {
        BookCategory bookCategoryToUpdate = getBookCategory(bookCategory.getCategoryId());
        bookCategoryToUpdate.setCategoryName(bookCategory.getCategoryName());
        bookCategoryToUpdate.setBooks(bookCategory.getBooks());
        getCurrentSession().update(bookCategoryToUpdate);
    }

    public BookCategory getBookCategory(int id) {
        BookCategory bookCategory = (BookCategory) getCurrentSession().get(BookCategory.class, id);
        return bookCategory;
    }

    public void deleteBookCategory(int id) {
        BookCategory BookCategory = getBookCategory(id);
        if (BookCategory != null)
            getCurrentSession().delete(BookCategory);
    }

    public List<BookCategory> getBookCategories() {
        return getCurrentSession().createQuery("from BookCategory").list();
    }

    @Override
    public BookCategory getCategoryByName(String categoryName) {
        List<BookCategory> categories = getCurrentSession().createQuery("from BookCategory where categoryname=:CategoryName").setParameter("CategoryName", categoryName).list();
        if(!categories.isEmpty())
            return categories.get(0);
        else return null;
    }
}