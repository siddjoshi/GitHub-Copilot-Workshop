package com.megabank.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Fraud Detection Service
 * This microservice analyzes transactions for potential fraud patterns
 * 
 * Key capabilities:
 * - Real-time transaction analysis
 * - Risk scoring based on multiple factors
 * - Machine learning-based anomaly detection
 * - Integration with external risk databases
 */
@SpringBootApplication
public class FraudDetectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FraudDetectionApplication.class, args);
    }
}
