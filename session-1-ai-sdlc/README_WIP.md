# 🎯 Session 1: AI-Powered Software Development Lifecycle

**Duration:** 80 minutes  
**Difficulty:** Intermediate  
**Points Available:** 50 points

## 🙏 Acknowledgment and Attribution

This session builds upon and incorporates concepts from the excellent [**Agents in SDLC Workshop**](https://github.com/sombaner/agents-in-sdlc-workshop) by @sombaner. We gratefully acknowledge this foundational work and encourage participants to explore the original repository for additional insights into AI-powered development workflows.

*This implementation extends and adapts the original concepts for GitHub Copilot's latest features and enterprise scenarios.*

## 🎯 Learning Objectives

By the end of this session, you will:
- ✅ Integrate GitHub Copilot into every SDLC phase
- ✅ Master **Chat Participants** (@workspace, @github, @terminal)
- ✅ Use **Slash Commands** effectively (/tests, /fix, /explain)
- ✅ Generate CI/CD pipelines with AI assistance
- ✅ Create automated test coverage

## 💡 Business Scenario

**TechCorp E-commerce Platform**

You're a senior developer at TechCorp, building a new checkout microservice for their e-commerce platform. The team needs to:
- Implement a robust payment processing system
- Create comprehensive CI/CD pipelines
- Ensure 90%+ test coverage
- Deploy to multiple environments (dev, staging, prod)

**Success Criteria:**
- Functional payment service with validation
- Automated testing and deployment pipeline
- Security scanning integration
- Performance monitoring setup

## 🛠️ Setup & Prerequisites

### Required Tools
- VS Code with GitHub Copilot extensions
- Node.js 18+ installed
- Git configured
- GitHub account

### Workshop Repository
```bash
# Clone the starter repository
git clone https://github.com/workshop/copilot-sdlc-starter
cd copilot-sdlc-starter/session-1
```

### Verify Copilot Status
1. Open VS Code
2. Press `Ctrl+Alt+I` to open Copilot Chat
3. Type: `@github What skills are available?`

Expected response should show available GitHub skills.

## 👣 Step-by-Step Walkthrough

### Phase 1: Project Planning with AI (10 minutes)

#### 🎯 Checkpoint 1.1: Generate User Stories
**Points:** 10 points

1. **Create Project Structure**
   ```bash
   mkdir payment-service
   cd payment-service
   ```

2. **Open Copilot Chat** (`Ctrl+Alt+I`)

3. **Use @workspace participant:**
   ```
   @workspace help me create a comprehensive project structure for an e-commerce payment microservice. Include:
   - User stories for payment processing
   - API endpoint specifications
   - Security requirements
   - Error handling scenarios
   ```

4. **Expected Output**: Copilot should generate:
   - Folder structure
   - User stories in Gherkin format
   - API documentation
   - Security checklist

5. **Apply Suggestions**: Create the recommended files and structure

#### 🎯 Checkpoint 1.2: Generate GitHub Issues
**Points:** 10 points

1. **Create Issues Template**:
   ```
   @github create issues from these user stories:
   
   Feature: Payment Processing
   As a customer
   I want to process payments securely
   So that I can complete my purchase
   
   Scenario: Successful credit card payment
   Given I have valid credit card details
   When I submit the payment
   Then the payment should be processed successfully
   And I should receive a confirmation
   ```

2. **Validation**: Check that issues are created in your repository

### Phase 2: Development with AI Assistance (15 minutes)

#### 🎯 Checkpoint 2.1: Core Service Implementation
**Points:** 15 points

1. **Create `src/payment-service.js`**:
   ```javascript
   // Use Copilot to generate a comprehensive payment service
   // Type the comment below and let Copilot complete:
   
   /**
    * Payment Service for TechCorp E-commerce Platform
    * Handles credit card, PayPal, and digital wallet payments
    * Includes fraud detection and secure token processing
    */
   ```

2. **Let Copilot Generate**: Accept suggestions for the complete service

3. **Use Inline Chat** (`Ctrl+I`) to refine:
   ```
   Add input validation for credit card numbers using Luhn algorithm
   ```

4. **Expected Result**: Complete payment service with:
   - Multiple payment methods
   - Input validation
   - Error handling
   - Security features

#### 🎯 Checkpoint 2.2: Generate Comprehensive Tests
**Points:** 15 points

1. **Select the payment service code**

2. **Use Slash Command**:
   ```
   /tests generate comprehensive unit tests with edge cases and mocking
   ```

3. **Follow up with Chat**:
   ```
   @workspace generate integration tests for the payment service that test:
   - Payment gateway integration
   - Database transactions
   - Error scenarios
   - Performance under load
   ```

4. **Validation**: Ensure tests cover:
   - ✅ Unit tests for all methods
   - ✅ Edge cases and error handling
   - ✅ Mock external dependencies
   - ✅ Integration tests

### Phase 3: CI/CD Pipeline Creation (15 minutes)

#### 🎯 Checkpoint 3.1: GitHub Actions Workflow
**Points:** 10 points

1. **Create `.github/workflows/` directory**

2. **Use Copilot Chat**:
   ```
   @workspace generate a production-ready GitHub Actions workflow for a Node.js payment service with:
   - Multi-environment deployment (dev, staging, prod)
   - Automated testing with coverage reports
   - Security scanning (SAST, dependency check)
   - Docker containerization
   - Blue-green deployment strategy
   - Slack notifications
   ```

3. **Expected Output**: Complete workflow file with:
   - Build and test jobs
   - Security scanning
   - Multi-environment deployment
   - Rollback capabilities

#### 🎯 Checkpoint 3.2: Infrastructure as Code
**Points:** 5 points

1. **Generate Terraform Configuration**:
   ```
   @workspace create Terraform configuration for:
   - AWS ECS cluster for payment service
   - Application Load Balancer
   - RDS database with encryption
   - VPC with proper security groups
   - CloudWatch monitoring and alerting
   ```

2. **Use /optimize command**:
   ```
   /optimize review this Terraform code for cost optimization and security best practices
   ```

### Phase 4: Monitoring & Documentation (5 minutes)

#### 🎯 Checkpoint 4.1: Application Monitoring
**Points:** 5 points

1. **Generate Monitoring Code**:
   ```
   @workspace add comprehensive application monitoring:
   - Prometheus metrics
   - Health check endpoints
   - Performance tracking
   - Error rate monitoring
   - Custom business metrics
   ```

2. **Use /doc command** on the monitoring code:
   ```
   /doc generate API documentation for the monitoring endpoints
   ```

## 📍 Final Validation Checklist

### ✅ Core Requirements
- [ ] Payment service with multiple payment methods
- [ ] Input validation and error handling
- [ ] Comprehensive test suite (unit + integration)
- [ ] CI/CD pipeline with security scanning
- [ ] Infrastructure as Code (Terraform)
- [ ] Monitoring and alerting setup
- [ ] Complete API documentation

### ✅ Copilot Features Used
- [ ] @workspace chat participant
- [ ] @github chat participant
- [ ] /tests slash command
- [ ] /optimize slash command
- [ ] /doc slash command
- [ ] Inline chat (`Ctrl+I`)

## 🎉 Session Wrap-Up

### Key Takeaways

1. **Chat Participants Power**: 
   - `@workspace` understands your entire project context
   - `@github` can create issues and manage repositories
   - `@terminal` helps with command-line tasks

2. **Slash Commands Efficiency**:
   - `/tests` generates comprehensive test suites
   - `/fix` proposes solutions for problems
   - `/doc` creates documentation automatically

3. **Prompt Engineering Best Practices**:
   - Be specific about requirements
   - Include context and constraints
   - Ask for complete solutions, not just snippets

### 🏆 Achievement Unlocked
If you completed all checkpoints:
- **🤖 SDLC Automator**: Successfully integrated AI into full development lifecycle
- **⚡ Pipeline Master**: Created production-ready CI/CD workflows
- **🔧 Test Champion**: Generated comprehensive test coverage

### Next Steps
- Explore advanced testing strategies
- Implement observability patterns
- Set up automated security scanning

---

**🚀 Ready for Session 2? Let's modernize some legacy code with AI assistance!**

### Troubleshooting Common Issues

#### Copilot Not Responding
- Check internet connection
- Restart VS Code
- Sign out and sign back into GitHub

#### Poor Suggestions
- Provide more context in prompts
- Use specific technical terms
- Include examples in your requests

#### Rate Limiting
- Use shorter, more focused prompts
- Take breaks between complex requests
- Switch to different model if available
