package com.example.service;

import com.example.model.CurrentlyReading;

import java.util.List;

public interface CurrentlyReadingService {

    public void addCurrentlyReading(CurrentlyReading currentlyReading);

    public void updateCurrentlyReading(CurrentlyReading currentlyReading);

    public CurrentlyReading getCurrentlyReading(int id);

    public void deleteCurrentlyReading(int id);

    public List<CurrentlyReading> getCurrentlyReading();

    public List<CurrentlyReading> getCurrentlyReadingByUser(int userId);
}
