package com.agileengine.service;

import com.agileengine.Application;
import com.agileengine.dto.AmountDto;
import com.agileengine.exception.AccountNotFoundException;
import com.agileengine.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {Application.class})
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public  void findAccount() {
        assertNotNull(accountService.findById(1L));
    }


    @Test
    public void incrementAndDecrementAmountSynchronizedTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> accountService.incrementAmount(1L,new AmountDto(1.0)));
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        Account account = accountService.findById(1L);
        assertEquals(1000, (double) account.getAmount());
    }

    @Test
    public void decrementAmountSynchronizedTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for(int i = 0; i < 1000; i++) {
            executorService.submit(() -> accountService.decrementAmount(1L,new AmountDto(1.0)));
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        Account account = accountService.findById(1L);
        assertEquals(0.0, (double) account.getAmount());
    }

}
