package com.megabank.fraud.controller;

import com.megabank.fraud.model.*;
import com.megabank.fraud.service.FraudDetectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FraudDetectionController.class)
@DisplayName("Fraud Detection Controller Tests")
class FraudDetectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FraudDetectionService fraudDetectionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should analyze transaction successfully")
    void shouldAnalyzeTransactionSuccessfully() throws Exception {
        // Given
        Transaction transaction = createSampleTransaction();
        FraudAnalysisResult mockResult = createMockAnalysisResult();
        
        when(fraudDetectionService.analyzeTransaction(any(Transaction.class)))
            .thenReturn(mockResult);

        // When & Then
        mockMvc.perform(post("/api/fraud/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN123"))
                .andExpect(jsonPath("$.riskLevel").value("LOW"))
                .andExpect(jsonPath("$.decision").value("APPROVE"))
                .andExpect(jsonPath("$.riskScore").value(0.2));
    }

    @Test
    @DisplayName("Should return bad request for invalid transaction")
    void shouldReturnBadRequestForInvalidTransaction() throws Exception {
        // Given - Transaction with missing required fields
        Transaction invalidTransaction = new Transaction();
        invalidTransaction.setTransactionId(""); // Invalid empty ID

        // When & Then
        mockMvc.perform(post("/api/fraud/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTransaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should handle service exception gracefully")
    void shouldHandleServiceExceptionGracefully() throws Exception {
        // Given
        Transaction transaction = createSampleTransaction();
        when(fraudDetectionService.analyzeTransaction(any(Transaction.class)))
            .thenThrow(new RuntimeException("Service error"));

        // When & Then
        mockMvc.perform(post("/api/fraud/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return health status")
    void shouldReturnHealthStatus() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/fraud/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Fraud Detection Service is healthy"));
    }

    @Test
    @DisplayName("Should return service information")
    void shouldReturnServiceInformation() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/fraud/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fraud Detection Service"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.description").value("Real-time transaction fraud analysis"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private Transaction createSampleTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("TXN123");
        transaction.setAccountId("ACC001");
        transaction.setAmount(new BigDecimal("500.00"));
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

    private FraudAnalysisResult createMockAnalysisResult() {
        FraudAnalysisResult result = new FraudAnalysisResult(
            "TXN123", 
            0.2, 
            RiskLevel.LOW, 
            "APPROVE"
        );
        result.setRiskFactors(List.of("Standard transaction"));
        result.setRecommendations(List.of("Process transaction normally"));
        result.setModelVersion("1.0");
        return result;
    }
}