package com.megabank.fraud.service;

import com.megabank.fraud.model.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core fraud detection service implementing risk analysis algorithms
 * This service contains the main business logic for fraud detection
 */
@Service
public class FraudDetectionService {
    
    // In-memory cache for transaction history (in real system, this would be a database)
    private final Map<String, List<Transaction>> accountTransactionHistory = new ConcurrentHashMap<>();
    private final Map<String, Integer> dailyTransactionCounts = new ConcurrentHashMap<>();
    
    private static final double HIGH_AMOUNT_THRESHOLD = 10000.0;
    private static final double UNUSUAL_AMOUNT_MULTIPLIER = 5.0;
    private static final int MAX_DAILY_TRANSACTIONS = 10;
    private static final int SUSPICIOUS_VELOCITY_MINUTES = 5;
    
    /**
     * Main method to analyze a transaction for fraud
     */
    public FraudAnalysisResult analyzeTransaction(Transaction transaction) {
        double riskScore = 0.0;
        List<String> riskFactors = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        
        // Amount-based risk analysis
        riskScore += analyzeAmountRisk(transaction, riskFactors);
        
        // Velocity-based risk analysis
        riskScore += analyzeVelocityRisk(transaction, riskFactors);
        
        // Geographic risk analysis
        riskScore += analyzeGeographicRisk(transaction, riskFactors);
        
        // Time-based risk analysis
        riskScore += analyzeTimeBasedRisk(transaction, riskFactors);
        
        // Device and IP analysis
        riskScore += analyzeDeviceRisk(transaction, riskFactors);
        
        // Merchant category analysis
        riskScore += analyzeMerchantRisk(transaction, riskFactors);
        
        // Normalize risk score to 0-1 range
        riskScore = Math.min(1.0, riskScore);
        
        // Determine risk level and decision
        RiskLevel riskLevel = RiskLevel.fromScore(riskScore);
        String decision = determineDecision(riskScore);
        
        // Generate recommendations
        generateRecommendations(riskScore, riskLevel, recommendations);
        
        // Store transaction in history for future analysis
        updateTransactionHistory(transaction);
        
        FraudAnalysisResult result = new FraudAnalysisResult(
            transaction.getTransactionId(), 
            riskScore, 
            riskLevel, 
            decision
        );
        result.setRiskFactors(riskFactors);
        result.setRecommendations(recommendations);
        result.setModelVersion("1.0");
        
        return result;
    }
    
    /**
     * Analyze risk based on transaction amount
     */
    private double analyzeAmountRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        BigDecimal amount = transaction.getAmount();
        
        // High amount transactions are inherently riskier
        if (amount.compareTo(BigDecimal.valueOf(HIGH_AMOUNT_THRESHOLD)) > 0) {
            risk += 0.3;
            riskFactors.add("High transaction amount: $" + amount);
        }
        
        // Check if amount is unusual for this account
        List<Transaction> history = accountTransactionHistory.get(transaction.getAccountId());
        if (history != null && !history.isEmpty()) {
            double avgAmount = history.stream()
                .mapToDouble(t -> t.getAmount().doubleValue())
                .average()
                .orElse(0.0);
            
            if (amount.doubleValue() > avgAmount * UNUSUAL_AMOUNT_MULTIPLIER) {
                risk += 0.2;
                riskFactors.add("Unusual amount compared to account history");
            }
        }
        
