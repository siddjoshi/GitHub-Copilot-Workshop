# Session 5: Application Maintenance with GitHub Copilot

## 🎯 Learning Objectives

By the end of this session, participants will be able to:
- Use GitHub Copilot to diagnose and fix bugs in existing applications
- Generate comprehensive unit tests using `/tests` command
- Implement logging and monitoring improvements with Copilot's assistance
- Refactor code for better performance using `/optimize`
- Use `@workspace` to understand complex application structures

## 📋 Prerequisites

- GitHub Copilot enabled in VS Code
- Basic understanding of Node.js/Express.js or Java/Spring Boot
- Familiarity with debugging concepts

## 🚀 Workshop Scenario

You've inherited a buggy Express.js e-commerce API that has several critical issues:
- Memory leaks in user session handling
- Broken payment processing logic
- Missing error handling and logging
- No unit tests (0% coverage)
- Performance bottlenecks in product search

Your mission: Use GitHub Copilot to transform this broken application into a production-ready service.

## 🛠️ Tasks Overview

### Task 1: Bug Discovery and Analysis (15 minutes)
- Use `/explain` to understand the codebase structure
- Identify bugs using Copilot Chat
- Analyze error logs with `/fix` suggestions

### Task 2: Fix Critical Bugs (20 minutes)
- Fix memory leaks in session management
- Repair broken payment processing
- Implement proper error handling

### Task 3: Add Comprehensive Testing (20 minutes)
- Use `/tests` to generate unit tests
- Achieve 90%+ code coverage
- Test edge cases and error scenarios

### Task 4: Performance Optimization (15 minutes)
- Use `/optimize` for performance improvements
- Implement caching strategies
- Optimize database queries

### Task 5: Logging and Monitoring (10 minutes)
- Add structured logging
- Implement health check endpoints
- Add performance metrics

## 🎮 Achievement System

### 🧪 Test Champion
- **Requirement**: Achieve 90%+ code coverage
- **Points**: 25
- **Badge**: 🧪

### 🔧 Bug Buster
- **Requirement**: Fix all critical bugs without breaking existing functionality
- **Points**: 20
- **Badge**: 🔧

### ⚡ Performance Pro
- **Requirement**: Improve API response time by 50%
- **Points**: 15
- **Badge**: ⚡

### 📊 Monitoring Master
- **Requirement**: Implement complete logging and monitoring
- **Points**: 10
- **Badge**: 📊

## 📚 Key Copilot Commands to Use

```
/explain @workspace #analyze-codebase
/fix #resolve-specific-bugs  
/tests #generate-comprehensive-tests
/optimize #improve-performance
@workspace what are the main issues in this codebase?
```

## 🎯 Success Criteria

- [ ] All critical bugs identified and fixed
- [ ] Unit test coverage above 90%
- [ ] API response time improved by at least 30%
- [ ] Proper error handling and logging implemented
- [ ] Application passes all health checks

## ⏱️ Time Allocation

- **Total Duration**: 80 minutes
- **Setup**: 5 minutes
- **Tasks**: 70 minutes
- **Wrap-up & Discussion**: 5 minutes

## 📖 Getting Started

1. Navigate to `starter-code/session-5-broken-app/`
2. Run `npm install` to install dependencies
3. Try running the app with `npm start`
4. Notice the errors and issues
5. Open GitHub Copilot Chat and start with: `@workspace analyze this Express.js application and identify the main issues`

## 🏆 Bonus Challenges

- **Security Audit**: Use Copilot to identify and fix security vulnerabilities
- **API Documentation**: Generate comprehensive API documentation
- **Docker Optimization**: Optimize the Dockerfile for production deployment
- **Load Testing**: Create load tests to validate performance improvements

---

*💡 Pro Tip: Use `@workspace` frequently to help Copilot understand the full context of your application when making suggestions.*
