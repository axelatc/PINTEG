package com.atc.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaUtils {
    private JpaUtils() {}

    private static EntityManagerFactory emf;
    private final static String PERSISTENCE_UNIT = "PersistUnit_Shapp";

    public static EntityManager createEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf.createEntityManager();
    }
}
