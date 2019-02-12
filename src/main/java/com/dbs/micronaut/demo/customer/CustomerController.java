package com.dbs.micronaut.demo.customer;

import com.dbs.micronaut.demo.customer.contract.CustomerDTO;
import io.micronaut.http.MutableHttpResponse;
import io.reactivex.Single;

/**
 * This REST API controller is responsible for providing an API for managing Customer entities.
 */
public interface CustomerController {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Return a CustomerDTO of a Customer with the given ID.
     */
    Single<MutableHttpResponse<CustomerDTO>> getCustomer(Integer customerId);

    // -----------------------------------------------------------------------------------------------------------------
}
