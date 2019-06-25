package com.agileengine.model;

import java.util.Objects;

public class Account {
    private Long id;
    private Double amount;


    public Account(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Account(Account account) {
        this.id = account.getId();
        this.amount = account.getAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(amount, account.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
