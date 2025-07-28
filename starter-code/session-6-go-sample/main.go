package main

import (
	"fraud-detection-service/internal/handler"
	"fraud-detection-service/internal/service"
	"log"

	"github.com/gin-gonic/gin"
)

// FraudDetectionApplication represents the main application
type FraudDetectionApplication struct {
	router  *gin.Engine
	service *service.FraudDetectionService
}

// NewFraudDetectionApplication creates a new instance of the application
func NewFraudDetectionApplication() *FraudDetectionApplication {
	fraudService := service.NewFraudDetectionService()
	fraudHandler := handler.NewFraudDetectionHandler(fraudService)

	router := gin.Default()
	
	// API routes
	api := router.Group("/api/fraud")
	{
		api.POST("/analyze", fraudHandler.AnalyzeTransaction)
		api.GET("/health", fraudHandler.Health)
		api.GET("/info", fraudHandler.GetServiceInfo)
	}

	return &FraudDetectionApplication{
		router:  router,
		service: fraudService,
	}
}

// Run starts the HTTP server
func (app *FraudDetectionApplication) Run(addr string) error {
	log.Printf("Starting Fraud Detection Service on %s", addr)
	return app.router.Run(addr)
}

func main() {
	app := NewFraudDetectionApplication()
	if err := app.Run(":8080"); err != nil {
		log.Fatalf("Failed to start server: %v", err)
	}
}