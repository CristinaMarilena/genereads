package com.example.service;

import com.example.dao.ToReadDAO;
import com.example.model.ToRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ToReadServiceImpl implements ToReadService {

    @Autowired
    private ToReadDAO toReadDAO;

    public void addToRead(ToRead toRead) {
        toReadDAO.addToRead(toRead);
    }

    public void updateToRead(ToRead ToRead) {
        toReadDAO.updateToRead(ToRead);
    }

    public ToRead getToRead(int id) {
        ToRead toRead = toReadDAO.getToRead(id);
        return toRead;
    }

    public void deleteToRead(int id) {
        toReadDAO.deleteToRead(id);
    }

    public List<ToRead> getToRead() {
        return toReadDAO.getToRead();
    }

    public List<ToRead> getToReadByUser(int userId) {
        return toReadDAO.getToReadByUser(userId);
    }
}