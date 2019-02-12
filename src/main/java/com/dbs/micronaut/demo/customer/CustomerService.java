package com.dbs.micronaut.demo.customer;

import com.dbs.micronaut.demo.customer.entity.Customer;
import com.dbs.micronaut.demo.exception.BusinessException;

/**
 * Defines all application business functionality related to the maintenance of Customer entities.
 */
public interface CustomerService {

    // -------------------------------------------------- CONSTANTS ----------------------------------------------------

    /**
     * DEVELOPER NOTE: Beware of the temptation to create a class or interface that consists of nothing but a list of
     * static constants (the dreaded "Constants.java"). A class of nothing but constants ends up creating a class that
     * EVERYONE requires.
     * <p>
     * Note the use of the square-brackets surrounding the customer ID. This is useful because it shows potential
     * whitespace, nulls, and empty strings that might exist in the data and makes it easier to see at a glance.
     */

    String INVALID_CUSTOMER_ID = "Invalid customer ID [%s].";

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Return a Customer entity with the given ID.
     *
     * @return null if the Customer is not found
     * @throws BusinessException if the given ID is not a valid customer ID.
     */
    Customer getCustomer(Integer customerId) throws BusinessException;

    /**
     * Returns whether or not the given customer ID is a valid customer ID.
     */
    boolean isValidCustomerId(Integer customerId);

    // -----------------------------------------------------------------------------------------------------------------
}
