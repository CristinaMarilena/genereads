package com.example.controller.model.mappings;

import com.example.model.BookCategory;
import com.example.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class BookCategoryController {

    @Autowired
    private BookCategoryService bookCategoryService;

    @RequestMapping(value = "bookcategories", method = RequestMethod.GET)
    public List<BookCategory> list() {
        return bookCategoryService.getBookCategories();
    }

    @RequestMapping(value = "bookcategories", method = RequestMethod.POST)
    public BookCategory create(@RequestBody BookCategory bookCategory) {
        bookCategoryService.addBookCategory(bookCategory);
        return bookCategory;
    }

    @RequestMapping(value = "bookcategories/{id}", method = RequestMethod.GET)
    public BookCategory get(@PathVariable int id) {
        return bookCategoryService.getBookCategory(id);
    }

    @RequestMapping(value = "bookcategories/{id}", method = RequestMethod.PUT)
    public BookCategory update(@PathVariable int id, @RequestBody BookCategory bookCategory) {
        BookCategory existingBookCategory = bookCategoryService.getBookCategory(id);

        if(bookCategory.getCategoryName() != null){
            existingBookCategory.setCategoryName(bookCategory.getCategoryName());
        }

        bookCategoryService.updateBookCategory(existingBookCategory);
        return bookCategory;
    }

    @RequestMapping(value = "bookcategories/{id}", method = RequestMethod.DELETE)
    public BookCategory delete(@PathVariable int id) {
        BookCategory existingBookCategory = bookCategoryService.getBookCategory(id);
        bookCategoryService.deleteBookCategory(id);
        return existingBookCategory;
    }

}