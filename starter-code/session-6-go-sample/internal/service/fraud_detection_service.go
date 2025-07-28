package service

import (
	"fraud-detection-service/internal/models"
	"math"
	"strings"
	"sync"
	"time"
)

// FraudDetectionService implements fraud detection business logic
type FraudDetectionService struct {
	// In-memory storage for demo purposes (would be replaced with proper database)
	accountHistory      map[string][]models.Transaction
	dailyTransactionCounts map[string]int
	mutex               sync.RWMutex
}

// Configuration constants
const (
	HighAmountThreshold       = 10000.0
	UnusualAmountMultiplier   = 5.0
	MaxDailyTransactions      = 10
	SuspiciousVelocityMinutes = 5
)

// NewFraudDetectionService creates a new fraud detection service
func NewFraudDetectionService() *FraudDetectionService {
	return &FraudDetectionService{
		accountHistory:         make(map[string][]models.Transaction),
		dailyTransactionCounts: make(map[string]int),
	}
}

// AnalyzeTransaction performs fraud analysis on a transaction
func (s *FraudDetectionService) AnalyzeTransaction(transaction models.Transaction) (*models.FraudAnalysisResult, error) {
	// Validate transaction
	if err := transaction.Validate(); err != nil {
		return nil, err
	}

	var riskScore float64
	var riskFactors []string
	var recommendations []string

	// Amount-based risk analysis
	riskScore += s.analyzeAmountRisk(transaction, &riskFactors)

	// Velocity-based risk analysis
	riskScore += s.analyzeVelocityRisk(transaction, &riskFactors)

	// Geographic risk analysis
	riskScore += s.analyzeGeographicRisk(transaction, &riskFactors)

	// Time-based risk analysis
	riskScore += s.analyzeTimeBasedRisk(transaction, &riskFactors)

	// Device and IP analysis
	riskScore += s.analyzeDeviceRisk(transaction, &riskFactors)

	// Merchant category analysis
	riskScore += s.analyzeMerchantRisk(transaction, &riskFactors)

	// Normalize risk score to 0-1 range
	riskScore = math.Min(1.0, riskScore)

	// Determine risk level and decision
	riskLevel := models.FromScore(riskScore)
	decision := s.determineDecision(riskScore)

	// Generate recommendations
	s.generateRecommendations(riskLevel, &recommendations)

	// Store transaction in history for future analysis
	s.updateTransactionHistory(transaction)

	// Create and return result
	result := models.NewFraudAnalysisResult(
		transaction.TransactionID,
		riskScore,
		riskLevel,
		decision,
	)
	result.RiskFactors = riskFactors
	result.Recommendations = recommendations

	return result, nil
}

// analyzeAmountRisk performs amount-based risk analysis
func (s *FraudDetectionService) analyzeAmountRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// High amount transactions are inherently riskier
	if transaction.Amount > HighAmountThreshold {
		risk += 0.3
		*riskFactors = append(*riskFactors, "High transaction amount")
	}

	// Check if amount is unusual for this account
	s.mutex.RLock()
	history, exists := s.accountHistory[transaction.AccountID]
	s.mutex.RUnlock()

	if exists && len(history) > 0 {
		var sum float64
		for _, t := range history {
			sum += t.Amount
		}
		avgAmount := sum / float64(len(history))

		if transaction.Amount > avgAmount*UnusualAmountMultiplier {
			risk += 0.2
			*riskFactors = append(*riskFactors, "Unusual amount compared to account history")
		}
	}

	return risk
}

// analyzeVelocityRisk performs velocity-based risk analysis
func (s *FraudDetectionService) analyzeVelocityRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// Check daily transaction count
	dailyKey := transaction.AccountID + ":" + transaction.TransactionTimestamp.Format("2006-01-02")
	
	s.mutex.Lock()
	dailyCount := s.dailyTransactionCounts[dailyKey] + 1
	s.dailyTransactionCounts[dailyKey] = dailyCount
	s.mutex.Unlock()

	if dailyCount > MaxDailyTransactions {
		risk += 0.2
		*riskFactors = append(*riskFactors, "High daily transaction count")
	}

	// Check for rapid successive transactions
	recentTransactions := s.getRecentTransactions(transaction.AccountID, SuspiciousVelocityMinutes)
	if len(recentTransactions) >= 3 {
		risk += 0.25
		*riskFactors = append(*riskFactors, "Multiple transactions within short time frame")
	}

	return risk
}

