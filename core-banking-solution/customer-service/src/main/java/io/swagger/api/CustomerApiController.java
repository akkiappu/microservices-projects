package io.swagger.api;

import io.swagger.model.Body;
import io.swagger.model.Customer;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.LocalDate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-22T13:23:54.373Z")

@Controller
public class CustomerApiController implements CustomerApi {

    private static final Logger log = LoggerFactory.getLogger(CustomerApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final Map<Long, Customer> customerData = new HashMap<>();

    private final AtomicLong autoCustomerId = new AtomicLong(0);

    @org.springframework.beans.factory.annotation.Autowired
    public CustomerApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PostConstruct
    public void initCustomerData(){
        customerData.put(autoCustomerId.incrementAndGet(),new Customer().firstName("Akhilesh").lastName("Singh").email("akhilesh@sap.com").dateOfBirth(LocalDate.parse("1984-12-27")).mobile("8050373367").id(autoCustomerId.get()));
        customerData.put(autoCustomerId.incrementAndGet(),new Customer().firstName("iswar").lastName("sahu").email("iswar@sap.com").dateOfBirth(LocalDate.parse("1983-11-17")).mobile("9876787789").id(autoCustomerId.get()));
    }

    public ResponseEntity<Customer> addCustomer(@ApiParam(value = "Customer object that needs to be added to the bank" ,required=true )  @Valid @RequestBody Body body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                customerData.put(autoCustomerId.incrementAndGet(),new Customer().firstName(body.getFirstName()).lastName(body.getLastName()).email(body.getEmail()).dateOfBirth(body.getDateOfBirth()).mobile(body.getMobile()).id(autoCustomerId.get()));
                String data = objectMapper.writeValueAsString(customerData.get(autoCustomerId.get()));
                return new ResponseEntity<Customer>(objectMapper.readValue(data, Customer.class), HttpStatus.CREATED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Customer>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> createCustomersWithArrayInput(@ApiParam(value = "List of customer object" ,required=true )  @Valid @RequestBody List<Customer> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> createCustomersWithListInput(@ApiParam(value = "List of customer object" ,required=true )  @Valid @RequestBody List<Customer> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteCustomer(@ApiParam(value = "The customer id that needs to be deleted",required=true) @PathVariable("customerId") Integer customerId) {
        String accept = request.getHeader("Accept");
        customerData.computeIfPresent(customerId.longValue(),(k,v)-> null);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<List<Customer>> findCustomers(@ApiParam(value = "Tags used to filter the result") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "maximum number of results to return") @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null ) {
            try {

                String data = objectMapper.writeValueAsString(customerData.values());
                return new ResponseEntity<List<Customer>>(objectMapper.readValue(data, List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Customer>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> findCustomersByName(@ApiParam(value = "Name values that need to be considered for filter") @Valid @RequestParam(value = "name", required = false) List<String> name) {
        String accept = request.getHeader("Accept");
        if (accept != null) {
            try {
                List list = Collections.emptyList();
                for(String data : name) {
                    list.addAll(customerData.values().stream().filter(c -> c.getFirstName().contains(data) || c.getLastName().contains(data)).collect(Collectors.toList()));
                }

                String responseData = objectMapper.writeValueAsString(list);
                return new ResponseEntity<List<Customer>>(objectMapper.readValue(responseData, List.class), HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Customer>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Customer> getCustomerById(@ApiParam(value = "ID of customer to return",required=true) @PathVariable("customerId") Long customerId) {
        String accept = request.getHeader("Accept");
        if (accept != null) {
            try {

                return new ResponseEntity<Customer>(objectMapper.readValue(objectMapper.writeValueAsString(customerData.get(customerId)), Customer.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updateCustomer(@ApiParam(value = "customer id that need to be updated",required=true) @PathVariable("customerId") Integer customerId,@ApiParam(value = "Updated customer object" ,required=true )  @Valid @RequestBody Customer body) {
        String accept = request.getHeader("Accept");
        if(customerData.containsKey(customerId.longValue())) {
            body.id(customerId.longValue());
            customerData.computeIfPresent(customerId.longValue(), (k, v) -> body);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
