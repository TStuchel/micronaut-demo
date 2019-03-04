package com.dbs.micronaut.demo.customer.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DEVELOPER NOTE: This customer class is a "Data Transfer Object" or "DTO". It is the Java implementation of the JSON
 * contract sent to this web service. It is the JSON that defines the contract, not this class. This class is just the
 * "Java interpretation" of the contract. DTO contract classes should not have any business logic in them at all except
 * for perhaps light contract-centric validations.
 */
@Data
public class CustomerDTO {

    // -------------------------------------------------- PROPERTIES ---------------------------------------------------

    /**
     * The unique identifier for this Customer.
     * <p>
     * DEVELOPER NOTE: Properties of a DTO contract should always be an object type, not a primitive (except String,
     * it's  a special case). This allows fields to be absent from JSON during de-serialization and allow for fields
     * with a null value. Primitive types such as integers will will be set to a default value such as zero, which isn't
     * the same as "absent".
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * The full name of the customer.
     * <p>
     * DEVELOPER NOTE: While not required, using @JsonProperty lets you set the property name as seen in the serialized
     * JSON of this class to something different than the Java property. This is useful when refactoring the name of
     * the Java property without having to break the contract.
     */
    @JsonProperty("fullName")
    private String fullName;

    /**
     * A timestamp indicating the last time this record was read from the database.
     * <p>
     * DEVELOPER NOTE: @JsonFormat used here defines the string representation of this ZonedDateTime that will be seen
     * in the JSON. This isn't the whole story, though. You must also register the jsr310 module with any ObjectMapper
     * that might serialize/deserialize this class. This lets ObjectMapper know how to serialize and deserialize
     * ZonedDateTime.
     */
    @JsonProperty("lastReadTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
    private ZonedDateTime lastReadTimestamp;

    /**
     * A list of order numbers associated with this Customer.
     * <p>
     * DEVELOPER NOTE: If there is no "orderNumbers" property in the JSON, then this List will be 'null'. See below.
     */
    @JsonProperty("orderNumbers")
    private List<String> orderNumbers;

    // -----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------- PROPERTY METHODS ------------------------------------------------

    /**
     * Return list of order numbers associated with this customer.
     * <p>
     * DEVELOPER NOTE: The @Data annotation is a Lombok library annotation that would automatically create this method
     * behind the scenes. However, since it is implemented here, Lombok will not create a getOrderNumbers() method and
     * will leave this one as the implementation to use. This method ensures that it's impossible for getOrderNumbers()
     * to return a null, thus the developer doesn't have to worry about null checks when using this List property in
     * case the JSON didn't have the orderNumbers property.
     */
    public List<String> getOrderNumbers() {
        if (orderNumbers == null) {
            orderNumbers = new ArrayList<>();
        }
        return orderNumbers;
    }

    // -----------------------------------------------------------------------------------------------------------------

}
