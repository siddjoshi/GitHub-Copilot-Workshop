package service

import (
	"fmt"
	"fraud-detection-service/internal/models"
	"strings"
	"testing"
	"time"

	"github.com/stretchr/testify/assert"
)

func TestFraudDetectionService_AnalyzeTransaction(t *testing.T) {
	service := NewFraudDetectionService()

	t.Run("Should approve low-risk transaction", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN001", "ACC001", 100.00)

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.NotNil(t, result)
		assert.Equal(t, "TXN001", result.TransactionID)
		assert.Equal(t, models.LowRisk, result.RiskLevel)
		assert.Equal(t, "APPROVE", result.Decision)
		assert.True(t, result.RiskScore < 0.3)
	})

	t.Run("Should flag high-amount transaction as high risk", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN002", "ACC002", 15000.00)

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.NotNil(t, result)
		assert.True(t, result.RiskScore >= 0.3, "High amount should increase risk score")
		assert.Contains(t, result.RiskFactors, "High transaction amount")
	})

	t.Run("Should detect high-risk country transactions", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN003", "ACC003", 500.00)
		transaction.LocationCountry = "XX" // High-risk country

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.True(t, containsRiskFactor(result.RiskFactors, "high-risk country"))
	})

	t.Run("Should detect unusual hours transactions", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN004", "ACC004", 200.00)
		// Set to 3 AM
		transaction.TransactionTimestamp = time.Date(2024, 7, 28, 3, 0, 0, 0, time.UTC)

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.True(t, containsRiskFactor(result.RiskFactors, "unusual hours"))
	})

	t.Run("Should detect high-risk merchant categories", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN005", "ACC005", 1000.00)
		transaction.MerchantCategory = "gambling"

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.True(t, containsRiskFactor(result.RiskFactors, "High-risk merchant category"))
	})

	t.Run("Should detect new device risk", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN006", "ACC006", 300.00)
		transaction.DeviceFingerprint = "new-device-123"

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.True(t, containsRiskFactor(result.RiskFactors, "new device"))
	})

	t.Run("Should detect suspicious IP addresses", func(t *testing.T) {
		// Given
		transaction := createBasicTransaction("TXN007", "ACC007", 250.00)
		transaction.IPAddress = "10.0.0.1" // Suspicious IP pattern

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.True(t, containsRiskFactor(result.RiskFactors, "suspicious IP"))
	})

	t.Run("Should generate appropriate recommendations based on risk level", func(t *testing.T) {
		// Given - High risk transaction
		transaction := createBasicTransaction("TXN008", "ACC008", 20000.00)
		transaction.LocationCountry = "XX"
		transaction.MerchantCategory = "gambling"

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.Equal(t, models.HighRisk, result.RiskLevel)
		assert.NotEmpty(t, result.Recommendations)
		assert.True(t, containsRecommendation(result.Recommendations, "Manual review"))
	})

	t.Run("Should handle multiple risk factors correctly", func(t *testing.T) {
		// Given - Transaction with multiple risk factors
		transaction := createBasicTransaction("TXN009", "ACC009", 12000.00)
		transaction.LocationCountry = "XX"
		transaction.MerchantCategory = "cryptocurrency"
		transaction.TransactionTimestamp = time.Date(2024, 7, 28, 2, 0, 0, 0, time.UTC)
		transaction.DeviceFingerprint = "suspicious-device"
		transaction.IPAddress = "10.0.0.99"

		// When
		result, err := service.AnalyzeTransaction(transaction)

		// Then
		assert.NoError(t, err)
		assert.Equal(t, models.HighRisk, result.RiskLevel)
		assert.Equal(t, "DECLINE", result.Decision)
		assert.True(t, len(result.RiskFactors) >= 4, "Should detect multiple risk factors")
		assert.True(t, result.RiskScore > 0.7, "Multiple risk factors should result in high score")
	})
}

func TestRiskLevel_FromScore(t *testing.T) {
	tests := []struct {
		score    float64
		expected models.RiskLevel
	}{
		{0.1, models.LowRisk},
		{0.29, models.LowRisk},
		{0.3, models.MediumRisk},
		{0.5, models.MediumRisk},
		{0.69, models.MediumRisk},
		{0.7, models.HighRisk},
		{0.9, models.HighRisk},
		{1.0, models.HighRisk},
	}

	for _, tt := range tests {
		t.Run(fmt.Sprintf("score_%.2f", tt.score), func(t *testing.T) {
			result := models.FromScore(tt.score)
			assert.Equal(t, tt.expected, result)
		})
	}
}

func createBasicTransaction(transactionID, accountID string, amount float64) models.Transaction {
	return models.Transaction{
		TransactionID:        transactionID,
		AccountID:            accountID,
		Amount:               amount,
		Currency:             "USD",
		MerchantID:           "MERCHANT_001",
		MerchantCategory:     "retail",
		TransactionTimestamp: time.Now(),
		TransactionType:      models.Purchase,
		LocationCountry:      "US",
		LocationCity:         "New York",
		IsCardPresent:        boolPtr(true),
	}
}

func containsRiskFactor(factors []string, keyword string) bool {
	for _, factor := range factors {
		if strings.Contains(strings.ToLower(factor), strings.ToLower(keyword)) {
			return true
		}
	}
	return false
}

func containsRecommendation(recommendations []string, keyword string) bool {
	for _, rec := range recommendations {
		if strings.Contains(strings.ToLower(rec), strings.ToLower(keyword)) {
			return true
		}
	}
	return false
}

func boolPtr(b bool) *bool {
	return &b
}