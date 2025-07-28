package handler

import (
	"fraud-detection-service/internal/models"
	"fraud-detection-service/internal/service"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

// FraudDetectionHandler handles HTTP requests for fraud detection
type FraudDetectionHandler struct {
	service *service.FraudDetectionService
}

// NewFraudDetectionHandler creates a new fraud detection handler
func NewFraudDetectionHandler(service *service.FraudDetectionService) *FraudDetectionHandler {
	return &FraudDetectionHandler{
		service: service,
	}
}

// AnalyzeTransaction handles transaction analysis requests
func (h *FraudDetectionHandler) AnalyzeTransaction(c *gin.Context) {
	var transaction models.Transaction
	
	// Bind JSON request to transaction struct
	if err := c.ShouldBindJSON(&transaction); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid request body",
			"details": err.Error(),
		})
		return
	}

	// Analyze transaction
	result, err := h.service.AnalyzeTransaction(transaction)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Transaction analysis failed",
			"details": err.Error(),
		})
		return
	}

	// Return analysis result
	c.JSON(http.StatusOK, result)
}

// Health handles health check requests
func (h *FraudDetectionHandler) Health(c *gin.Context) {
	c.String(http.StatusOK, "Fraud Detection Service is healthy")
}

// GetServiceInfo handles service information requests
func (h *FraudDetectionHandler) GetServiceInfo(c *gin.Context) {
	info := models.ServiceInfo{
		Name:        "Fraud Detection Service",
		Version:     "1.0.0",
		Description: "Real-time transaction fraud analysis",
		Timestamp:   time.Now().UnixMilli(),
	}
	
	c.JSON(http.StatusOK, info)
}