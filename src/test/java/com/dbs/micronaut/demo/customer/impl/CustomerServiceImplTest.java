package com.dbs.micronaut.demo.customer.impl;

import com.dbs.micronaut.demo.BaseTest;
import com.dbs.micronaut.demo.customer.CustomerRepository;
import com.dbs.micronaut.demo.customer.CustomerService;
import com.dbs.micronaut.demo.customer.entity.Customer;
import com.dbs.micronaut.demo.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * DEVELOPER NOTE:  Note that we don't need Spring... nothing in this test, or the tested class, cares about Spring.
 * This is all basic Mockito and JUnit. Don't involve Spring unless you have to; it just slows down your tests.
 * <p>
 * Comments have been added to this class to indicate the order that JUnit and Mockito execute this class.
 */
class CustomerServiceImplTest extends BaseTest { // <-- (1) JUnit instantiates a new instance of this class for each @Test

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    @Mock // <-- (4) Mockito sees this annotation and will create a Mock instance of this class
    private CustomerRepository customerRepository_mock;

    // -----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    /**
     * Class under test (spied to test protected methods)
     */
    private CustomerService customerService_spy;

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- TEST METHODS --------------------------------------------------

    @BeforeEach // (2) <-- JUnit calls the @BeforeEach method
    public void setup() {
        super.setup(); // <-- (3) This line is executed to have Mockito scan this class for Mockito annotations

        // Create a spy so that protected methods can/may be mocked
        customerService_spy = spy(new CustomerServiceImpl(customerRepository_mock));
    }

    /**
     * GIVEN a valid customer ID and a customer with that ID is in the system
     * WHEN the customer is requested
     * THEN the Customer with the given ID should be returned.
     */
    @Test
    // <-- (5) JUnit calls this test method
    void getCustomer_found() throws BusinessException {

        // GIVEN a valid customer ID and a customer with that ID is in the system
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);
        Integer customerId = expectedCustomer.getCustomerId();

        // Mock dependencies
        doReturn(Optional.of(expectedCustomer)).when(customerRepository_mock).findById(customerId);

        // WHEN the customer is requested
        Customer actualCustomer = customerService_spy.getCustomer(customerId);

        // THEN the Customer with the given ID should be returned.
        assertEquals(expectedCustomer, actualCustomer);

        // Verify dependency mocks
        verify(customerRepository_mock).findById(customerId);
    }

    /**
     * GIVEN a customer ID
     * WHEN the customer is requested, but the customer is NOT in the system
     * THEN null should be returned.
     */
    @Test
    void getCustomer_notFound() throws BusinessException {

        // GIVEN a customer ID
        Integer customerId = podamFactory.manufacturePojo(Integer.class);

        // Mock dependencies
        doReturn(Optional.empty()).when(customerRepository_mock).findById(customerId);

        // WHEN the customer is requested, but the customer is NOT in the system
        Customer actualCustomer = customerService_spy.getCustomer(customerId);

        // THEN null should be returned.
        assertNull(actualCustomer);

        // Verify dependency mocks
        verify(customerRepository_mock).findById(customerId);
    }

    /**
     * GIVEN an invalid customer ID
     * WHEN the customer is requested
     * THEN a BusinessException should be thrown
     * AND it must contain an informative message.
     */
    @Test
    void getCustomer_invalidCustomerId() {

        // GIVEN an invalid customer ID
        Integer customerId = podamFactory.manufacturePojo(Integer.class);

        // Mocked to assume always invalid
        doReturn(false).when(customerService_spy).isValidCustomerId(any());

        // WHEN the customer is requested
        // THEN a BusinessException should be thrown
        BusinessException ex = assertThrows(BusinessException.class, () -> customerService_spy.getCustomer(customerId));

        // AND it must contain an informative message.
        assertEquals(String.format(CustomerService.INVALID_CUSTOMER_ID, customerId), ex.getMessage());
    }

    /**
     * GIVEN a customer ID
     * WHEN the customer ID is checked to see if it is valid
     * THEN false should be returned if the customer ID is null
     * AND false should be returned if the customer ID is a negative number
     * AND true should be returned if the customer ID is a positive number
     * <p>
     * DEVELOPER NOTE: Notice the use of @ParameterizedTest and @CsvSource here. This will cause JUnit to call this
     * method once for each of the (comma-delimited) strings in the given array.
     */
    @ParameterizedTest
    @CsvSource({", false", "-999, false", "0, false", "1234, true"})
    void validCustomerId(Integer customerId, boolean expected) {
        assertEquals(expected, customerService_spy.isValidCustomerId(customerId));
    }

    // -----------------------------------------------------------------------------------------------------------------
}