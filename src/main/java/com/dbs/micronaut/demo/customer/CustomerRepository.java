package com.dbs.micronaut.demo.customer;

import com.dbs.micronaut.demo.customer.entity.Customer;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Defines all application data access functionality related to the persistence and retrieval of Customer entities from
 * a persistence store or external system (such as a database or external web service).
 */
public interface CustomerRepository {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Return the Customer with the given ID, if found. If not found, then the value inside the Optional returned will
     * be null.
     */
    Optional<Customer> findById(@NotNull Integer id);

    // -----------------------------------------------------------------------------------------------------------------
}
