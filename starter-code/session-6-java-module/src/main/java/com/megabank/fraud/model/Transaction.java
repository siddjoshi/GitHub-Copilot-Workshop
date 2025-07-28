package com.megabank.fraud.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transaction entity representing a financial transaction to be analyzed for fraud
 */
@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Transaction ID cannot be blank")
    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;
    
    @NotBlank(message = "Account ID cannot be blank")
    @Column(name = "account_id", nullable = false)
    private String accountId;
    
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    
    @NotBlank(message = "Merchant ID cannot be blank")
    @Column(name = "merchant_id", nullable = false)
    private String merchantId;
    
    @Column(name = "merchant_category")
    private String merchantCategory;
    
    @Column(name = "transaction_timestamp", nullable = false)
    private LocalDateTime transactionTimestamp;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "device_fingerprint")
    private String deviceFingerprint;
    
    @Column(name = "location_country")
    private String locationCountry;
    
    @Column(name = "location_city")
    private String locationCity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    
    @Column(name = "is_card_present")
    private Boolean isCardPresent;
    
    // Constructors
    public Transaction() {}
    
    public Transaction(String transactionId, String accountId, BigDecimal amount, 
                      String currency, String merchantId, LocalDateTime transactionTimestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.transactionTimestamp = transactionTimestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    
    public String getMerchantCategory() { return merchantCategory; }
    public void setMerchantCategory(String merchantCategory) { this.merchantCategory = merchantCategory; }
    
    public LocalDateTime getTransactionTimestamp() { return transactionTimestamp; }
    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) { this.transactionTimestamp = transactionTimestamp; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getDeviceFingerprint() { return deviceFingerprint; }
    public void setDeviceFingerprint(String deviceFingerprint) { this.deviceFingerprint = deviceFingerprint; }
    
    public String getLocationCountry() { return locationCountry; }
    public void setLocationCountry(String locationCountry) { this.locationCountry = locationCountry; }
    
    public String getLocationCity() { return locationCity; }
    public void setLocationCity(String locationCity) { this.locationCity = locationCity; }
    
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    
    public Boolean getIsCardPresent() { return isCardPresent; }
    public void setIsCardPresent(Boolean isCardPresent) { this.isCardPresent = isCardPresent; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", transactionTimestamp=" + transactionTimestamp +
                '}';
    }
}
