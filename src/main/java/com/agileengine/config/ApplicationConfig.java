package com.agileengine.config;

import com.agileengine.model.Account;
import com.agileengine.model.Transaction;
import com.agileengine.repository.AccountRepository;
import com.agileengine.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean
   public AccountRepository accountRepository(){
        List<Account> accounts = Collections.singletonList(new Account(1L,0.0));
        return  new AccountRepository(accounts);
    }

    @Bean
    public TransactionRepository transactionRepository(){
    return new TransactionRepository(new HashMap<>());
    }




}
