package com.mikalai.library.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Mikalai_Churakou on 4/6/2016.
 */
public abstract class GenericDAO {

    @PersistenceContext(name = "libraryPU")
    protected EntityManager em;

    protected Connection getConnection() throws SQLException {
        Session session = em.unwrap(Session.class);
        return session.doReturningWork(conn -> conn);
    }

}
