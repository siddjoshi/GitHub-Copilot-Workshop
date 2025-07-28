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

1. Load the dataset using pandas
2. Perform exploratory data analysis
3. Clean and preprocess the data
4. Engineer relevant features
5. Build and evaluate machine learning models

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
