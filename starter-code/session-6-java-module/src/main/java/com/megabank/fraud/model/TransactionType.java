package com.megabank.fraud.model;

/**
 * Enumeration of different transaction types
 */
public enum TransactionType {
    PURCHASE("Purchase"),
    WITHDRAWAL("ATM Withdrawal"),
    TRANSFER("Money Transfer"),
    REFUND("Refund"),
    PAYMENT("Bill Payment"),
    DEPOSIT("Deposit");
    
    private final String description;
    
    TransactionType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
