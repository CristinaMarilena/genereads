package com.example.examples;

import com.example.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAccountExample {

    @Autowired
    SessionFactory sessionFactory;


    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }


    public void addAccount() {

        Session session = getCurrentSession();

        try {

            session.save(new Account(15, "ddss", "dwssdwadwqadssddw"));

            System.out.println("\n.......Records Saved Successfully To The Database.......\n");

            // Committing The Transactions To The Database
            session.getTransaction().commit();
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
