This app shows:
- how to create CLRunner by @Bean & by @Component
- how CLRUnner can be used to pre-populate DB in 2 ways:
    - how to use @@PersitanceContext javax,persistence.EntityManager  to em.persist(User u) to DB
    - how to persist with SpringData repo using JpaRepository out-of-the-box save(User u)