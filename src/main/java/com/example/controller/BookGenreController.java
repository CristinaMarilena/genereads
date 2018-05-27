package com.example.controller;

import com.example.model.BookGenre;
import com.example.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class BookGenreController {

    @Autowired
    private BookGenreService bookGenreService;

    @RequestMapping(value = "bookgenres", method = RequestMethod.GET)
    public List<BookGenre> list() {
        return bookGenreService.getBookGenres();
    }

    @RequestMapping(value = "bookgenres", method = RequestMethod.POST)
    public BookGenre create(@RequestBody BookGenre bookGenre) {
        bookGenreService.addBookGenre(bookGenre);
        return bookGenre;
    }

    @RequestMapping(value = "bookgenres/{id}", method = RequestMethod.GET)
    public BookGenre get(@PathVariable int id) {
        return bookGenreService.getBookGenre(id);
    }

    @RequestMapping(value = "bookgenres/{id}", method = RequestMethod.PUT)
    public BookGenre update(@PathVariable int id, @RequestBody BookGenre bookGenre) {
        BookGenre existingBookGenre = bookGenreService.getBookGenre(id);

        if(bookGenre.getGenreName() != null){
            existingBookGenre.setGenreName(bookGenre.getGenreName());
        }

        bookGenreService.updateBookGenre(existingBookGenre);
        return bookGenre;
    }

    @RequestMapping(value = "bookgenres/{id}", method = RequestMethod.DELETE)
    public BookGenre delete(@PathVariable int id) {
        BookGenre existingBookGenre = bookGenreService.getBookGenre(id);
        bookGenreService.deleteBookGenre(id);
        return existingBookGenre;
    }

}