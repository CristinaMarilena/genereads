package com.example.service;

import com.example.dao.CurrentlyReadingDAO;
import com.example.model.CurrentlyReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CurrentlyReadingServiceImpl implements CurrentlyReadingService {

    @Autowired
    private CurrentlyReadingDAO currentlyReadingDAO;

    public void addCurrentlyReading(CurrentlyReading currentlyReading) {
        currentlyReadingDAO.addCurrentlyReading(currentlyReading);
    }

    public void updateCurrentlyReading(CurrentlyReading CurrentlyReading) {
        currentlyReadingDAO.updateCurrentlyReading(CurrentlyReading);
    }

    public CurrentlyReading getCurrentlyReading(int id) {
        CurrentlyReading currentlyReading = currentlyReadingDAO.getCurrentlyReading(id);
        return currentlyReading;
    }

    public void deleteCurrentlyReading(int id) {
        currentlyReadingDAO.deleteCurrentlyReading(id);
    }

    public List<CurrentlyReading> getCurrentlyReading() {
        return currentlyReadingDAO.getCurrentlyReading();
    }

    public List<CurrentlyReading> getCurrentlyReadingByUser(int userId) {
        return currentlyReadingDAO.getCurrentlyReadingByUser(userId);
    }
}