package micronaut.demo.customer;

import micronaut.demo.customer.entity.Customer;
import micronaut.demo.exception.BusinessException;

public interface CustomerService {

    Customer getCustomer(Integer customerId) throws BusinessException;

    boolean isValidCustomerId(final Integer customerId);
}
