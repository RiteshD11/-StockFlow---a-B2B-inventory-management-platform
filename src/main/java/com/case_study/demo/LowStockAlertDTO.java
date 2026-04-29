package com.case_study.demo;

public class LowStockAlertDTO {

    private Long productId;
    private String productName;
    private String sku;
    private Long warehouseId;
    private String warehouseName;
    private int currentStock;
    private int threshold;
    private int daysUntilStockout;
    private Supplier supplier;

    // constructor + getters
}