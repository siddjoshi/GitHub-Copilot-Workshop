package com.megabank.fraud.controller;

import com.megabank.fraud.model.FraudAnalysisResult;
import com.megabank.fraud.model.Transaction;
import com.megabank.fraud.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * REST controller for fraud detection API endpoints
 */
@RestController
@RequestMapping("/api/fraud")
public class FraudDetectionController {
    
    private final FraudDetectionService fraudDetectionService;
    
    @Autowired
    public FraudDetectionController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }
    
    /**
     * Analyze a transaction for fraud risk
     */
    @PostMapping("/analyze")
    public ResponseEntity<FraudAnalysisResult> analyzeTransaction(@Valid @RequestBody Transaction transaction) {
        try {
            FraudAnalysisResult result = fraudDetectionService.analyzeTransaction(transaction);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // In a real system, this would be properly logged and handled
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Fraud Detection Service is healthy");
    }
    
    /**
     * Get service information
     */
    @GetMapping("/info")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        ServiceInfo info = new ServiceInfo(
            "Fraud Detection Service",
            "1.0.0",
            "Real-time transaction fraud analysis",
            System.currentTimeMillis()
        );
        return ResponseEntity.ok(info);
    }
    
    // Inner class for service information
    public static class ServiceInfo {
        private String name;
        private String version;
        private String description;
        private long timestamp;
        
        public ServiceInfo(String name, String version, String description, long timestamp) {
            this.name = name;
            this.version = version;
            this.description = description;
            this.timestamp = timestamp;
        }
        
        // Getters
        public String getName() { return name; }
        public String getVersion() { return version; }
        public String getDescription() { return description; }
        public long getTimestamp() { return timestamp; }
    }
}
