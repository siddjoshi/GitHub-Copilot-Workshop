# Payment Service Starter Template

## Project Structure
```
payment-service/
├── src/
│   ├── payment-service.js
│   ├── validators/
│   │   └── card-validator.js
│   ├── models/
│   │   └── payment.js
│   └── utils/
│       └── crypto.js
├── tests/
│   ├── unit/
│   └── integration/
├── config/
│   └── database.js
├── .github/
│   └── workflows/
│       └── ci-cd.yml
├── infrastructure/
│   └── terraform/
│       ├── main.tf
│       ├── variables.tf
│       └── outputs.tf
├── docker/
│   └── Dockerfile
└── docs/
    └── api.md
```

## Getting Started

1. **Initialize Project**:
   ```bash
   npm init -y
   npm install express helmet joi bcrypt jsonwebtoken
   npm install --save-dev jest supertest
   ```

2. **Environment Setup**:
   ```bash
   cp .env.example .env
   # Edit .env with your configuration
   ```

3. **Start Development**:
   ```bash
   npm run dev
   ```

## User Stories for Copilot

Use these user stories to guide your development:

### Epic: Payment Processing
**As a customer, I want to process payments securely so that I can complete my purchases.**

#### Story 1: Credit Card Payment
```gherkin
Feature: Credit Card Payment Processing
  As a customer
  I want to pay with my credit card
  So that I can complete my purchase quickly

  Scenario: Successful payment
    Given I have a valid credit card
    When I enter my card details
    And I submit the payment
    Then the payment should be processed
    And I should receive a confirmation

  Scenario: Invalid card details
    Given I have invalid card details
    When I submit the payment
    Then I should see an error message
    And the payment should not be processed
```

#### Story 2: Payment Validation
```gherkin
Feature: Payment Validation
  As a system
  I want to validate payment information
  So that only valid payments are processed

  Scenario: Luhn algorithm validation
    Given a credit card number
    When I validate using Luhn algorithm
    Then I should get a valid/invalid result

  Scenario: Expiry date validation
    Given a card expiry date
    When I check if it's expired
    Then I should get appropriate validation result
```

## API Endpoints to Implement

### Payment Processing
- `POST /api/payments` - Process a payment
- `GET /api/payments/:id` - Get payment status
- `POST /api/payments/:id/refund` - Refund a payment

### Health & Monitoring
- `GET /health` - Health check
- `GET /metrics` - Prometheus metrics
- `GET /api/status` - Service status

## Implementation Guidelines

### Security Requirements
- Input validation for all endpoints
- Rate limiting for payment attempts
- Encryption for sensitive data
- Secure token generation
- PCI DSS compliance considerations

### Error Handling
- Structured error responses
- Appropriate HTTP status codes
- Detailed logging for debugging
- User-friendly error messages

### Testing Requirements
- Unit tests for all business logic
- Integration tests for API endpoints
- Mock external payment gateways
- Performance testing for high load

## Use Copilot Effectively

### Chat Prompts to Try:
```
@workspace analyze this payment service structure and suggest improvements for scalability

/tests generate comprehensive test cases for payment validation logic

@workspace create a comprehensive error handling strategy for payment failures

/fix identify and fix potential security vulnerabilities in this payment code

@workspace generate monitoring and alerting configuration for payment service health
```

### Inline Chat Examples:
```
Add fraud detection logic to prevent suspicious transactions

Implement retry mechanism for failed payment gateway calls

Add rate limiting to prevent payment abuse

Create async payment processing for large transactions
```
