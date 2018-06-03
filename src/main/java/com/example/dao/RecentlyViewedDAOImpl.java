package com.example.dao;

import com.example.model.RecentlyViewed;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecentlyViewedDAOImpl implements RecentlyViewedDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addRecentlyViewed(RecentlyViewed recentlyViewed) {
        getCurrentSession().save(recentlyViewed);
    }

    public void updateRecentlyViewed(RecentlyViewed recentlyViewed) {
        RecentlyViewed recentlyViewedToUpdate = getRecentlyViewed(recentlyViewed.getRecentlyViewedId());
        recentlyViewedToUpdate.setBookId(recentlyViewed.getBookId());
        recentlyViewedToUpdate.setUserId(recentlyViewed.getUserId());
        getCurrentSession().update(recentlyViewedToUpdate);
    }

    public RecentlyViewed getRecentlyViewed(int id) {
        RecentlyViewed recentlyViewed = (RecentlyViewed) getCurrentSession().get(RecentlyViewed.class, id);
        return recentlyViewed;
    }

    public void deleteRecentlyViewed(int id) {
        RecentlyViewed RecentlyViewed = getRecentlyViewed(id);
        if (RecentlyViewed != null)
            getCurrentSession().delete(RecentlyViewed);
    }

    public List<RecentlyViewed> getRecentlyViewed() {
        return getCurrentSession().createQuery("from RecentlyViewedService").list();
    }

    @Override
    public List<RecentlyViewed> getRecentlyViewedByUser(int userid) {
        List<RecentlyViewed> recentlyViewed = getCurrentSession().createQuery("from RecentlyViewedService where userId=:UserId").setParameter("UserId", userid).list();
        if(!recentlyViewed.isEmpty())
            return recentlyViewed;
        else return null;
    }

}