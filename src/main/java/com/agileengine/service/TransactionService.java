package com.agileengine.service;

import com.agileengine.exception.NegativeAmountException;
import com.agileengine.model.Account;
import com.agileengine.model.Transaction;
import com.agileengine.model.TypeTransaction;
import com.agileengine.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.agileengine.model.TypeTransaction.CREDIT;
import static com.agileengine.model.TypeTransaction.DEBIT;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<Transaction> findByAccountId(Long accountId) {
        List<Transaction> result;
        validateIfAccountExist(accountId);
        readLock.lock();
        try {
            result = transactionRepository.findByAccountId(accountId).orElse(new ArrayList<>());
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public Transaction addDebitTransaction(Account account, Double amount) {
        return createTransaction(account, amount, DEBIT);
    }

    public Transaction addCreditTransaction(Account account, Double amount) {
        return createTransaction(account, amount, CREDIT);
    }

    private Transaction createTransaction(Account account, Double amount, TypeTransaction debit) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        transaction.setTypeTransaction(debit);
        Transaction transactionToReturn;
        writeLock.lock();
        try {
            transactionToReturn = transactionRepository.insertTransaction(transaction);
        } finally {
            writeLock.unlock();
        }
        return transactionToReturn;
    }

    private void validateIfAccountExist(Long accountId) {
        accountService.findById(accountId);
    }

}
