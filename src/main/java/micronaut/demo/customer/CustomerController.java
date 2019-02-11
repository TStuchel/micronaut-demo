package micronaut.demo.customer;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;
import micronaut.demo.customer.contract.CustomerDTO;
import micronaut.demo.customer.entity.Customer;

import javax.inject.Inject;

/**
 * This REST API controller is responsible for providing an API for managing Customer entities.
 * <p>
 * DEVELOPER NOTE: The first layer of a web application is the "API" layer. In this case, this layer is implemented
 * using a REST-ful endpoint that exposes a way to retrieve a Customer object via the Customer's ID.
 * <p>
 * The @Controller annotation tells Micronaut that this class is a "controller" that can handle incoming HTTP requests.
 *
 * @see micronaut.demo.customer.CustomerService next!
 */
@Controller("/v1")
public class CustomerController {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    /**
     * DEVELOPER NOTE: This class has dependencies (requires... needs it to work... can't live without it) with classes
     * from  the next "service" layer of the application called "CustomerService". It's pretty common to have a suffixed
     * naming convention for classes in each layer. Note that this variable is both private and "final". Once the
     * variable is set (in the constructor), it will never be changed again.
     */
    private final CustomerService customerService;
    private final CustomerTranslatorImpl customerTranslator;

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    @Inject
    public CustomerController(CustomerService customerService, CustomerTranslatorImpl customerTranslator) {
        this.customerService = customerService;
        this.customerTranslator = customerTranslator;
    }

    // -----------------------------------------------------------------------------------------------------------------

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Given a customer ID, return a Customer with the given ID.
     * <p>
     * DEVELOPER NOTE: This method is annotated such that any GET requests to the /v1/customers/{customerId} URL will be
     * fed into this method. Notice that is method returns Single<MutableHttpResponse<CustomerDTO>>. This makes this
     * method part of a "reactive" controller, meaning an event loop is used to process this request (like NodeJS) and
     * thus allows this service to handle more concurrent requests.
     * </p>
     */
    @Get(uri = "/customers/{customerId}")
    public Single<MutableHttpResponse<CustomerDTO>> getCustomer(Integer customerId) {

        // DEVELOPER NOTE: Basically returns a function that will be called when the underlying web server (Netty) is
        // ready to process this request.
        return Single.fromCallable(() -> {

            // DEVELOPER NOTE: Keep in mind that every line of code might blow up with an Exception. It's good form
            // to let exceptions bubble up to the controller, where it can decide on what HTTP response to return.
            Customer customer = customerService.getCustomer(customerId);

            // Customer not found?
            if (customer == null) {
                return HttpResponse.notFound();
            }

            // Translate to contract
            CustomerDTO customerDto = customerTranslator.toContract(customer);

            // Return 200-OK and the Customer
            return HttpResponse.ok(customerDto);

        });
    }

    // -----------------------------------------------------------------------------------------------------------------
}
