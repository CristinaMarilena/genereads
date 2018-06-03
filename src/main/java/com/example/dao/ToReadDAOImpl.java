package com.example.dao;

import com.example.model.ToRead;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ToReadDAOImpl implements ToReadDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addToRead(ToRead toRead) {
        getCurrentSession().save(toRead);
    }

    public void updateToRead(ToRead toRead) {
        ToRead toReadToUpdate = getToRead(toRead.getToReadId());
        toReadToUpdate.setBookId(toRead.getBookId());
        toReadToUpdate.setUserId(toRead.getUserId());
        getCurrentSession().update(toReadToUpdate);
    }

    public ToRead getToRead(int id) {
        ToRead toRead = (ToRead) getCurrentSession().get(ToRead.class, id);
        return toRead;
    }

    public void deleteToRead(int id) {
        ToRead ToRead = getToRead(id);
        if (ToRead != null)
            getCurrentSession().delete(ToRead);
    }

    public List<ToRead> getToRead() {
        return getCurrentSession().createQuery("from ToRead").list();
    }

    @Override
    public List<ToRead> getToReadByUser(int userid) {
        List<ToRead> toRead = getCurrentSession().createQuery("from ToRead where userId=:UserId").setParameter("UserId", userid).list();
        if(!toRead.isEmpty())
            return toRead;
        else return null;
    }

}