"""
Test Suite for Customer Churn Prediction Pipeline
================================================

This test suite validates the ML pipeline functionality and serves as an example
for the workshop participants to understand testing ML systems.

Run tests with: python -m pytest test_churn_prediction.py -v

Use GitHub Copilot to help expand these tests:
- "Add tests for data validation and quality checks"
- "Create tests for model performance thresholds"
- "Generate tests for feature engineering edge cases"
"""

import pytest
import pandas as pd
import numpy as np
from churn_prediction import ChurnPredictor


class TestChurnPredictor:
    """Test class for ChurnPredictor functionality"""
    
    def setup_method(self):
        """Set up test fixtures before each test method"""
        self.predictor = ChurnPredictor()
    
    def test_data_loading(self):
        """Test that data loading works correctly"""
        data = self.predictor.load_data()
        
        # Basic data structure tests
        assert data is not None, "Data should not be None"
        assert len(data) == 1000, "Should have 1000 records"
        assert 'Churn' in data.columns, "Should have Churn target column"
        assert 'customerID' in data.columns, "Should have customer ID"
        
        # Data quality tests
        assert data['Churn'].isin(['Yes', 'No']).all(), "Churn should be Yes/No"
        assert data['tenure'].min() >= 1, "Tenure should be at least 1 month"
        assert data['tenure'].max() <= 72, "Tenure should be at most 72 months"
        assert data['MonthlyCharges'].min() >= 18, "Monthly charges should be at least $18"
        
    def test_data_realism(self):
        """Test that synthetic data has realistic characteristics"""
        data = self.predictor.load_data()
        
        # Churn rate should be reasonable (10-40%)
        churn_rate = (data['Churn'] == 'Yes').mean()
        assert 0.10 <= churn_rate <= 0.40, f"Churn rate {churn_rate:.1%} should be 10-40%"
        
        # Month-to-month contracts should have higher churn
        churn_by_contract = data.groupby('Contract')['Churn'].apply(lambda x: (x == 'Yes').mean())
        mtm_churn = churn_by_contract['Month-to-month']
        yearly_churn = churn_by_contract.get('One year', 0)
        
        assert mtm_churn > yearly_churn, "Month-to-month should have higher churn than yearly contracts"
        
    def test_preprocessing(self):
        """Test data preprocessing functionality"""
        self.predictor.load_data()
        self.predictor.preprocess_data()
        
        # Check that train/test split worked
        assert self.predictor.X_train is not None, "Training features should exist"
        assert self.predictor.y_train is not None, "Training target should exist"
        assert len(self.predictor.X_train) == 800, "Training set should have 800 samples"
        assert len(self.predictor.X_test) == 200, "Test set should have 200 samples"
        
        # Check that target encoding worked
        assert set(self.predictor.y_train.unique()) == {0, 1}, "Target should be encoded as 0/1"
        
    def test_feature_engineering(self):
        """Test feature engineering functionality"""
        self.predictor.load_data()
        self.predictor.preprocess_data()
        self.predictor.engineer_features()
        
        # Check that new features were created
        original_features = 15  # From preprocessing
        new_features = self.predictor.X_train_scaled.shape[1]
        assert new_features > original_features, "Feature engineering should add new features"
        
        # Check specific engineered features
        assert 'AvgChargesPerMonth' in self.predictor.X_train_scaled.columns, "Should have AvgChargesPerMonth feature"
        assert 'TenureGroup' in self.predictor.X_train_scaled.columns, "Should have TenureGroup feature"
        
    def test_model_training(self):
        """Test model training functionality"""
        self.predictor.load_data()
        self.predictor.preprocess_data()
        self.predictor.engineer_features()
        self.predictor.train_models()
        
        # Check that models were trained
        assert len(self.predictor.models) >= 2, "Should train at least 2 models"
        assert 'RandomForest' in self.predictor.models, "Should train Random Forest"
        assert 'LogisticRegression' in self.predictor.models, "Should train Logistic Regression"
        
        # Check model performance
        for name, model_data in self.predictor.models.items():
            accuracy = model_data['accuracy']
            assert 0.5 <= accuracy <= 1.0, f"{name} accuracy {accuracy} should be between 50-100%"
            
    def test_achievement_thresholds(self):
        """Test that achievement badge thresholds are realistic"""
        self.predictor.load_data()
        self.predictor.preprocess_data()
        self.predictor.engineer_features()
        self.predictor.train_models()
        
        # Get best model accuracy
        best_accuracy = max(model_data['accuracy'] for model_data in self.predictor.models.values())
        
        # ML Wizard badge (>85%) should be achievable with optimization
        # For now, just check that we're getting reasonable performance
        assert best_accuracy > 0.70, f"Best accuracy {best_accuracy:.3f} should be >70% for realistic workshop"
        
        # Should be possible to reach 85% with hyperparameter tuning
        print(f"Best model accuracy: {best_accuracy:.3f} (Target: >85% for ML Wizard badge)")
        
    def test_full_pipeline(self):
        """Test the complete ML pipeline end-to-end"""
        # Run the complete pipeline
        self.predictor.load_data()
        self.predictor.explore_data()
        self.predictor.preprocess_data()
        self.predictor.engineer_features()
        self.predictor.train_models()
        self.predictor.evaluate_models()
        
        # Verify pipeline completed successfully
        assert self.predictor.data is not None
        assert len(self.predictor.models) > 0
        assert self.predictor.X_train_scaled is not None
        
        print("✅ Complete ML pipeline validation successful!")


def test_data_quality_checks():
    """Test data quality validation functions"""
    predictor = ChurnPredictor()
    data = predictor.load_data()
    
    # Check for missing values
    missing_values = data.isnull().sum()
    assert missing_values.sum() == 0, "Synthetic data should have no missing values"
    
    # Check data types
    assert data['SeniorCitizen'].dtype in ['int64', 'int32'], "SeniorCitizen should be integer"
    assert data['MonthlyCharges'].dtype in ['float64', 'float32'], "MonthlyCharges should be float"
    

def test_workshop_timing():
    """Test that the workshop can be completed in reasonable time"""
    import time
    
    start_time = time.time()
    
    # Simulate workshop execution
    predictor = ChurnPredictor()
    predictor.load_data()
    predictor.explore_data()
    predictor.preprocess_data()
    predictor.engineer_features()
    predictor.train_models()
    predictor.evaluate_models()
    
    execution_time = time.time() - start_time
    
    # Should complete basic pipeline quickly (under 30 seconds for testing)
    assert execution_time < 30, f"Basic pipeline took {execution_time:.1f}s, might be too slow for workshop"
    
    print(f"Pipeline execution time: {execution_time:.1f} seconds")


if __name__ == "__main__":
    # Run tests directly
    import sys
    
    print("Running Churn Prediction Pipeline Tests...")
    print("=" * 50)
    
    # Run basic functionality test
    test_predictor = TestChurnPredictor()
    test_predictor.setup_method()
    
    try:
        test_predictor.test_full_pipeline()
        test_data_quality_checks()
        test_workshop_timing()
        
        print("\n🎉 All tests passed! Session 7 ML pipeline is ready for workshop.")
        
    except Exception as e:
        print(f"\n❌ Test failed: {str(e)}")
        sys.exit(1)