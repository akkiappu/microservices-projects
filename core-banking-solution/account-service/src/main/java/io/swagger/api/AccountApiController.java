package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-22T13:21:02.260Z")

@Controller
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final Map<Long, Account> accountData = new HashMap<>();

    private final AtomicLong autoAccountId = new AtomicLong(0);

    @Autowired
    private RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PostConstruct
    public void initAccountData(){
        accountData.put(autoAccountId.incrementAndGet(),new Account().id(autoAccountId.get()).customerId(1l).accountBalance(new BigDecimal(100)).accountType(Account.AccountTypeEnum.CURRENT));
        accountData.put(autoAccountId.incrementAndGet(),new Account().id(autoAccountId.get()).customerId(2l).accountBalance(new BigDecimal(200)).accountType(Account.AccountTypeEnum.SAVING));
    }

    public ResponseEntity<Account> addAccount(@ApiParam(value = "Account object that needs to be added to the bank" ,required=true )  @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Customer customer = restTemplate.getForObject("http://customer-service/customer/" + body.getCustomerId(), Customer.class);
                if (customer.getId().equals(body.getCustomerId())) {
                    accountData.put(autoAccountId.incrementAndGet(),new Account().id(autoAccountId.get()).customerId(body.getCustomerId()).accountBalance(body.getAccountBalance()).accountType(body.getAccountType()));
                    String responseData = objectMapper.writeValueAsString(accountData.get(autoAccountId.get()));
                    return new ResponseEntity<Account>(objectMapper.readValue(responseData, Account.class), HttpStatus.CREATED);
                }
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteAccount(@ApiParam(value = "The account id that needs to be deleted",required=true) @PathVariable("accountId") Integer accountId) {
        String accept = request.getHeader("Accept");
        if(accept != null && accountData.containsKey(accountId.longValue())) {
            accountData.computeIfPresent(accountId.longValue(), (k, v) -> null);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Account>> findAccounts(@ApiParam(value = "Tags used to filter the result") @Valid @RequestParam(value = "tags", required = false) List<String> tags,@ApiParam(value = "maximum number of results to return") @Valid @RequestParam(value = "limit", required = false) Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null ) {
            try {

                String data = objectMapper.writeValueAsString(accountData.values());
                return new ResponseEntity<List<Account>>(objectMapper.readValue(data, List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Customer>> findAccountsByCustomerIds(@ApiParam(value = "Id's values that need to be considered for filter") @Valid @RequestParam(value = "customerIds", required = false) List<String> customerIds) {
        String accept = request.getHeader("Accept");
        if (accept != null) {
            try {

                List list = Collections.emptyList();
                for(String customerId : customerIds){
                    Long cId = Long.valueOf(customerId);
                    list.addAll(accountData.values().stream().filter(c ->c.getCustomerId().equals(cId)).collect(Collectors.toList()));
                }
                String responseData = objectMapper.writeValueAsString(list);
                return new ResponseEntity<List<Customer>>(objectMapper.readValue(responseData, List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Customer>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Account> getAccountById(@ApiParam(value = "ID of account to return",required=true) @PathVariable("accountId") Long accountId) {
        String accept = request.getHeader("Accept");
        if (accept != null) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue(objectMapper.writeValueAsString(accountData.get(accountId)), Account.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> updateAccount(@ApiParam(value = "account id that need to be updated",required=true) @PathVariable("accountId") Integer accountId,@ApiParam(value = "Updated account object" ,required=true )  @Valid @RequestBody Account body) {
        String accept = request.getHeader("Accept");
        if(accountData.containsKey(accountId.longValue())) {
            body.id(accountId.longValue());
            accountData.computeIfPresent(accountId.longValue(), (k, v) -> body);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
