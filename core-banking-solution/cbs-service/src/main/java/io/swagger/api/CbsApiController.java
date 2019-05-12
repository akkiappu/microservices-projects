package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.threeten.bp.LocalDate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-22T12:04:48.784Z")

@Controller
public class CbsApiController implements CbsApi {

    private static final Logger log = LoggerFactory.getLogger(CbsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    private final Map<Long, List<Transaction>> transactionData = new HashMap<>();

    private final AtomicLong autoTransactionId = new AtomicLong(0);

    @org.springframework.beans.factory.annotation.Autowired
    public CbsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PostConstruct
    public void initTransactionData(){
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction().transactionDate(LocalDate.now()).valueDate(LocalDate.now()).availableBalance(new BigDecimal(100)).deposit(new BigDecimal(100)).narration("Deposit in self account"));
        transactionData.put(1l,transactions);
    }
    public ResponseEntity<Transaction> cbsDeposit(@ApiParam(value = ""  )  @Valid @RequestBody Body body) {
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            try {
                Account account = restTemplate.getForObject("http://account-service/account/" + body.getAccountId(), Account.class);
                if(account.getId().equals(body.getAccountId())){
                    Account updatedInstance = new Account();
                    updatedInstance.setId(account.getId());
                    updatedInstance.setCustomerId(account.getCustomerId());
                    updatedInstance.setAccountType(account.getAccountType());
                    updatedInstance.setAccountBalance(new BigDecimal(account.getAccountBalance().longValue() + body.getAmount().longValue()));
                    String resourceUrl =
                            "http://account-service/account/" + body.getAccountId();
                    HttpEntity<Account> requestUpdate = new HttpEntity<>(updatedInstance,new HttpHeaders());
                    restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
                    Transaction transaction = new Transaction().transactionDate(LocalDate.now()).valueDate(LocalDate.now()).availableBalance(updatedInstance.getAccountBalance()).deposit(body.getAmount()).narration(body.getComments()).id(autoTransactionId.incrementAndGet());
                    List<Transaction> list = transactionData.get(account.getId());
                    if(list == null){
                        list = new ArrayList<>();
                    }
                    list.add(transaction);
                    transactionData.put(account.getId(),list);
                    String responseData = objectMapper.writeValueAsString(transaction);
                    return new ResponseEntity<Transaction>(objectMapper.readValue(responseData, Transaction.class), HttpStatus.CREATED);
                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
    }



    public ResponseEntity<Transaction> cbsWithdrawl(@ApiParam(value = ""  )  @Valid @RequestBody Body1 body) {
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            try {
                Account account = restTemplate.getForObject("http://account-service/account/" + body.getAccountId(), Account.class);
                if(account.getId().equals(body.getAccountId()) && body.getAmount().longValue() <= account.getAccountBalance().longValue()){
                    Account updatedInstance = new Account();
                    updatedInstance.setId(account.getId());
                    updatedInstance.setCustomerId(account.getCustomerId());
                    updatedInstance.setAccountType(account.getAccountType());
                    updatedInstance.setAccountBalance(new BigDecimal(account.getAccountBalance().longValue() - body.getAmount().longValue()));
                    String resourceUrl =
                            "http://account-service/account/" + body.getAccountId();
                    HttpEntity<Account> requestUpdate = new HttpEntity<>(updatedInstance,new HttpHeaders());
                    restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
                    Transaction transaction = new Transaction().transactionDate(LocalDate.now()).valueDate(LocalDate.now()).availableBalance(updatedInstance.getAccountBalance()).withdrawl(new BigDecimal(body.getAmount().longValue())).narration(body.getComments()).id(autoTransactionId.incrementAndGet());
                    List<Transaction> list = transactionData.get(account.getId());
                    if(list == null){
                        list = new ArrayList<>();
                    }
                    list.add(transaction);
                    transactionData.put(account.getId(),list);
                    String responseData = objectMapper.writeValueAsString(transaction);
                    return new ResponseEntity<Transaction>(objectMapper.readValue(responseData, Transaction.class), HttpStatus.CREATED);

                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Transaction> cbsTransfer(@ApiParam(value = ""  )  @Valid @RequestBody Body2 body) {
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            try {
                Account fromAccount = restTemplate.getForObject("http://account-service/account/" + body.getFromAccountId(), Account.class);
                Account toAccount = restTemplate.getForObject("http://account-service/account/" + body.getToAccountId(), Account.class);
                if(fromAccount.getId().equals(body.getFromAccountId()) && toAccount.getId().equals(body.getToAccountId()) && body.getAmount().longValue() <= fromAccount.getAccountBalance().longValue()){
                    Account updatedFromInstance = new Account();
                    updatedFromInstance.setId(fromAccount.getId());
                    updatedFromInstance.setCustomerId(fromAccount.getCustomerId());
                    updatedFromInstance.setAccountType(fromAccount.getAccountType());
                    updatedFromInstance.setAccountBalance(new BigDecimal(fromAccount.getAccountBalance().longValue() - body.getAmount().longValue()));
                    String resourceUrl =
                            "http://account-service/account/" + body.getFromAccountId();
                    HttpEntity<Account> requestUpdate = new HttpEntity<>(updatedFromInstance,new HttpHeaders());
                    restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
                    Transaction fromTransaction = new Transaction().transactionDate(LocalDate.now()).valueDate(LocalDate.now()).availableBalance(updatedFromInstance.getAccountBalance()).withdrawl(new BigDecimal(body.getAmount().longValue())).narration(body.getComments()).id(autoTransactionId.incrementAndGet());
                    List<Transaction> list = transactionData.get(fromAccount.getId());
                    if(list == null){
                        list = new ArrayList<>();
                    }
                    list.add(fromTransaction);
                    transactionData.put(fromAccount.getId(),list);

                    Account updatedToInstance = new Account();
                    updatedToInstance.setId(toAccount.getId());
                    updatedToInstance.setCustomerId(toAccount.getCustomerId());
                    updatedToInstance.setAccountType(toAccount.getAccountType());
                    updatedToInstance.setAccountBalance(new BigDecimal(toAccount.getAccountBalance().longValue() + body.getAmount().longValue()));
                    resourceUrl =
                            "http://account-service/account/" + body.getToAccountId();
                    HttpEntity<Account> requestToUpdate = new HttpEntity<>(updatedToInstance,new HttpHeaders());
                    restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestToUpdate, Void.class);
                    Transaction toTransaction = new Transaction().transactionDate(LocalDate.now()).valueDate(LocalDate.now()).availableBalance(updatedToInstance.getAccountBalance()).deposit(new BigDecimal(body.getAmount().longValue())).narration(body.getComments()).id(autoTransactionId.incrementAndGet());
                    List<Transaction> listTo = transactionData.get(toAccount.getId());
                    if(list == null){
                        list = new ArrayList<>();
                    }
                    listTo.add(toTransaction);
                    transactionData.put(toAccount.getId(),listTo);

                    String responseData = objectMapper.writeValueAsString(fromTransaction);
                    return new ResponseEntity<Transaction>(objectMapper.readValue(responseData, Transaction.class), HttpStatus.CREATED);

                }
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Transaction>> getStatementByAccountId(@ApiParam(value = "ID of account used for statment generation",required=true) @PathVariable("accountId") Long accountId) {
        String accept = request.getHeader("Accept");

        if (accept != null) {
            try {
                List<Transaction> transactions = transactionData.get(accountId);
                String responseData = objectMapper.writeValueAsString(transactions);
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue(responseData, List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.BAD_REQUEST);
    }

}
