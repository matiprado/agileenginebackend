package com.agileengine.repository;

import com.agileengine.model.Account;
import com.agileengine.model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionRepositoryTest {

    @Test
    public void findByAccountId() {
        Optional<List<Transaction>> transactions  = new TransactionRepository(new HashMap<>()).findByAccountId(1L);
        assertFalse(transactions.isPresent());
    }

    @Test
    public   void findAll() {
        Map<Long, List<Transaction>> transactions  = new TransactionRepository(new HashMap<>()).findAll();
        assertTrue(transactions.isEmpty());
    }

    @Test
    public  void insertTransaction() {
        Transaction transaction = new Transaction();
        Account account = new Account(1L,0.0);
        transaction.setAccount(account);
        TransactionRepository transactionRepository = new TransactionRepository(new HashMap<>());
        transactionRepository.insertTransaction(transaction);
        Map<Long, List<Transaction>> transactions  = transactionRepository.findAll();
        assertFalse(transactions.isEmpty());
        assertEquals(transactions.size(),1);
    }
}
