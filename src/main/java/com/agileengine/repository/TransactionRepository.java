package com.agileengine.repository;

import com.agileengine.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TransactionRepository {
    private Map<Long, List<Transaction>> transactions;

    public TransactionRepository(Map<Long, List<Transaction>> transactions) {
        this.transactions = transactions;
    }

    public Map<Long, List<Transaction>> findAll() {
        return new HashMap<>(transactions);
    }

    public Optional<List<Transaction>> findByAccountId(Long accountId) {
        List<Transaction> transactionsByAccount = transactions.get(accountId);
        if (transactionsByAccount != null) {
            return Optional.of(transactionsByAccount);
        } else {
            return Optional.empty();
        }
    }

    public Transaction insertTransaction(Transaction transaction) {
        List<Transaction> transactionsByAccount = transactions.get(transaction.getAccount().getId());
        if (transactionsByAccount != null) {
            long id = (long) transactionsByAccount.size();
            transaction.setId(id);
            transactionsByAccount.add(transaction);
        } else {
            transactionsByAccount = new LinkedList<>();
            transaction.setId(0L);
            transactionsByAccount.add(transaction);
            transactions.put(transaction.getAccount().getId(), transactionsByAccount);
        }

        return transaction;
    }


}