        return risk;
    }
    
    /**
     * Analyze risk based on transaction velocity
     */
    private double analyzeVelocityRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        String accountId = transaction.getAccountId();
        
        // Check daily transaction count
        String dailyKey = accountId + ":" + transaction.getTransactionTimestamp().toLocalDate();
        int dailyCount = dailyTransactionCounts.getOrDefault(dailyKey, 0) + 1;
        dailyTransactionCounts.put(dailyKey, dailyCount);
        
        if (dailyCount > MAX_DAILY_TRANSACTIONS) {
            risk += 0.2;
            riskFactors.add("High daily transaction count: " + dailyCount);
        }
        
        // Check for rapid successive transactions
        List<Transaction> recentTransactions = getRecentTransactions(accountId, SUSPICIOUS_VELOCITY_MINUTES);
        if (recentTransactions.size() >= 3) {
            risk += 0.25;
            riskFactors.add("Multiple transactions within " + SUSPICIOUS_VELOCITY_MINUTES + " minutes");
        }
        
        return risk;
    }
    
    /**
     * Analyze geographic risk factors
     */
    private double analyzeGeographicRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        
        // Check for high-risk countries
        if (isHighRiskCountry(transaction.getLocationCountry())) {
            risk += 0.4;
            riskFactors.add("Transaction from high-risk country: " + transaction.getLocationCountry());
        }
        
        // Check for geographic inconsistency with recent transactions
        if (hasGeographicInconsistency(transaction)) {
            risk += 0.3;
            riskFactors.add("Geographic inconsistency detected");
        }
        
        return risk;
    }
    
    /**
     * Analyze time-based risk factors
     */
    private double analyzeTimeBasedRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        LocalDateTime timestamp = transaction.getTransactionTimestamp();
        
        // Transactions during unusual hours
        int hour = timestamp.getHour();
        if (hour < 6 || hour > 23) {
            risk += 0.1;
            riskFactors.add("Transaction during unusual hours");
        }
        
        return risk;
    }
    
    /**
     * Analyze device and IP-based risk
     */
    private double analyzeDeviceRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        
        // Check for new device
        if (transaction.getDeviceFingerprint() != null && isNewDevice(transaction)) {
            risk += 0.15;
            riskFactors.add("Transaction from new device");
        }
        
        // Check for suspicious IP
        if (isSuspiciousIP(transaction.getIpAddress())) {
            risk += 0.2;
            riskFactors.add("Transaction from suspicious IP address");
        }
        
        return risk;
    }
    
    /**
     * Analyze merchant-related risk
     */
    private double analyzeMerchantRisk(Transaction transaction, List<String> riskFactors) {
        double risk = 0.0;
        
        // High-risk merchant categories
        if (isHighRiskMerchantCategory(transaction.getMerchantCategory())) {
            risk += 0.15;
            riskFactors.add("High-risk merchant category: " + transaction.getMerchantCategory());
        }
        
        return risk;
    }
    
    /**
     * Determine the final decision based on risk score
     */
    private String determineDecision(double riskScore) {
        if (riskScore < 0.3) {
            return "APPROVE";
        } else if (riskScore < 0.7) {
            return "REVIEW";
        } else {
            return "DECLINE";
        }
    }
    
    /**
     * Generate recommendations based on analysis
     */
    private void generateRecommendations(double riskScore, RiskLevel riskLevel, List<String> recommendations) {
        switch (riskLevel) {
            case LOW:
                recommendations.add("Process transaction normally");
                break;
            case MEDIUM:
                recommendations.add("Additional verification recommended");
                recommendations.add("Monitor account for suspicious activity");
                break;
            case HIGH:
                recommendations.add("Manual review required");
                recommendations.add("Contact customer for verification");
                recommendations.add("Consider temporary account restrictions");
                break;
        }
    }
    
    // Helper methods (simplified implementations for demo purposes)
    
    private void updateTransactionHistory(Transaction transaction) {
        accountTransactionHistory
            .computeIfAbsent(transaction.getAccountId(), k -> new ArrayList<>())
            .add(transaction);
    }
    
    private List<Transaction> getRecentTransactions(String accountId, int minutes) {
        List<Transaction> transactions = accountTransactionHistory.get(accountId);
        if (transactions == null) return new ArrayList<>();
        
        LocalDateTime cutoff = LocalDateTime.now().minus(minutes, ChronoUnit.MINUTES);
        return transactions.stream()
            .filter(t -> t.getTransactionTimestamp().isAfter(cutoff))
            .toList();
    }
    
    private boolean isHighRiskCountry(String country) {
        // Simplified implementation
        Set<String> highRiskCountries = Set.of("XX", "YY", "ZZ"); // Placeholder countries
        return highRiskCountries.contains(country);
    }
    
    private boolean hasGeographicInconsistency(Transaction transaction) {
        // Simplified implementation - check if location differs significantly from recent transactions
        return false; // Placeholder implementation
    }
    
    private boolean isNewDevice(Transaction transaction) {
        // Simplified implementation
        return true; // Placeholder - would check against known devices
    }
    
    private boolean isSuspiciousIP(String ipAddress) {
        // Simplified implementation
        return ipAddress != null && ipAddress.startsWith("10.0.0"); // Placeholder logic
    }
    
    private boolean isHighRiskMerchantCategory(String category) {
        // High-risk categories
        Set<String> highRiskCategories = Set.of("gambling", "adult", "cryptocurrency", "money_transfer");
        return highRiskCategories.contains(category);
    }
}
