package com.agileengine.controller;

import com.agileengine.dto.AmountDto;
import com.agileengine.model.Account;
import com.agileengine.model.Transaction;
import com.agileengine.service.AccountService;
import com.agileengine.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@ComponentScan("com.agileengine")
@EnableAutoConfiguration
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/transaction/account/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transaction>> transactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.findByAccountId(accountId);
        if (transactions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/transaction/account/{accountId}/credit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Transaction> updateCredit(@PathVariable Long accountId, @RequestBody AmountDto amountDto) {
        Double amount = amountDto.getValue();
        Account account = accountService.findById(accountId);
        accountService.incrementAmount(account.getId(), amountDto);
        Transaction transaction = transactionService.addCreditTransaction(account, amount);
        return ResponseEntity.ok(transaction);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/transaction/account/{accountId}/debit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Transaction> updateDebit(@PathVariable Long accountId, @RequestBody AmountDto amountDto) {
        Double amount = amountDto.getValue();
        Account account = accountService.findById(accountId);
        accountService.decrementAmount(account.getId(), amountDto);
        Transaction transaction = transactionService.addDebitTransaction(account, amount);
        return ResponseEntity.ok(transaction);
    }

}
