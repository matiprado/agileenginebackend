package com.agileengine.service;

import com.agileengine.Application;
import com.agileengine.model.Account;
import com.agileengine.model.Transaction;
import com.agileengine.model.TypeTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;


    @Test
    public  void addDebitTransaction() {
        Transaction addDebitTransaction = transactionService.addDebitTransaction(new Account(1L,0.0),10.00);
        assertNotNull(addDebitTransaction);
        assertEquals(TypeTransaction.DEBIT, addDebitTransaction.getTypeTransaction());
        assertEquals(Double.valueOf(10.00), addDebitTransaction.getAmount());
        assertEquals(Long.valueOf(1), addDebitTransaction.getAccount().getId());
    }

    @Test
    public  void addCreditTransaction() {
        Transaction addDebitTransaction = transactionService.addCreditTransaction(new Account(1L,0.0),10.00);
        assertNotNull(addDebitTransaction);
        assertEquals(TypeTransaction.CREDIT, addDebitTransaction.getTypeTransaction());
        assertEquals(Double.valueOf(10.00), addDebitTransaction.getAmount());
        assertEquals(Long.valueOf(1), addDebitTransaction.getAccount().getId());
    }

}
