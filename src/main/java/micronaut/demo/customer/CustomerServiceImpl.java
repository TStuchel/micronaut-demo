package micronaut.demo.customer;

import micronaut.demo.customer.entity.Customer;
import micronaut.demo.exception.BusinessException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomerServiceImpl implements CustomerService {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    /**
     * DEVELOPER NOTE: This class is dependent on the existence of a CustomerRepository object... which will created and
     * injected by Micronaut prior to this object's construction.
     *
     * @see micronaut.demo.customer.CustomerRepository
     */
    private final CustomerRepository customerRepository;

    // -----------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------- VARIABLES ----------------------------------------------------

    public static final String INVALID_CUSTOMER_ID = "Invalid customer ID [%s]";

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    @Inject
    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Given the ID of a customer, return the Customer with that ID. Null is returned if the customer could not be
     * found.
     * <p>
     * DEVELOPER NOTE: There's not much going on here, is there. *This* method doesn't have any business logic, so it's
     * just a pass-through to the CustomerRepository. There might be a temptation to have the Controller talk directly
     * to the Repository, but this is a bad idea. It's best to respect the 3-layered nature of the application
     * architecture. Inevitably, you'll have to add some real business logic, and you'll need a place to put it.
     */
    @Override
    public Customer getCustomer(Integer customerId) throws BusinessException {

        // Business validation
        if (!isValidCustomerId(customerId)) {
            throw new BusinessException(String.format(INVALID_CUSTOMER_ID, customerId));
        }

        return customerRepository.findById(customerId).orElse(null);
    }

    /**
     * Returns whether or not the given customer ID is valid.
     */
    public boolean isValidCustomerId(final Integer customerId) {
        return (customerId != null) && (customerId > 0);
    }

    // -----------------------------------------------------------------------------------------------------------------

}
