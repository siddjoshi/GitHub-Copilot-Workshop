package com.megabank.fraud.service;

import com.megabank.fraud.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Fraud Detection Service Tests")
class FraudDetectionServiceTest {
    
    private FraudDetectionService fraudDetectionService;
    
    @BeforeEach
    void setUp() {
        fraudDetectionService = new FraudDetectionService();
    }
    
    @Test
    @DisplayName("Should approve low-risk transaction")
    void shouldApproveLowRiskTransaction() {
        // Given
        Transaction transaction = createBasicTransaction("TXN001", "ACC001", new BigDecimal("100.00"));
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertNotNull(result);
        assertEquals("TXN001", result.getTransactionId());
        assertEquals(RiskLevel.LOW, result.getRiskLevel());
        assertEquals("APPROVE", result.getDecision());
        assertTrue(result.getRiskScore() < 0.3);
    }
    
    @Test
    @DisplayName("Should flag high-amount transaction as high risk")
    void shouldFlagHighAmountTransactionAsHighRisk() {
        // Given
        Transaction transaction = createBasicTransaction("TXN002", "ACC002", new BigDecimal("15000.00"));
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertNotNull(result);
        assertTrue(result.getRiskScore() >= 0.3, "High amount should increase risk score");
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("High transaction amount")));
    }
    
    @Test
    @DisplayName("Should detect high-risk country transactions")
    void shouldDetectHighRiskCountryTransactions() {
        // Given
        Transaction transaction = createBasicTransaction("TXN003", "ACC003", new BigDecimal("500.00"));
        transaction.setLocationCountry("XX"); // High-risk country
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("high-risk country")));
    }
    
    @Test
    @DisplayName("Should detect unusual hours transactions")
    void shouldDetectUnusualHoursTransactions() {
        // Given
        Transaction transaction = createBasicTransaction("TXN004", "ACC004", new BigDecimal("200.00"));
        transaction.setTransactionTimestamp(LocalDateTime.now().withHour(3)); // 3 AM
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("unusual hours")));
    }
    
    @Test
    @DisplayName("Should detect high-risk merchant categories")
    void shouldDetectHighRiskMerchantCategories() {
        // Given
        Transaction transaction = createBasicTransaction("TXN005", "ACC005", new BigDecimal("1000.00"));
        transaction.setMerchantCategory("gambling");
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("High-risk merchant category")));
    }
    
    @Test
    @DisplayName("Should detect new device risk")
    void shouldDetectNewDeviceRisk() {
        // Given
        Transaction transaction = createBasicTransaction("TXN006", "ACC006", new BigDecimal("300.00"));
        transaction.setDeviceFingerprint("new-device-123");
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("new device")));
    }
    
    @Test
    @DisplayName("Should detect suspicious IP addresses")
    void shouldDetectSuspiciousIPAddresses() {
        // Given
        Transaction transaction = createBasicTransaction("TXN007", "ACC007", new BigDecimal("250.00"));
        transaction.setIpAddress("10.0.0.1"); // Suspicious IP pattern
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
        
        // Then
        assertTrue(result.getRiskFactors().stream()
            .anyMatch(factor -> factor.contains("suspicious IP")));
    }
    
    @Test
    @DisplayName("Should track transaction velocity and flag multiple transactions")
    void shouldTrackTransactionVelocity() {
        // Given
        String accountId = "ACC008";
        
        // Simulate multiple transactions for same account
        for (int i = 1; i <= 5; i++) {
            Transaction transaction = createBasicTransaction("TXN" + (100 + i), accountId, new BigDecimal("100.00"));
            fraudDetectionService.analyzeTransaction(transaction);
        }
        
        // When - 6th transaction
        Transaction sixthTransaction = createBasicTransaction("TXN106", accountId, new BigDecimal("100.00"));
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(sixthTransaction);
        
        // Then
        assertNotNull(result);
        // Should have some velocity-based risk due to multiple transactions
        assertTrue(result.getRiskScore() > 0.0);
    }
    
    @Test
    @DisplayName("Should generate appropriate recommendations based on risk level")
    void shouldGenerateAppropriateRecommendations() {
        // Given - High risk transaction
        Transaction highRiskTransaction = createBasicTransaction("TXN008", "ACC008", new BigDecimal("20000.00"));
        highRiskTransaction.setLocationCountry("XX");
        highRiskTransaction.setMerchantCategory("gambling");
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(highRiskTransaction);
        
        // Then
        assertEquals(RiskLevel.HIGH, result.getRiskLevel());
        assertFalse(result.getRecommendations().isEmpty());
        assertTrue(result.getRecommendations().stream()
            .anyMatch(rec -> rec.contains("Manual review")));
    }
    
    @Test
    @DisplayName("Should handle multiple risk factors correctly")
    void shouldHandleMultipleRiskFactorsCorrectly() {
        // Given - Transaction with multiple risk factors
        Transaction multiRiskTransaction = createBasicTransaction("TXN009", "ACC009", new BigDecimal("12000.00"));
        multiRiskTransaction.setLocationCountry("XX");
        multiRiskTransaction.setMerchantCategory("cryptocurrency");
        multiRiskTransaction.setTransactionTimestamp(LocalDateTime.now().withHour(2));
        multiRiskTransaction.setDeviceFingerprint("suspicious-device");
        multiRiskTransaction.setIpAddress("10.0.0.99");
        
        // When
        FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(multiRiskTransaction);
        
        // Then
        assertEquals(RiskLevel.HIGH, result.getRiskLevel());
        assertEquals("DECLINE", result.getDecision());
        assertTrue(result.getRiskFactors().size() >= 4, "Should detect multiple risk factors");
        assertTrue(result.getRiskScore() > 0.7, "Multiple risk factors should result in high score");
    }
    
    @Test
    @DisplayName("Risk level categorization should be correct")
    void riskLevelCategorizationShouldBeCorrect() {
        // Test Low Risk
        assertEquals(RiskLevel.LOW, RiskLevel.fromScore(0.1));
        assertEquals(RiskLevel.LOW, RiskLevel.fromScore(0.29));
        
        // Test Medium Risk
        assertEquals(RiskLevel.MEDIUM, RiskLevel.fromScore(0.3));
        assertEquals(RiskLevel.MEDIUM, RiskLevel.fromScore(0.5));
        assertEquals(RiskLevel.MEDIUM, RiskLevel.fromScore(0.69));
        
        // Test High Risk
        assertEquals(RiskLevel.HIGH, RiskLevel.fromScore(0.7));
        assertEquals(RiskLevel.HIGH, RiskLevel.fromScore(0.9));
        assertEquals(RiskLevel.HIGH, RiskLevel.fromScore(1.0));
    }
    
    private Transaction createBasicTransaction(String transactionId, String accountId, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setCurrency("USD");
        transaction.setMerchantId("MERCHANT_001");
        transaction.setMerchantCategory("retail");
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.PURCHASE);
        transaction.setLocationCountry("US");
        transaction.setLocationCity("New York");
        transaction.setIsCardPresent(true);
        return transaction;
    }
}