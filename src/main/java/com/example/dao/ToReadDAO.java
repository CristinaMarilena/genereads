package com.example.dao;

import com.example.model.ToRead;

import java.util.List;

public interface ToReadDAO {

    public void addToRead(ToRead roRead);

    public void updateToRead(ToRead roRead);

    public ToRead getToRead(int id);

    public void deleteToRead(int id);

    public List<ToRead> getToRead();

    public List<ToRead> getToReadByUser(int userId);
}
