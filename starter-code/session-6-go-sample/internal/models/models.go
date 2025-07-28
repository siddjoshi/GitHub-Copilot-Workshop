package models

import (
	"strings"
	"time"
)

// TransactionType represents different types of transactions
type TransactionType string

const (
	Purchase   TransactionType = "PURCHASE"
	Withdrawal TransactionType = "WITHDRAWAL"
	Transfer   TransactionType = "TRANSFER"
	Refund     TransactionType = "REFUND"
	Payment    TransactionType = "PAYMENT"
	Deposit    TransactionType = "DEPOSIT"
)

// String returns the string representation of TransactionType
func (t TransactionType) String() string {
	return string(t)
}

// Description returns a human-readable description
func (t TransactionType) Description() string {
	switch t {
	case Purchase:
		return "Purchase"
	case Withdrawal:
		return "ATM Withdrawal"
	case Transfer:
		return "Money Transfer"
	case Refund:
		return "Refund"
	case Payment:
		return "Bill Payment"
	case Deposit:
		return "Deposit"
	default:
		return "Unknown"
	}
}

// RiskLevel represents the risk assessment levels
type RiskLevel string

const (
	LowRisk    RiskLevel = "LOW"
	MediumRisk RiskLevel = "MEDIUM"
	HighRisk   RiskLevel = "HIGH"
)

// String returns the string representation of RiskLevel
func (r RiskLevel) String() string {
	return string(r)
}

// Description returns a human-readable description
func (r RiskLevel) Description() string {
	switch r {
	case LowRisk:
		return "Low Risk"
	case MediumRisk:
		return "Medium Risk"
	case HighRisk:
		return "High Risk"
	default:
		return "Unknown Risk"
	}
}

// GetMinScore returns the minimum score for this risk level
func (r RiskLevel) GetMinScore() float64 {
	switch r {
	case LowRisk:
		return 0.0
	case MediumRisk:
		return 0.3
	case HighRisk:
		return 0.7
	default:
		return 0.0
	}
}

// GetMaxScore returns the maximum score for this risk level
func (r RiskLevel) GetMaxScore() float64 {
	switch r {
	case LowRisk:
		return 0.3
	case MediumRisk:
		return 0.7
	case HighRisk:
		return 1.0
	default:
		return 1.0
	}
}

// FromScore determines risk level based on risk score
func FromScore(score float64) RiskLevel {
	if score < 0.3 {
		return LowRisk
	}
	if score < 0.7 {
		return MediumRisk
	}
	return HighRisk
}

// Transaction represents a financial transaction to be analyzed for fraud
type Transaction struct {
	ID                   *int64           `json:"id,omitempty"`
	TransactionID        string           `json:"transactionId" binding:"required"`
	AccountID            string           `json:"accountId" binding:"required"`
	Amount               float64          `json:"amount" binding:"required,min=0.01"`
	Currency             string           `json:"currency" binding:"required,len=3"`
	MerchantID           string           `json:"merchantId" binding:"required"`
	MerchantCategory     string           `json:"merchantCategory,omitempty"`
	TransactionTimestamp time.Time        `json:"transactionTimestamp" binding:"required"`
	IPAddress            string           `json:"ipAddress,omitempty"`
	DeviceFingerprint    string           `json:"deviceFingerprint,omitempty"`
	LocationCountry      string           `json:"locationCountry,omitempty"`
	LocationCity         string           `json:"locationCity,omitempty"`
	TransactionType      TransactionType  `json:"transactionType,omitempty"`
	IsCardPresent        *bool            `json:"isCardPresent,omitempty"`
}

// Validate performs basic validation on the transaction
func (t *Transaction) Validate() error {
	if strings.TrimSpace(t.TransactionID) == "" {
		return &ValidationError{Field: "transactionId", Message: "cannot be blank"}
	}
	if strings.TrimSpace(t.AccountID) == "" {
		return &ValidationError{Field: "accountId", Message: "cannot be blank"}
	}
	if t.Amount <= 0 {
		return &ValidationError{Field: "amount", Message: "must be greater than 0"}
	}
	if len(strings.TrimSpace(t.Currency)) != 3 {
		return &ValidationError{Field: "currency", Message: "must be 3 characters"}
	}
	if strings.TrimSpace(t.MerchantID) == "" {
		return &ValidationError{Field: "merchantId", Message: "cannot be blank"}
	}
	return nil
}

// FraudAnalysisResult contains the result of fraud analysis
type FraudAnalysisResult struct {
	TransactionID       string    `json:"transactionId"`
	RiskScore           float64   `json:"riskScore"`
	RiskLevel           RiskLevel `json:"riskLevel"`
	Decision            string    `json:"decision"`
	RiskFactors         []string  `json:"riskFactors"`
	Recommendations     []string  `json:"recommendations"`
	AnalysisTimestamp   time.Time `json:"analysisTimestamp"`
	ModelVersion        string    `json:"modelVersion"`
}

// NewFraudAnalysisResult creates a new fraud analysis result
func NewFraudAnalysisResult(transactionID string, riskScore float64, riskLevel RiskLevel, decision string) *FraudAnalysisResult {
	return &FraudAnalysisResult{
		TransactionID:     transactionID,
		RiskScore:         riskScore,
		RiskLevel:         riskLevel,
		Decision:          decision,
		RiskFactors:       make([]string, 0),
		Recommendations:   make([]string, 0),
		AnalysisTimestamp: time.Now(),
		ModelVersion:      "1.0",
	}
}

// ServiceInfo represents service information
type ServiceInfo struct {
	Name        string `json:"name"`
	Version     string `json:"version"`
	Description string `json:"description"`
	Timestamp   int64  `json:"timestamp"`
}

// ValidationError represents a validation error
type ValidationError struct {
	Field   string `json:"field"`
	Message string `json:"message"`
}

// Error implements the error interface
func (e *ValidationError) Error() string {
	return e.Field + ": " + e.Message
}