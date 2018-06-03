package com.example.service;

import com.example.dao.RecentlyViewedDAO;
import com.example.model.RecentlyViewed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecentlyViewedServiceImpl implements RecentlyViewedService {

    @Autowired
    private RecentlyViewedDAO recentlyViewedDAO;

    public void addRecentlyViewed(RecentlyViewed recentlyViewed) {
        recentlyViewedDAO.addRecentlyViewed(recentlyViewed);
    }

    public void updateRecentlyViewed(RecentlyViewed RecentlyViewed) {
        recentlyViewedDAO.updateRecentlyViewed(RecentlyViewed);
    }

    public RecentlyViewed getRecentlyViewed(int id) {
        RecentlyViewed recentlyViewed = recentlyViewedDAO.getRecentlyViewed(id);
        return recentlyViewed;
    }

    public void deleteRecentlyViewed(int id) {
        recentlyViewedDAO.deleteRecentlyViewed(id);
    }

    public List<RecentlyViewed> getRecentlyViewed() {
        return recentlyViewedDAO.getRecentlyViewed();
    }

    public List<RecentlyViewed> getRecentlyViewedByUser(int userId) {
        return recentlyViewedDAO.getRecentlyViewedByUser(userId);
    }
}