package me.truong.dto;

import lombok.Data;

@Data
public class ProductUpdateInput {
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private String description;
    private Double discount;
    private Short status;
    private Long categoryId;
}