package com.example.service;

import com.example.dao.BookCategoryDAO;
import com.example.model.BookCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryDAO bookCategoryDAO;

    public void addBookCategory(BookCategory bookCategory) {
        bookCategoryDAO.addBookCategory(bookCategory);
    }

    public void updateBookCategory(BookCategory BookCategory) {
        bookCategoryDAO.updateBookCategory(BookCategory);
    }

    public BookCategory getBookCategory(int id) {
        BookCategory bookCategory = bookCategoryDAO.getBookCategory(id);
        return bookCategory;
    }

    public void deleteBookCategory(int id) {
        bookCategoryDAO.deleteBookCategory(id);
    }

    public List<BookCategory> getBookCategories() {
        return bookCategoryDAO.getBookCategories();
    }

    @Override
    public BookCategory getCategoryByName(String categoryName) {
        return bookCategoryDAO.getCategoryByName(categoryName);
    }
}