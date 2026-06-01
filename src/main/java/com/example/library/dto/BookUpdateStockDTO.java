package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookUpdateStockDTO {

    @NotNull(message = "Stock không được để trống")
    @Min(value = 0, message = "Stock phải là số nguyên không âm")
    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
