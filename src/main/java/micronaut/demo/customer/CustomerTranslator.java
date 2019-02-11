package micronaut.demo.customer;

import micronaut.demo.customer.contract.CustomerDTO;
import micronaut.demo.customer.entity.Customer;

public interface CustomerTranslator {

    Customer toEntity(CustomerDTO customerDto);

    CustomerDTO toContract(Customer customer);
}
