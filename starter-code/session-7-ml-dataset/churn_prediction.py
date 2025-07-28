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
        self.X_train_scaled = None
        self.X_test_scaled = None
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
        # Generate realistic sample data for demonstration
        np.random.seed(42)
        n_samples = 1000
        
        # Create more realistic interdependent features
        tenure = np.random.randint(1, 73, n_samples)
        senior_citizen = np.random.choice([0, 1], n_samples, p=[0.84, 0.16])
        
        # Contract type influences tenure and churn
        contract = []
        for t in tenure:
            if t < 12:
                probs = [0.8, 0.15, 0.05]
            elif t < 24:
                probs = [0.4, 0.4, 0.2]
            else:
                probs = [0.2, 0.3, 0.5]
            contract.append(np.random.choice(['Month-to-month', 'One year', 'Two year'], p=probs))
        contract = np.array(contract)
        
        # Monthly charges influenced by services and contract
        base_charges = np.random.uniform(18, 40, n_samples)
        internet_service = np.random.choice(['DSL', 'Fiber optic', 'No'], n_samples, p=[0.35, 0.45, 0.2])
        service_multiplier = np.where(internet_service == 'Fiber optic', 1.8,
                                    np.where(internet_service == 'DSL', 1.3, 1.0))
        monthly_charges = base_charges * service_multiplier + np.random.normal(0, 5, n_samples)
        monthly_charges = np.clip(monthly_charges, 18, 120)
        
        # Total charges based on tenure and monthly charges
        total_charges = monthly_charges * tenure + np.random.normal(0, 100, n_samples)
        total_charges = np.clip(total_charges, 18, 8500)
        
        # Churn probability influenced by multiple factors
        churn_prob = np.full(n_samples, 0.1)  # Base probability
        # Higher churn for month-to-month contracts
        churn_prob += np.where(contract == 'Month-to-month', 0.25, 0.0)
        # Higher churn for new customers
        churn_prob += np.where(tenure < 6, 0.2, 0.0)
        # Higher churn for expensive plans
        churn_prob += np.where(monthly_charges > 80, 0.15, 0.0)
        # Lower churn for senior citizens (more stable)
        churn_prob -= np.where(senior_citizen == 1, 0.1, 0.0)
        
        # Ensure probabilities are valid
        churn_prob = np.clip(churn_prob, 0.05, 0.95)
        churn = np.random.binomial(1, churn_prob, n_samples)
        
        self.data = pd.DataFrame({
            'customerID': [f'customer_{i:04d}' for i in range(n_samples)],
            'gender': np.random.choice(['Male', 'Female'], n_samples),
            'SeniorCitizen': senior_citizen,
            'Partner': np.random.choice(['Yes', 'No'], n_samples, p=[0.48, 0.52]),
            'Dependents': np.random.choice(['Yes', 'No'], n_samples, p=[0.30, 0.70]),
            'tenure': tenure,
            'PhoneService': np.random.choice(['Yes', 'No'], n_samples, p=[0.91, 0.09]),
            'MultipleLines': np.random.choice(['Yes', 'No', 'No phone service'], n_samples, p=[0.42, 0.49, 0.09]),
            'InternetService': internet_service,
            'OnlineSecurity': np.random.choice(['Yes', 'No', 'No internet service'], n_samples, p=[0.34, 0.46, 0.20]),
            'OnlineBackup': np.random.choice(['Yes', 'No', 'No internet service'], n_samples, p=[0.38, 0.42, 0.20]),
            'Contract': contract,
            'PaperlessBilling': np.random.choice(['Yes', 'No'], n_samples, p=[0.59, 0.41]),
            'PaymentMethod': np.random.choice(['Electronic check', 'Mailed check', 'Bank transfer (automatic)', 'Credit card (automatic)'], 
                                           n_samples, p=[0.33, 0.19, 0.22, 0.26]),
            'MonthlyCharges': np.round(monthly_charges, 2),
            'TotalCharges': np.round(total_charges, 2),
            'Churn': np.where(churn == 1, 'Yes', 'No')
        })
        
        print(f"Dataset loaded successfully with {len(self.data)} records")
        print(f"Churn rate: {(self.data['Churn'] == 'Yes').mean():.1%}")
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
        churn_counts = self.data['Churn'].value_counts()
        print(churn_counts)
        print(f"Churn Rate: {(self.data['Churn'] == 'Yes').mean():.1%}")
        
        print("\nChurn by Contract Type:")
        print("=" * 50)
        contract_churn = pd.crosstab(self.data['Contract'], self.data['Churn'], normalize='index') * 100
        print(contract_churn.round(1))
        
        print("\nChurn by Tenure Groups:")
        print("=" * 50)
        tenure_groups = pd.cut(self.data['tenure'], bins=[0, 12, 24, 48, 72], 
                              labels=['0-12', '13-24', '25-48', '49-72'])
        tenure_churn = pd.crosstab(tenure_groups, self.data['Churn'], normalize='index') * 100
        print(tenure_churn.round(1))
        
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
        if self.data is None:
            print("Error: No data loaded. Call load_data() first.")
            return
            
        # Create a copy for preprocessing
        df = self.data.copy()
        
        # Handle categorical variables with label encoding
        categorical_cols = ['gender', 'Partner', 'Dependents', 'PhoneService', 
                          'MultipleLines', 'InternetService', 'OnlineSecurity', 
                          'OnlineBackup', 'Contract', 'PaperlessBilling', 'PaymentMethod']
        
        self.encoders = {}
        for col in categorical_cols:
            if col in df.columns:
                le = LabelEncoder()
                df[col] = le.fit_transform(df[col].astype(str))
                self.encoders[col] = le
        
        # Encode target variable
        target_encoder = LabelEncoder()
        df['Churn'] = target_encoder.fit_transform(df['Churn'])
        self.encoders['Churn'] = target_encoder
        
        # Prepare features and target
        feature_cols = [col for col in df.columns if col not in ['customerID', 'Churn']]
        X = df[feature_cols]
        y = df['Churn']
        
        # Split data
        self.X_train, self.X_test, self.y_train, self.y_test = train_test_split(
            X, y, test_size=0.2, random_state=42, stratify=y
        )
        
        # Scale numerical features
        scaler = StandardScaler()
        numerical_cols = ['SeniorCitizen', 'tenure', 'MonthlyCharges', 'TotalCharges']
        
        self.X_train_scaled = self.X_train.copy()
        self.X_test_scaled = self.X_test.copy()
        
        for col in numerical_cols:
            if col in self.X_train.columns:
                self.X_train_scaled[col] = scaler.fit_transform(self.X_train[[col]])
                self.X_test_scaled[col] = scaler.transform(self.X_test[[col]])
        
        self.scalers['standard'] = scaler
        
        print(f"Preprocessing complete!")
        print(f"Training set: {self.X_train.shape[0]} samples")
        print(f"Test set: {self.X_test.shape[0]} samples")
        print(f"Features: {self.X_train.shape[1]}")
        
        # TODO: Handle missing values if any
        # TODO: More sophisticated feature scaling
        # TODO: Outlier detection and handling
    
    def engineer_features(self):
        """
        Create new features for better prediction
        
        TODO: Implement feature engineering
        Ask Copilot: "Suggest feature engineering ideas for churn prediction"
        """
        if self.X_train is None:
            print("Error: Data not preprocessed. Call preprocess_data() first.")
            return
            
        # Add feature engineering
        # Average monthly charges per tenure month
        self.X_train_scaled['AvgChargesPerMonth'] = self.X_train_scaled['TotalCharges'] / (self.X_train_scaled['tenure'] + 1)
        self.X_test_scaled['AvgChargesPerMonth'] = self.X_test_scaled['TotalCharges'] / (self.X_test_scaled['tenure'] + 1)
        
        # Tenure groups
        self.X_train_scaled['TenureGroup'] = pd.cut(self.X_train['tenure'], 
                                                   bins=[0, 12, 24, 48, 72], 
                                                   labels=[0, 1, 2, 3]).astype(float)
        self.X_test_scaled['TenureGroup'] = pd.cut(self.X_test['tenure'], 
                                                  bins=[0, 12, 24, 48, 72], 
                                                  labels=[0, 1, 2, 3]).astype(float)
        
        # Monthly charges categories
        self.X_train_scaled['ChargesCategory'] = pd.cut(self.X_train['MonthlyCharges'], 
                                                       bins=[0, 35, 65, 95, 200], 
                                                       labels=[0, 1, 2, 3]).astype(float)
        self.X_test_scaled['ChargesCategory'] = pd.cut(self.X_test['MonthlyCharges'], 
                                                      bins=[0, 35, 65, 95, 200], 
                                                      labels=[0, 1, 2, 3]).astype(float)
        
        print("Feature engineering complete!")
        print(f"New feature count: {self.X_train_scaled.shape[1]}")
        
        # TODO: Create interaction features
        # TODO: Binning of continuous variables  
        # TODO: Customer segment features
        # TODO: More sophisticated feature combinations
    
    def train_models(self):
        """
        Train multiple ML models
        
        TODO: Implement model training
        Ask Copilot: "Help me train and compare multiple models for churn prediction"
        """
        if self.X_train_scaled is None:
            print("Error: Features not engineered. Call engineer_features() first.")
            return
            
        from sklearn.ensemble import RandomForestClassifier
        from sklearn.linear_model import LogisticRegression
        from sklearn.metrics import accuracy_score, classification_report
        
        print("Training multiple models...")
        
        # Random Forest
        rf_model = RandomForestClassifier(n_estimators=100, random_state=42, max_depth=10)
        rf_model.fit(self.X_train_scaled, self.y_train)
        rf_pred = rf_model.predict(self.X_test_scaled)
        rf_accuracy = accuracy_score(self.y_test, rf_pred)
        
        # Logistic Regression
        lr_model = LogisticRegression(random_state=42, max_iter=1000)
        lr_model.fit(self.X_train_scaled, self.y_train)
        lr_pred = lr_model.predict(self.X_test_scaled)
        lr_accuracy = accuracy_score(self.y_test, lr_pred)
        
        # Store models and predictions
        self.models = {
            'RandomForest': {
                'model': rf_model,
                'predictions': rf_pred,
                'accuracy': rf_accuracy
            },
            'LogisticRegression': {
                'model': lr_model,
                'predictions': lr_pred,
                'accuracy': lr_accuracy
            }
        }
        
        print(f"Random Forest Accuracy: {rf_accuracy:.3f}")
        print(f"Logistic Regression Accuracy: {lr_accuracy:.3f}")
        
        # Try XGBoost if available
        try:
            import xgboost as xgb
            xgb_model = xgb.XGBClassifier(random_state=42, eval_metric='logloss')
            xgb_model.fit(self.X_train_scaled, self.y_train)
            xgb_pred = xgb_model.predict(self.X_test_scaled)
            xgb_accuracy = accuracy_score(self.y_test, xgb_pred)
            
            self.models['XGBoost'] = {
                'model': xgb_model,
                'predictions': xgb_pred,
                'accuracy': xgb_accuracy
            }
            print(f"XGBoost Accuracy: {xgb_accuracy:.3f}")
        except ImportError:
            print("XGBoost not available. Install with: pip install xgboost")
        
        # TODO: Implement cross-validation
        # TODO: Hyperparameter tuning
        # TODO: More model types (SVM, Neural Networks)
    
    def evaluate_models(self):
        """
        Evaluate model performance
        
        TODO: Implement comprehensive evaluation
        Ask Copilot: "Create model evaluation framework with multiple metrics"
        """
        if not self.models:
            print("Error: No models trained. Call train_models() first.")
            return
            
        from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score
        
        print("\nModel Evaluation Results:")
        print("=" * 60)
        
        for name, model_data in self.models.items():
            predictions = model_data['predictions']
            model = model_data['model']
            
            # Calculate probabilities for AUC
            if hasattr(model, 'predict_proba'):
                probabilities = model.predict_proba(self.X_test_scaled)[:, 1]
                auc_score = roc_auc_score(self.y_test, probabilities)
            else:
                auc_score = "N/A"
            
            accuracy = accuracy_score(self.y_test, predictions)
            precision = precision_score(self.y_test, predictions)
            recall = recall_score(self.y_test, predictions)
            f1 = f1_score(self.y_test, predictions)
            
            print(f"\n{name}:")
            print(f"  Accuracy:  {accuracy:.3f}")
            print(f"  Precision: {precision:.3f}")
            print(f"  Recall:    {recall:.3f}")
            print(f"  F1-Score:  {f1:.3f}")
            if auc_score != "N/A":
                print(f"  AUC-ROC:   {auc_score:.3f}")
            
            # Feature importance for tree-based models
            if hasattr(model, 'feature_importances_'):
                importances = model.feature_importances_
                feature_names = self.X_train_scaled.columns
                important_features = sorted(zip(feature_names, importances), 
                                          key=lambda x: x[1], reverse=True)[:5]
                print(f"  Top Features:")
                for feat, imp in important_features:
                    print(f"    {feat}: {imp:.3f}")
        
        # Find best model
        best_model_name = max(self.models.keys(), 
                            key=lambda x: self.models[x]['accuracy'])
        best_accuracy = self.models[best_model_name]['accuracy']
        
        print(f"\nBest Model: {best_model_name} (Accuracy: {best_accuracy:.3f})")
        
        # Check achievement thresholds
        if best_accuracy > 0.85:
            print("🏆 Achievement Unlocked: ML Wizard (>85% accuracy)!")
        elif best_accuracy > 0.80:
            print("📊 Great performance! Close to ML Wizard achievement.")
        
        # TODO: Confusion matrices
        # TODO: ROC curves
        # TODO: Feature importance visualization
        # TODO: Model comparison plots
    
    def predict_churn(self, customer_data):
        """
        Make churn predictions for new customers
        
        TODO: Implement prediction pipeline
        Ask Copilot: "Create prediction pipeline for new customer data"
        """
        if not self.models:
            print("Error: No models trained. Call train_models() first.")
            return None
            
        # Use the best performing model
        best_model_name = max(self.models.keys(), 
                            key=lambda x: self.models[x]['accuracy'])
        best_model = self.models[best_model_name]['model']
        
        # TODO: Preprocess new customer data with same transformations
        # TODO: Apply feature engineering
        # TODO: Make predictions with confidence scores
        
        print(f"Prediction ready with {best_model_name} model")
        print("Note: Implement data preprocessing pipeline for new customer data")
        
        return best_model_name

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
