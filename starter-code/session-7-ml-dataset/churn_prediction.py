"""
Customer Churn Prediction Starter Code
=====================================

This script provides a starting point for building a customer churn prediction model.
Use GitHub Copilot to help complete and improve this analysis!

Instructions:
1. Use Copilot Chat to understand the dataset structure
2. Ask for help with data cleaning and preprocessing
3. Get suggestions for feature engineering
4. Build multiple models with Copilot's assistance
5. Use /tests to generate comprehensive tests

Example Copilot prompts to try:
- "Analyze this customer churn dataset and provide insights"
- "Create a comprehensive EDA for customer churn prediction"
- "Build a feature engineering pipeline for this dataset"
- "Compare multiple ML models for churn prediction"
- "Generate unit tests for this ML pipeline"
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.ensemble import RandomForestClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report, confusion_matrix, roc_auc_score
import warnings
warnings.filterwarnings('ignore')

# Set plotting style
plt.style.use('seaborn-v0_8')
sns.set_palette("husl")

class ChurnPredictor:
    """
    Customer Churn Prediction Pipeline
    
    This class encapsulates the entire ML pipeline for churn prediction.
    Use GitHub Copilot to help implement the missing methods!
    """
    
    def __init__(self):
        self.data = None
        self.X_train = None
        self.X_test = None
        self.y_train = None
        self.y_test = None
        self.models = {}
        self.scalers = {}
        self.encoders = {}
        
    def load_data(self, file_path=None):
        """
        Load customer churn dataset
        
        TODO: Implement data loading logic
        Ask Copilot: "Help me load and validate the customer churn dataset"
        """
        # Generate sample data for demonstration
        np.random.seed(42)
        n_samples = 1000
        
        self.data = pd.DataFrame({
            'customerID': [f'customer_{i}' for i in range(n_samples)],
            'gender': np.random.choice(['Male', 'Female'], n_samples),
            'SeniorCitizen': np.random.choice([0, 1], n_samples, p=[0.8, 0.2]),
            'Partner': np.random.choice(['Yes', 'No'], n_samples),
            'Dependents': np.random.choice(['Yes', 'No'], n_samples, p=[0.3, 0.7]),
            'tenure': np.random.randint(1, 73, n_samples),
            'PhoneService': np.random.choice(['Yes', 'No'], n_samples, p=[0.9, 0.1]),
            'InternetService': np.random.choice(['DSL', 'Fiber optic', 'No'], n_samples, p=[0.4, 0.4, 0.2]),
            'Contract': np.random.choice(['Month-to-month', 'One year', 'Two year'], n_samples, p=[0.5, 0.3, 0.2]),
            'MonthlyCharges': np.random.uniform(18, 120, n_samples),
            'TotalCharges': np.random.uniform(18, 8000, n_samples),
            'Churn': np.random.choice(['Yes', 'No'], n_samples, p=[0.27, 0.73])
        })
        
        print(f"Dataset loaded successfully with {len(self.data)} records")
        return self.data
    
    def explore_data(self):
        """
        Perform Exploratory Data Analysis
        
        TODO: Add comprehensive EDA
        Ask Copilot: "Create comprehensive EDA for customer churn analysis"
        """
        print("Dataset Info:")
        print("=" * 50)
        print(self.data.info())
        
        print("\nDataset Description:")
        print("=" * 50)
        print(self.data.describe())
        
        print("\nChurn Distribution:")
        print("=" * 50)
        print(self.data['Churn'].value_counts())
        
        # TODO: Add more EDA visualizations
        # Ask Copilot to help create:
        # - Distribution plots for numerical features
        # - Count plots for categorical features
        # - Correlation analysis
        # - Churn rate by different segments
        
    def preprocess_data(self):
        """
        Clean and preprocess the dataset
        
        TODO: Implement data preprocessing
        Ask Copilot: "Help me preprocess this customer dataset for ML"
        """
        # TODO: Handle missing values
        # TODO: Encode categorical variables
        # TODO: Scale numerical features
        # TODO: Feature engineering
        
        pass
    
    def engineer_features(self):
        """
        Create new features for better prediction
        
        TODO: Implement feature engineering
        Ask Copilot: "Suggest feature engineering ideas for churn prediction"
        """
        # TODO: Create interaction features
        # TODO: Binning of continuous variables
        # TODO: Customer segment features
        # TODO: Tenure-based features
        
        pass
    
    def train_models(self):
        """
        Train multiple ML models
        
        TODO: Implement model training
        Ask Copilot: "Help me train and compare multiple models for churn prediction"
        """
        # TODO: Split data into train/test
        # TODO: Train multiple models (RF, LogReg, XGBoost, etc.)
        # TODO: Implement cross-validation
        # TODO: Hyperparameter tuning
        
        pass
    
    def evaluate_models(self):
        """
        Evaluate model performance
        
        TODO: Implement comprehensive evaluation
        Ask Copilot: "Create model evaluation framework with multiple metrics"
        """
        # TODO: Calculate multiple metrics
        # TODO: ROC curves
        # TODO: Feature importance analysis
        # TODO: Confusion matrices
        
        pass
    
    def predict_churn(self, customer_data):
        """
        Make churn predictions for new customers
        
        TODO: Implement prediction pipeline
        Ask Copilot: "Create prediction pipeline for new customer data"
        """
        # TODO: Preprocess new data
        # TODO: Make predictions
        # TODO: Return probability scores
        
        pass

def main():
    """
    Main execution function
    
    Use this function to run your complete analysis pipeline.
    Ask Copilot to help you implement each step!
    """
    print("Customer Churn Prediction Analysis")
    print("=" * 50)
    
    # Initialize predictor
    predictor = ChurnPredictor()
    
    # Step 1: Load data
    print("\n1. Loading data...")
    data = predictor.load_data()
    
    # Step 2: Explore data
    print("\n2. Exploring data...")
    predictor.explore_data()
    
    # Step 3: Preprocess data
    print("\n3. Preprocessing data...")
    predictor.preprocess_data()
    
    # Step 4: Engineer features
    print("\n4. Engineering features...")
    predictor.engineer_features()
    
    # Step 5: Train models
    print("\n5. Training models...")
    predictor.train_models()
    
    # Step 6: Evaluate models
    print("\n6. Evaluating models...")
    predictor.evaluate_models()
    
    print("\nAnalysis complete! Check your results and ask Copilot for improvements.")

if __name__ == "__main__":
    main()

# Sample Copilot prompts to try:
"""
After running this code, try these prompts with GitHub Copilot:

1. "Complete the preprocess_data method with proper data cleaning"
2. "Implement comprehensive EDA with visualizations"
3. "Add feature engineering for customer tenure and spending patterns"
4. "Train RandomForest, XGBoost, and Neural Network models"
5. "Create model evaluation with ROC curves and feature importance"
6. "Generate unit tests for the ChurnPredictor class"
7. "Add logging and error handling to the pipeline"
8. "Implement customer segmentation analysis"
9. "Create a deployment-ready prediction API"
10. "Add model monitoring and drift detection"
"""
