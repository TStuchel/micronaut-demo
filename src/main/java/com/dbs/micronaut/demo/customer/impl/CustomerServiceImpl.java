package com.dbs.micronaut.demo.customer.impl;

import com.dbs.micronaut.demo.customer.CustomerRepository;
import com.dbs.micronaut.demo.customer.CustomerService;
import com.dbs.micronaut.demo.customer.entity.Customer;
import com.dbs.micronaut.demo.exception.BusinessException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implements all application business functionality related to the maintenance of Customer entities.
 */
@Singleton
public class CustomerServiceImpl implements CustomerService {

    /**
     * DEVELOPER NOTE: Micronaut testing likes having implementations (this class) and interfaces (the CustomerService
     * interface) to be defined separately. It's a common, and often preferred, way to architect code. That way, all of
     * your code references interfaces, not concrete classes, and the dependency injection engine (in this case,
     * Micronaut) will find and use this implementation wherever the interface is used automatically.
     *
     * The @Singleton annotation tells Micronaut to only instantiate one of this class, which is therefore going to be
     * used by every web request passing through this app... concurrently, multi-threaded, etc.
     */

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    /**
     * DEVELOPER NOTE: Take note of the "private" (nobody needs to mess with this dependency) and "final" (the variable
     * may only be set once, in this case, by the constructor). Also notice that other than the reference to this class'
     * dependency, this class has no NO MEMBER VARIABLES. That's because in web services, every class should be both a
     * singleton (just one) and stateless (doesn't have its own variables). The only exception are business entities,
     * and even these only get to live as long as a single web request.
     */

    private final CustomerRepository customerRepository;

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    /**
     * DEVELOPER NOTE: The use of @Inject here tells Micronaut to find an implementation of CustomerRepostitory from
     * ~somewhere~ in the classpath prior to instantiating this class. There happens to only be one implementation found
     * in CustomerRepostitoryImpl, so that's the one it will make and use to call this constructor. This pattern is
     * called "constructor injection" and this pattern is preferred over using the alternative "field injection".
     */

    @Inject
    CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Return a Customer entity with the given ID.
     *
     * @return null if the Customer is not found
     * @throws BusinessException if the given ID is not a valid customer ID.
     */
    public Customer getCustomer(Integer customerId) throws BusinessException {

        /**
         * DEVELOPER NOTE: There's not much going on here, is there? *This* method doesn't have much business logic, so
         * it's mostly just a pass-through to the CustomerRepositoryImpl. There might be a temptation to have the Controller
         * talk directly to the Repository, but this is a bad idea. It's best to respect a 3-layered  application
         * architecture. Inevitably, you'll have to add some real business logic, and you'll need a place to put it.
         */

        // Business validation
        if (!isValidCustomerId(customerId)) {
            throw new BusinessException(String.format(INVALID_CUSTOMER_ID, customerId));
        }

        return customerRepository.findById(customerId).orElse(null);
    }

    /**
     * Returns whether or not the given customer ID is a valid customer ID.
     */
    public boolean isValidCustomerId(Integer customerId) {
        return (customerId != null) && (customerId > 0);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
