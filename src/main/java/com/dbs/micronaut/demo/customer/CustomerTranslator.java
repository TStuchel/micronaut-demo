package com.dbs.micronaut.demo.customer;

import com.dbs.micronaut.demo.customer.contract.CustomerDTO;
import com.dbs.micronaut.demo.customer.entity.Customer;

/**
 * Translate to and from CustomerDTO web service contracts and Customer business entities.
 */
public interface CustomerTranslator {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Translate the given CustomerDTO to a new Customer entity.
     */
    Customer toEntity(CustomerDTO customerDto);

    /**
     * Translate the given Customer to a new CustomerDTO contract.
     */
    CustomerDTO toContract(Customer customer);

    // -----------------------------------------------------------------------------------------------------------------
}
