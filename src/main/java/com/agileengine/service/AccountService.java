package com.agileengine.service;

import com.agileengine.dto.AmountDto;
import com.agileengine.exception.AccountNotFoundException;
import com.agileengine.exception.InsufficientAmountException;
import com.agileengine.exception.NegativeAmountException;
import com.agileengine.model.Account;
import com.agileengine.repository.AccountRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class AccountService {
    private static final Log logger = LogFactory.getLog(AccountService.class);

    private final AccountRepository accountRepository;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public Account findById(Long id) {
        logger.debug(String.format("Searching for account with id: %s", id));

        Account result;
        readLock.lock();
        try {
            result = new Account(accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException(String.format("Account %s not found", id))));
        } finally {
            readLock.unlock();
        }
        return result;
    }

    public void incrementAmount(Long accountId, AmountDto amountDto) {
        logger.debug(String.format("Searching for account with id: %s", accountId));
        validateAmount(amountDto.getValue());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account %s not found", accountId)));
        writeLock.lock();
        try {
            double amount = amountDto.getValue() + account.getAmount();
            account.setAmount(amount);
            logger.debug(String.format("Update account amount with id %s with amount: %s", accountId, amount));
        } finally {
            writeLock.unlock();
        }
    }

    public void decrementAmount(Long accountId, AmountDto amountDto) {
        logger.debug(String.format("Searching for account with id: %s", accountId));
        validateAmount(amountDto.getValue());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account %s not found", accountId)));
        writeLock.lock();
        try {
            double amount = account.getAmount() - amountDto.getValue();
            if (amount >= 0) {
                account.setAmount(amount);
                logger.debug(String.format("Update account amount with id %s to amount: %s", accountId, amount));
            } else {
                throw new InsufficientAmountException(String.format("The amount should be positive %s ", amount));
            }

        } finally {
            writeLock.unlock();
        }
    }

    private void  validateAmount(Double amount){
        if (amount < 0){
            throw new NegativeAmountException(String.format("amount should be positive %s is invalid" ,amount));
        }
    }
}
