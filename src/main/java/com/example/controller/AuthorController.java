package com.example.controller;

import com.example.model.Author;
import com.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "authors", method = RequestMethod.GET)
    public List<Author> list() {
        return authorService.getAuthors();
    }

    @RequestMapping(value = "authors", method = RequestMethod.POST)
    public Author create(@RequestBody Author author) {
        authorService.addAuthor(author);
        return author;
    }

    @RequestMapping(value = "authors/{id}", method = RequestMethod.GET)
    public Author get(@PathVariable int id) {
        return authorService.getAuthor(id);
    }

    @RequestMapping(value = "authors/{id}", method = RequestMethod.PUT)
    public Author update(@PathVariable int id, @RequestBody Author author) {
        Author existingAuthor = authorService.getAuthor(id);

        if(author.getBooks() != null){
            existingAuthor.setBooks(author.getBooks());
        }
        if(author.getName() != null){
            existingAuthor.setName(author.getName());
        }

        authorService.updateAuthor(existingAuthor);
        return author;
    }

    @RequestMapping(value = "authors/{id}", method = RequestMethod.DELETE)
    public Author delete(@PathVariable int id) {
        Author existingAuthor = authorService.getAuthor(id);
        authorService.deleteAuthor(id);
        return existingAuthor;
    }

}