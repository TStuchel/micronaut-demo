package micronaut.demo.customer;

import io.micronaut.test.annotation.MicronautTest;
import micronaut.demo.BaseTest;
import micronaut.demo.customer.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DEVELOPER NOTES: This test class uses the @DataJpaTest annotation to test the JPA-based CustomerRepository. Unlike
 * the Controller and Service test, this test needs Spring to initialize both a JPA EntityManager (to talk to the
 * database) as well as automatically implement the CustomerRepository interface. For this reason, this test class uses
 * field-style dependency injection and does not use Mockito.
 * <p>
 * There's some magic under the covers of this test. The H2 database engine is an in-memory database that knows SQL. It
 * only lives as long as the unit test runs, but for all intents and purposes JPA thinks that it's a real database. In
 * addition, because the Customer (and Order) classes are annotated as @Entity classes, Spring/JPA will automatically
 * create the correct schema/table structure in memory. All that was needed was to include the H2 library in the
 * build.gradle file.
 */
@MicronautTest
class CustomerRepositoryTest extends BaseTest {

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
    void getCustomer() {

        // GIVEN a Customer with a given ID is in the database
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);
        entityManager.persist(expectedCustomer);
        Integer customerId = expectedCustomer.getCustomerId();

        // WHEN an attempt is made to read the Customer record from the database
        Customer actualCustomer = customerRepository.findById(customerId).orElse(null);

        // THEN the customer record with the given ID should be read from the database and returned as a Customer object.
        assertEquals(expectedCustomer, actualCustomer);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
