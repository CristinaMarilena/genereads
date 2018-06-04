package com.example.dao;

import java.util.List;

public interface BookAuthorDAO {
    
    public List<String> getAuthorByBook(String bookUrl);
}
