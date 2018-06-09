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

    public void addRecentlyViewed(RecentlyViewed recentlyViewed, int userId) {
        List<RecentlyViewed> recentlyViewedlist = getRecentlyViewedByUser(userId);
        if(recentlyViewedlist != null) {
            RecentlyViewed firstRecentlyViewed = recentlyViewedlist.get(0);
            deleteRecentlyViewed(firstRecentlyViewed.getRecentlyViewedId());
        }
        getCurrentSession().save(recentlyViewed);
    }

    public void updateRecentlyViewed(RecentlyViewed recentlyViewed, int userId) {
        RecentlyViewed recentlyViewedToUpdate = getRecentlyViewed(recentlyViewed.getRecentlyViewedId());
        recentlyViewedToUpdate.setBookId(recentlyViewed.getBookId());
        recentlyViewedToUpdate.setUserId(recentlyViewed.getUserId());
        List<RecentlyViewed> recentlyViewedlist = getRecentlyViewedByUser(userId);
        if(!recentlyViewedlist.isEmpty()) {
            RecentlyViewed firstRecentlyViewed = recentlyViewedlist.get(0);
            deleteRecentlyViewed(firstRecentlyViewed.getRecentlyViewedId());
        }
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
        List<RecentlyViewed> recentlyViewed = getCurrentSession().createQuery("from RecentlyViewed where userId=:UserId order by recentlyViewedId DESC")
                .setParameter("UserId", userid)
                .list();
        if(!recentlyViewed.isEmpty())
            return recentlyViewed;
        else return null;
    }

    @Override
    public RecentlyViewed getRecentlyViewedByUserAndBook(int userid, int bookId) {
        List<RecentlyViewed> recentlyViewed = getCurrentSession().createQuery("from RecentlyViewed where userId=:UserId and bookId=:BookId")
                .setParameter("UserId", userid)
                .setParameter("BookId", bookId)
                .list();
        if(!recentlyViewed.isEmpty())
            return recentlyViewed.get(0);
        else return null;
    }

}