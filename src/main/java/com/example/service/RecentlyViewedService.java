package com.example.service;

import com.example.model.RecentlyViewed;

import java.util.List;

public interface RecentlyViewedService {

    public void addRecentlyViewed(RecentlyViewed recentlyViewed);

    public void updateRecentlyViewed(RecentlyViewed recentlyViewed);

    public RecentlyViewed getRecentlyViewed(int id);

    public void deleteRecentlyViewed(int id);

    public List<RecentlyViewed> getRecentlyViewed();

    public List<RecentlyViewed> getRecentlyViewedByUser(int userId);
}
