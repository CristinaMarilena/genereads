package com.example.dao;

import com.example.model.CurrentlyReading;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrentlyReadingDAOImpl implements CurrentlyReadingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addCurrentlyReading(CurrentlyReading currentlyReading) {
        getCurrentSession().save(currentlyReading);
    }

    public void updateCurrentlyReading(CurrentlyReading currentlyReading) {
        CurrentlyReading currentlyReadingToUpdate = getCurrentlyReading(currentlyReading.getCurrentlyReadingId());
        currentlyReadingToUpdate.setBookId(currentlyReading.getBookId());
        currentlyReadingToUpdate.setUserId(currentlyReading.getUserId());
        getCurrentSession().update(currentlyReadingToUpdate);
    }

    public CurrentlyReading getCurrentlyReading(int id) {
        CurrentlyReading currentlyReading = (CurrentlyReading) getCurrentSession().get(CurrentlyReading.class, id);
        return currentlyReading;
    }

    public void deleteCurrentlyReading(int id) {
        CurrentlyReading CurrentlyReading = getCurrentlyReading(id);
        if (CurrentlyReading != null)
            getCurrentSession().delete(CurrentlyReading);
    }

    public List<CurrentlyReading> getCurrentlyReading() {
        return getCurrentSession().createQuery("from CurrentlyReading").list();
    }

    @Override
    public List<CurrentlyReading> getCurrentlyReadingByUser(int userid) {
        List<CurrentlyReading> currentlyReading = getCurrentSession().createQuery("from CurrentlyReading where userId=:UserId").setParameter("UserId", userid).list();
        if(!currentlyReading.isEmpty())
            return currentlyReading;
        else return null;
    }

    @Override
    public CurrentlyReading getCurrentlyReadingByUserAndBook(int userId, int bookId) {
        List<CurrentlyReading> currentlyReadingList = getCurrentSession().createQuery("from CurrentlyReading where userId=:UserId and bookId=:BookId")
                .setParameter("UserId", userId)
                .setParameter("BookId", bookId)
                .list();
        if(!currentlyReadingList.isEmpty())
            return currentlyReadingList.get(0);
        else return null;
    }
}