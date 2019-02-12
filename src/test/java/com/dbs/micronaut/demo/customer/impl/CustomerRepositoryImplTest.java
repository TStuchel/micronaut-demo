package com.dbs.micronaut.demo.customer.impl;

import com.dbs.micronaut.demo.BaseTest;
import com.dbs.micronaut.demo.customer.CustomerRepository;
import com.dbs.micronaut.demo.customer.entity.Customer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * DEVELOPER NOTES: This test class uses the @MicronautTest annotation to test the JPA-based CustomerRepositoryImpl.
 * Unlike the Service test, this test needs Micronaut to initialize the JPA EntityManager (to talk to the database).
 * <p>
 * There's some magic under the covers of this test. The H2 database engine is an in-memory database that knows SQL. It
 * only lives as long as the unit test runs, but for all intents and purposes JPA thinks that it's a real database. In
 * addition, because the Customer (and Order) classes are annotated as @Entity classes, JPA will automatically create
 * the correct schema/table structure in memory. All that was needed was to include the H2 library in the build.gradle
 * file.
 */
@MicronautTest
class CustomerRepositoryImplTest extends BaseTest {

    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    @Inject
    private EntityManager entityManager;

    /**
     * Class under test (spied to test protected methods)
     */
    @Inject
    private CustomerRepository customerRepository;

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- TEST METHODS --------------------------------------------------

    /**
     * Initialize the test
     */
    @BeforeEach
    void beforeEach() {
        super.setup();
    }

    /**
     * GIVEN a Customer with a given ID is in the database
     * WHEN an attempt is made to read the Customer record from the database
     * THEN the customer record with the given ID should be read from the database and returned as a Customer object.
     */
    @Test
    void getCustomer_success() {

        // GIVEN a Customer with a given ID is in the database
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);
        entityManager.persist(expectedCustomer);
        Integer customerId = expectedCustomer.getCustomerId();

        // WHEN an attempt is made to read the Customer record from the database
        Customer actualCustomer = customerRepository.findById(customerId).orElse(null);

        // THEN the customer record with the given ID should be read from the database and returned as a Customer object.
        assertEquals(expectedCustomer, actualCustomer);
    }

    /**
     * GIVEN a Customer with a given ID is NOT in the database
     * WHEN an attempt is made to read the Customer record from the database
     * THEN the null should be returned.
     */
    @Test
    void getCustomer_notFound() {

        // GIVEN a Customer with a given ID is NOT in the database
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);

        // WHEN an attempt is made to read the Customer record from the database
        Optional<Customer> actualCustomer = customerRepository.findById(expectedCustomer.getCustomerId());

        // THEN the null should be returned.
        assertFalse(actualCustomer.isPresent());
    }

    // -----------------------------------------------------------------------------------------------------------------
}