// analyzeGeographicRisk performs geographic risk analysis
func (s *FraudDetectionService) analyzeGeographicRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// Check for high-risk countries
	if s.isHighRiskCountry(transaction.LocationCountry) {
		risk += 0.4
		*riskFactors = append(*riskFactors, "Transaction from high-risk country: "+transaction.LocationCountry)
	}

	// Check for geographic inconsistency (simplified implementation)
	if s.hasGeographicInconsistency(transaction) {
		risk += 0.3
		*riskFactors = append(*riskFactors, "Geographic inconsistency detected")
	}

	return risk
}

// analyzeTimeBasedRisk performs time-based risk analysis
func (s *FraudDetectionService) analyzeTimeBasedRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// Transactions during unusual hours
	hour := transaction.TransactionTimestamp.Hour()
	if hour < 6 || hour > 23 {
		risk += 0.1
		*riskFactors = append(*riskFactors, "Transaction during unusual hours")
	}

	return risk
}

// analyzeDeviceRisk performs device and IP-based risk analysis
func (s *FraudDetectionService) analyzeDeviceRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// Check for new device
	if transaction.DeviceFingerprint != "" && s.isNewDevice(transaction) {
		risk += 0.15
		*riskFactors = append(*riskFactors, "Transaction from new device")
	}

	// Check for suspicious IP
	if s.isSuspiciousIP(transaction.IPAddress) {
		risk += 0.2
		*riskFactors = append(*riskFactors, "Transaction from suspicious IP address")
	}

	return risk
}

// analyzeMerchantRisk performs merchant-related risk analysis
func (s *FraudDetectionService) analyzeMerchantRisk(transaction models.Transaction, riskFactors *[]string) float64 {
	risk := 0.0

	// High-risk merchant categories
	if s.isHighRiskMerchantCategory(transaction.MerchantCategory) {
		risk += 0.15
		*riskFactors = append(*riskFactors, "High-risk merchant category: "+transaction.MerchantCategory)
	}

	return risk
}

// determineDecision determines the final decision based on risk score
func (s *FraudDetectionService) determineDecision(riskScore float64) string {
	if riskScore < 0.3 {
		return "APPROVE"
	} else if riskScore < 0.7 {
		return "REVIEW"
	}
	return "DECLINE"
}

// generateRecommendations generates recommendations based on risk level
func (s *FraudDetectionService) generateRecommendations(riskLevel models.RiskLevel, recommendations *[]string) {
	switch riskLevel {
	case models.LowRisk:
		*recommendations = append(*recommendations, "Process transaction normally")
	case models.MediumRisk:
		*recommendations = append(*recommendations, "Additional verification recommended")
		*recommendations = append(*recommendations, "Monitor account for suspicious activity")
	case models.HighRisk:
		*recommendations = append(*recommendations, "Manual review required")
		*recommendations = append(*recommendations, "Contact customer for verification")
		*recommendations = append(*recommendations, "Consider temporary account restrictions")
	}
}

// Helper methods

func (s *FraudDetectionService) updateTransactionHistory(transaction models.Transaction) {
	s.mutex.Lock()
	defer s.mutex.Unlock()
	
	s.accountHistory[transaction.AccountID] = append(
		s.accountHistory[transaction.AccountID], 
		transaction,
	)
}

func (s *FraudDetectionService) getRecentTransactions(accountID string, minutes int) []models.Transaction {
	s.mutex.RLock()
	defer s.mutex.RUnlock()
	
	transactions, exists := s.accountHistory[accountID]
	if !exists {
		return []models.Transaction{}
	}

	cutoff := time.Now().Add(-time.Duration(minutes) * time.Minute)
	var recent []models.Transaction
	
	for _, t := range transactions {
		if t.TransactionTimestamp.After(cutoff) {
			recent = append(recent, t)
		}
	}
	
	return recent
}

func (s *FraudDetectionService) isHighRiskCountry(country string) bool {
	// Simplified implementation
	highRiskCountries := []string{"XX", "YY", "ZZ"} // Placeholder countries
	for _, hrc := range highRiskCountries {
		if country == hrc {
			return true
		}
	}
	return false
}

func (s *FraudDetectionService) hasGeographicInconsistency(transaction models.Transaction) bool {
	// Simplified implementation - would check against recent transactions
	return false // Placeholder implementation
}

func (s *FraudDetectionService) isNewDevice(transaction models.Transaction) bool {
	// Simplified implementation - would check against known devices
	return true // Placeholder - always return true for demo
}

func (s *FraudDetectionService) isSuspiciousIP(ipAddress string) bool {
	// Simplified implementation
	return strings.HasPrefix(ipAddress, "10.0.0") // Placeholder logic
}

func (s *FraudDetectionService) isHighRiskMerchantCategory(category string) bool {
	// High-risk categories
	highRiskCategories := []string{"gambling", "adult", "cryptocurrency", "money_transfer"}
	for _, hrc := range highRiskCategories {
		if category == hrc {
			return true
		}
	}
	return false
}