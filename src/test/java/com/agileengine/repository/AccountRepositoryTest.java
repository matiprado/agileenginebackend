package com.agileengine.repository;

import com.agileengine.model.Account;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryTest {

    @Test
    public void findOneExisting() {
        Optional<Account> account  = new AccountRepository(Collections.singletonList(new Account(1L,0.0))).findById(1L);
        assertTrue(account.isPresent());
        Account toTest = account.get();
        assertEquals(1L, (long) toTest.getId());
    }

    @Test
    public   void findOneNotExisting() {
        Optional<Account> account  = new AccountRepository(Collections.singletonList(new Account(1L,0.0))).findById(11L);
        assertFalse(account.isPresent());
    }

    @Test
    public  void findAll() {
        List<Account> all = new AccountRepository(Collections.singletonList(new Account(1L,0.0))).findAll();
        assertFalse(all.isEmpty());
    }
}
