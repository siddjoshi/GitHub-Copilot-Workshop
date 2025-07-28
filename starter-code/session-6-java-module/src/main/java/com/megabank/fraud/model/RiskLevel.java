package com.megabank.fraud.model;

/**
 * Risk level categories for fraud analysis
 */
public enum RiskLevel {
    LOW("Low Risk", 0.0, 0.3),
    MEDIUM("Medium Risk", 0.3, 0.7),
    HIGH("High Risk", 0.7, 1.0);
    
    private final String description;
    private final double minScore;
    private final double maxScore;
    
    RiskLevel(String description, double minScore, double maxScore) {
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }
    
    public String getDescription() { return description; }
    public double getMinScore() { return minScore; }
    public double getMaxScore() { return maxScore; }
    
    /**
     * Determine risk level based on risk score
     */
    public static RiskLevel fromScore(double score) {
        if (score < 0.3) return LOW;
        if (score < 0.7) return MEDIUM;
        return HIGH;
    }
}
