package me.truong.dto;

import lombok.Data;

@Data
public class ProductInput {
    private String productName;
    private int quantity;
    private double unitPrice;
    private String description;
    private double discount;
    private short status;
    private Long categoryId;
}