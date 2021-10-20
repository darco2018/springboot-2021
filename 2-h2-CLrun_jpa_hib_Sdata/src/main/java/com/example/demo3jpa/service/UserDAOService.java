package com.example.demo3jpa.service;

import com.example.demo3jpa.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/* The class is annotated with @Repository to enable exception translation from JPA exceptions to Spring’s DataAccessException hierarchy. */
@Repository // alias for @Component
@Transactional // can be used on methods too
public class UserDAOService {

    // EM tracks only entities added to PersistanceCOntext. You add them by persisting.
    // EM - interface used to interact with the persistence context.
    @PersistenceContext
    private EntityManager em;

    /*
     * @PersistenceContext: A persistence context handles a set of entities which hold data to be persisted
     * in some persistence store (e.g. a database). In particular, the context is aware of the different states
     *  an entity can have (e.g. managed, detached) in relation to both the context and
     * the underlying persistence store.
     *
     * The set of entities that can be managed by a given EntityManager instance is defined by a !!!persistence unit!!!
     * A persistence unit defines the set of all classes that are related or grouped by the application,
     * and which must be placed together in their mapping to a single database.     *
     * */
    public long insert(User user) {
        // Make user entity instance managed and persistent
        em.persist(user);
        return user.getId();
    }
}
