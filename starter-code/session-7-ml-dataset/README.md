# Customer Churn Prediction Dataset

This dataset contains customer information for predicting churn in a telecommunications company.

## Dataset Overview

- **Total Records**: 7,043 customers
- **Features**: 21 columns (20 features + 1 target)
- **Target Variable**: `Churn` (Yes/No)
- **Use Case**: Binary classification to predict customer churn

## Features Description

### Customer Demographics
- `customerID`: Unique customer identifier
- `gender`: Customer gender (Male/Female)
- `SeniorCitizen`: Whether customer is senior citizen (0/1)
- `Partner`: Whether customer has partner (Yes/No)
- `Dependents`: Whether customer has dependents (Yes/No)

### Account Information
- `tenure`: Number of months customer has stayed
- `Contract`: Contract term (Month-to-month, One year, Two year)
- `PaperlessBilling`: Whether customer uses paperless billing (Yes/No)
- `PaymentMethod`: Payment method type
- `MonthlyCharges`: Monthly charges amount
- `TotalCharges`: Total charges amount

### Services
- `PhoneService`: Whether customer has phone service (Yes/No)
- `MultipleLines`: Whether customer has multiple lines
- `InternetService`: Type of internet service (DSL, Fiber optic, No)
- `OnlineSecurity`: Whether customer has online security (Yes/No/No internet service)
- `OnlineBackup`: Whether customer has online backup
- `DeviceProtection`: Whether customer has device protection
- `TechSupport`: Whether customer has tech support
- `StreamingTV`: Whether customer has streaming TV
- `StreamingMovies`: Whether customer has streaming movies

### Target
- `Churn`: Whether customer churned (Yes/No)

## Data Quality Notes

- Some missing values in `TotalCharges` column (needs cleaning)
- Categorical variables need encoding
- Numerical features may need scaling
- Class imbalance might be present

## Getting Started

### Quick Setup

1. **Install Dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

2. **Run the Basic Pipeline**:
   ```bash
   python churn_prediction.py
   ```

3. **Run Tests**:
   ```bash
   python test_churn_prediction.py
   ```

### What You'll Get

- **Realistic Dataset**: 1000 customers with 19-25% churn rate
- **Working Pipeline**: Complete ML workflow from data to models
- **Multiple Models**: Random Forest, Logistic Regression, XGBoost
- **Performance**: 80%+ accuracy achievable, 85%+ with optimization
- **Testing Suite**: Validates data quality and model performance

### Workshop Flow

1. **Data Exploration (20 min)**: Use Copilot for comprehensive EDA
2. **Feature Engineering (25 min)**: Create predictive features with AI assistance  
3. **Model Training (25 min)**: Build and compare multiple models
4. **Evaluation (20 min)**: Analyze performance and interpretability
5. **Testing (15 min)**: Validate pipeline with comprehensive tests
6. **MLOps (15 min)**: Prepare for deployment and monitoring

## Sample Code

```python
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier

# Load the dataset
df = pd.read_csv('customer_churn.csv')

# Basic exploration
print(df.info())
print(df.describe())
print(df['Churn'].value_counts())
```

---

*Note: This is a synthetic dataset created for educational purposes. Use GitHub Copilot to help with data exploration, feature engineering, and model building!*
