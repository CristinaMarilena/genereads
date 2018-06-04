package com.example.service;

import com.example.dao.BookAuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class BookAuthorServiceImpl implements BookAuthorService{

    @Autowired
    private BookAuthorDAO bookAuthorDAO;

    @Override
    public List<String> getAuthorByBook(String apiUrl) {
        return bookAuthorDAO.getAuthorByBook(apiUrl);
    }
}
