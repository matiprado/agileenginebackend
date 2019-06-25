package com.agileengine.repository;

import com.agileengine.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    private  List<Account> accounts;

    public AccountRepository(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    public Optional<Account> findById(Long accountId) {
        return  accounts.stream()
                .filter(account -> accountId.equals(account.getId())).findFirst();
    }
}
