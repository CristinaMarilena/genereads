package com.example.dao;

import com.example.model.BookCategory;

import java.util.List;

public interface BookCategoryDAO {

    public void addBookCategory(BookCategory bookCategory);

    public void updateBookCategory(BookCategory bookCategory);

    public BookCategory getBookCategory(int id);

    public void deleteBookCategory(int id);

    public List<BookCategory> getBookCategories();

    public BookCategory getCategoryByName(String categoryName);
}
