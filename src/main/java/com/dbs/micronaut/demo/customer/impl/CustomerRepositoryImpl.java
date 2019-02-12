package com.dbs.micronaut.demo.customer.impl;

import com.dbs.micronaut.demo.customer.CustomerRepository;
import com.dbs.micronaut.demo.customer.entity.Customer;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * This JPA (Java Persistence Architecture) repository interface provides methods to return Customer data from the
 * database.
 * <p>
 * DEVELOPER NOTE: This is the third and "deepest" layer of the application architecture. This layer is sometimes
 * called the "data access layer" (and repositories called "Data Access Objects" or DAO objects). This layer shouldn't
 * have ANY business logic in it at all. The only responsibility of a repository class is to either interface with a
 * database to retrieve and manipulate data, or to interact with an external web service. The service layer of this
 * application doesn't know or care where the repository got its data, or how it got it. It should also be "dumb" and
 * just do its job of putting and pulling data from the external source, not making business or quality decisions about
 * the data.
 *
 * @see Customer
 */
@Singleton
public class CustomerRepositoryImpl implements CustomerRepository {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    /**
     * DEVELOPER NOTE: This injected dependency is the JPA "EntityManager". Think of the EntityManager as an object that
     * wraps the database and hides all interactions with the database. It is through the EntityManager that all
     * entities (business objects) are read from the database and written to the database. The @PersistenceContext is
     * a JPA annotation that indicates that this field should be automatically set (via dependency-injection) to the
     * current EntityManager managing database access.
     */
    @PersistenceContext
    private EntityManager entityManager;

    private final ApplicationConfiguration applicationConfiguration;

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    /**
     * Create a new CustomerRepositoryImpl.
     * <p>
     * DEVELOPER NOTE: The @Inject annotation is a core Java dependency-injection annotation that is equivalent to the
     * Spring annotation @Autowired (in fact, in Spring, you can use @Inject in place of @Autowired).
     */
    @Inject
    CustomerRepositoryImpl(@CurrentSession EntityManager entityManager, ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Return the Customer with the given ID, if found. If not found, then the value inside the Optional returned will
     * be null.
     * <p>
     * DEVELOPER NOTE: If coming from Spring, you'll notice that we had to implement these methods ourselves, instead
     * of letting Spring JPA auto-implement them from a repository interface. That's because Micronaut is compile-time
     * linked, and can't use Java reflection to automatically implement the interface.
     */
    @Transactional(readOnly = true)
    public Optional<Customer> findById(@NotNull Integer id) {
        Optional<Customer> customer = Optional.ofNullable(entityManager.find(Customer.class, id));
        if (customer.isPresent()) {
            customer.get().setLastReadTimestamp(ZonedDateTime.now());
        }
        return customer;
    }

    // -----------------------------------------------------------------------------------------------------------------
}
