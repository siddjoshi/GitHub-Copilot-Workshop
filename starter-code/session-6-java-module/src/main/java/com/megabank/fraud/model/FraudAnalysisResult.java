package com.megabank.fraud.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Fraud analysis result containing risk assessment and recommendations
 */
public class FraudAnalysisResult {
    
    private String transactionId;
    private double riskScore;  // 0.0 to 1.0, where 1.0 is highest risk
    private RiskLevel riskLevel;
    private String decision;   // APPROVE, DECLINE, REVIEW
    private List<String> riskFactors;
    private List<String> recommendations;
    private LocalDateTime analysisTimestamp;
    private String modelVersion;
    
    // Constructors
    public FraudAnalysisResult() {}
    
    public FraudAnalysisResult(String transactionId, double riskScore, RiskLevel riskLevel, String decision) {
        this.transactionId = transactionId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.decision = decision;
        this.analysisTimestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }
    
    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }
    
    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    
    public List<String> getRiskFactors() { return riskFactors; }
    public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    
    public LocalDateTime getAnalysisTimestamp() { return analysisTimestamp; }
    public void setAnalysisTimestamp(LocalDateTime analysisTimestamp) { this.analysisTimestamp = analysisTimestamp; }
    
    public String getModelVersion() { return modelVersion; }
    public void setModelVersion(String modelVersion) { this.modelVersion = modelVersion; }
    
    @Override
    public String toString() {
        return "FraudAnalysisResult{" +
                "transactionId='" + transactionId + '\'' +
                ", riskScore=" + riskScore +
                ", riskLevel=" + riskLevel +
                ", decision='" + decision + '\'' +
                ", analysisTimestamp=" + analysisTimestamp +
                '}';
    }
}
