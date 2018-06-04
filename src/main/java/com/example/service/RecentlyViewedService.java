package com.example.service;

import com.example.model.RecentlyViewed;

import java.util.List;

public interface RecentlyViewedService {

    public void addRecentlyViewed(RecentlyViewed recentlyViewed, int userId);

    public void updateRecentlyViewed(RecentlyViewed recentlyViewed, int userId);

    public RecentlyViewed getRecentlyViewed(int id);

    public void deleteRecentlyViewed(int id);

    public List<RecentlyViewed> getRecentlyViewed();

    public List<RecentlyViewed> getRecentlyViewedByUser(int userId);

    public RecentlyViewed getRecentlyViewedByUserAndBook(int userId, int bookId);
}
