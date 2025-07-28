package com.megabank.banking.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Legacy Account Entity
 * 
 * This entity demonstrates legacy JPA patterns that need modernization:
 * - Uses old Date instead of LocalDateTime
 * - Missing validation annotations
 * - No audit fields
 * - Uses deprecated JPA patterns
 * 
 * TODO: Use GitHub Copilot to modernize this entity:
 * - "@workspace modernize this JPA entity to use Spring Boot 3.2 and Java 21"
 * - "/fix replace deprecated Date with LocalDateTime"
 * - "@workspace add validation annotations and audit fields"
 */
@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "account_number", unique = true)
    private String accountNumber;
    
    @Column(name = "account_type")
    private String accountType; // Should be enum
    
    @Column(name = "balance", precision = 19, scale = 2)
    private BigDecimal balance;
    
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; // Legacy - should be LocalDateTime
    
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified; // Legacy - should be LocalDateTime
    
    @Column(name = "status")
    private String status; // Should be enum
    
    @Column(name = "interest_rate")
    private Double interestRate; // Legacy - should be BigDecimal
    
    // Legacy constructor pattern
    public Account() {
        this.createdDate = new Date();
        this.lastModified = new Date();
        this.status = "ACTIVE";
    }
    
    public Account(String accountNumber, String accountType, BigDecimal balance, Long customerId) {
        this();
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }
    
    // Legacy getters and setters (should be replaced with records or Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { 
        this.balance = balance; 
        this.lastModified = new Date(); // Legacy pattern
    }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public Date getLastModified() { return lastModified; }
    public void setLastModified(Date lastModified) { this.lastModified = lastModified; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
        this.lastModified = new Date(); // Legacy pattern
    }
    
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    
    // Legacy business logic mixed with entity
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    public void deactivate() {
        this.status = "INACTIVE";
        this.lastModified = new Date();
    }
}