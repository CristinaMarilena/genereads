package com.example.service;

import com.example.model.ToRead;

import java.util.List;

public interface ToReadService {

    public void addToRead(ToRead toRead);

    public void updateToRead(ToRead toRead);

    public ToRead getToRead(int id);

    public void deleteToRead(int id);

    public List<ToRead> getToRead();

    public List<ToRead> getToReadByUser(int userId);

    public ToRead getToReadByUserAndBook(int userId, int bookId);
}
