package micronaut.demo.customer;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import micronaut.demo.BaseTest;
import micronaut.demo.customer.contract.CustomerDTO;
import micronaut.demo.customer.entity.Customer;
import micronaut.demo.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
public class CustomerControllerTest extends BaseTest {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    @Inject
    private CustomerService customerService_mock;

    @MockBean(CustomerServiceImpl.class)
    protected CustomerService customerService() {
        return mock(CustomerService.class);
    }

    @Inject
    private CustomerTranslator customerTranslator_spy;

    @MockBean(CustomerTranslatorImpl.class)
    protected CustomerTranslator customerTranslator() {
        return spy(new CustomerTranslatorImpl());
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    /**
     * The URI for retrieving a customer
     */
    private static final String V1_GET_CUSTOMER_URI = "/v1/customers/%s";

    /**
     * The Micronaut client that will be used to connect to the server
     */
    @Inject
    @Client("/")
    private RxHttpClient client;

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
     * GIVEN a valid customer ID and a customer with that ID is in the system
     * WHEN the GET customer API endpoint is called
     * THEN the Customer with the given ID should be returned.
     */
    @Test
    void getCustomer_success() throws BusinessException {

        // GIVEN a valid customer ID and a customer with that ID is in the system
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);
        Integer customerId = expectedCustomer.getCustomerId();

        // Mock dependencies
        doReturn(expectedCustomer).when(customerService_mock).getCustomer(customerId);

        // WHEN the customer API endpoint is called
        HttpResponse<CustomerDTO> response = client.toBlocking().exchange(HttpRequest.GET(String.format(V1_GET_CUSTOMER_URI, customerId)), CustomerDTO.class);
        assertEquals(response.getStatus(), HttpStatus.OK);

        // THEN the Customer with the given ID should be returned.
        CustomerDTO actualCustomerDto = response.body();
        assertEquals(expectedCustomer.getCustomerId(), actualCustomerDto.getId());
        assertEquals(expectedCustomer.getFullName(), actualCustomerDto.getFullName());
        assertTrue(expectedCustomer.getLastReadTimestamp().isEqual(actualCustomerDto.getLastReadTimestamp()));
    }

    /**
     * GIVEN a valid customer ID and a customer with that ID is not in the system
     * WHEN the GET customer API endpoint is called
     * THEN a NOT FOUND status should be returned.
     */
    @Test
    void getCustomer_notFound() throws BusinessException {

        // GIVEN a valid customer ID and a customer with that ID is in the system
        Customer expectedCustomer = podamFactory.manufacturePojo(Customer.class);
        Integer customerId = expectedCustomer.getCustomerId();

        // Dependency Mocks
        doReturn(null).when(customerService_mock).getCustomer(customerId);

        // WHEN the customer API endpoint is called
        // THEN a NOT FOUND status should be returned.
        try {
            client.toBlocking().exchange(HttpRequest.GET(String.format(V1_GET_CUSTOMER_URI, customerId)), CustomerDTO.class);
            fail("Should have thrown a HttpClientResponseException");
        } catch (HttpClientResponseException ex) {
            assertEquals(ex.getStatus(), HttpStatus.NOT_FOUND);
        }

        // Verify dependency mocks
        verify(customerService_mock).getCustomer(customerId);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
