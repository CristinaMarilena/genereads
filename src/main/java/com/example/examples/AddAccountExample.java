package com.example.examples;

import com.example.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddAccountExample {

    @Autowired
    SessionFactory sessionFactory;


    protected Session openSession(){
        return sessionFactory.openSession();
    }


    public void addAccount() {

        Session session = openSession();

        try {
            session.beginTransaction();

            session.save(new Account("gdewhj", "dwssdwadwqadssddw"));

            System.out.println("\n.......Records Saved Successfully To The Database.......\n");


            if (session.getTransaction().getStatus().equals(TransactionStatus.ACTIVE)) {
                session.getTransaction().commit();
            }
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
