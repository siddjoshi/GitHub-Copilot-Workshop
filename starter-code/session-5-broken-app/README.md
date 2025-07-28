# Broken E-commerce API - Maintenance Workshop

## 🚨 Current Issues

This Express.js e-commerce API has several critical problems that need to be fixed using GitHub Copilot:

### 🔴 Critical Bugs
- **Memory Leak**: Sessions accumulate without cleanup, causing gradual memory increase
- **Payment Processing**: Broken Stripe integration with invalid API key
- **Security Issues**: Hardcoded secrets, exposed error details, no rate limiting
- **Data Validation**: No input validation on critical endpoints
- **Performance Issues**: Inefficient product search with O(n) complexity

### 🟡 Quality Issues
- **No Logging**: Uses console.log instead of proper logging framework
- **No Tests**: Zero test coverage
- **No Error Handling**: Missing error handling middleware
- **No Authentication**: Endpoints missing proper authentication checks
- **Database Issues**: In-memory storage without persistence

### 🟢 Performance Problems
- **Blocking Operations**: Synchronous search operations
- **No Caching**: Product search without caching
- **No Pagination**: Could return thousands of results
- **No Indexing**: No search optimization

## 🎯 Your Mission

Use GitHub Copilot to fix these issues and transform this broken application into a production-ready service:

1. **Fix Memory Leaks**: Implement proper session cleanup
2. **Repair Payment Processing**: Fix Stripe integration
3. **Add Security**: Implement authentication, validation, rate limiting
4. **Improve Performance**: Optimize search and add caching
5. **Add Testing**: Achieve 90%+ test coverage
6. **Implement Logging**: Replace console.log with Winston
7. **Add Error Handling**: Comprehensive error middleware

## 🚀 Getting Started

1. Run `npm install` to install dependencies
2. Try starting the app with `npm start` - notice the errors
3. Open Copilot Chat and ask: `@workspace analyze this Express.js application and identify the main issues`
4. Use `/fix`, `/tests`, `/optimize`, and `/explain` commands to improve the code

## 🧪 Test Coverage Goal

- **Target**: 90%+ test coverage
- **Use**: `/tests` command to generate comprehensive tests
- **Include**: Unit tests, integration tests, error scenarios

## ⚡ Performance Targets

- **API Response Time**: Improve by 50%
- **Memory Usage**: Fix memory leaks
- **Search Performance**: Implement efficient search with caching

Good luck! Use GitHub Copilot to become a maintenance hero! 🦸‍♂️
