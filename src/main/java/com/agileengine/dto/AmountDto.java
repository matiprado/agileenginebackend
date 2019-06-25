package com.agileengine.dto;

public class AmountDto {

    public AmountDto() {
    }

    public AmountDto(Double value) {
        this.value = value;
    }

    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
