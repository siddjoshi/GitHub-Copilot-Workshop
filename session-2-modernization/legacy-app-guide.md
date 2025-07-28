# Legacy Banking Application - Modernization Template

## Current Application Overview

This is a simplified legacy banking application built with outdated technologies that needs comprehensive modernization.

### Current Technology Stack
- **Framework**: Spring Boot 1.5.22
- **Java Version**: Java 8
- **Build Tool**: Maven 3.6
- **Database**: H2 (in-memory for demo)
- **Security**: Spring Security 4.x
- **UI**: JSP with Bootstrap 3
- **Testing**: JUnit 4, Mockito 1.x

## Known Issues & Technical Debt

### Security Vulnerabilities
- Spring Boot 1.5 has multiple CVEs
- Deprecated security configurations
- Weak password hashing algorithms
- Missing CSRF protection
- No security headers

### Performance Issues
- Synchronous processing only
- No connection pooling
- Inefficient query patterns
- Large object creation in loops
- No caching mechanisms

### Code Quality Issues
- Deprecated annotations and methods
- Tightly coupled components
- Missing error handling
- No input validation
- Inconsistent logging

## Modernization Targets

### Framework Upgrades
- Spring Boot 1.5 → 3.2+
- Spring Security 4.x → 6.x
- Spring Data JPA → 3.x
- Java 8 → Java 21 LTS

### Architecture Improvements
- Monolith → Microservices
- Synchronous → Reactive patterns
- JSP → React/Angular SPA
- H2 → PostgreSQL/MySQL

### DevOps Enhancements
- Docker containerization
- Kubernetes deployment
- CI/CD automation
- Infrastructure as Code

## File Structure
```
legacy-banking-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bank/
│   │   │           ├── BankingApplication.java
│   │   │           ├── config/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   └── DatabaseConfig.java
│   │   │           ├── controller/
│   │   │           │   ├── AccountController.java
│   │   │           │   ├── TransactionController.java
│   │   │           │   └── UserController.java
│   │   │           ├── service/
│   │   │           │   ├── AccountService.java
│   │   │           │   ├── TransactionService.java
│   │   │           │   └── UserService.java
│   │   │           ├── repository/
│   │   │           │   ├── AccountRepository.java
│   │   │           │   ├── TransactionRepository.java
│   │   │           │   └── UserRepository.java
│   │   │           └── model/
│   │   │               ├── Account.java
│   │   │               ├── Transaction.java
│   │   │               └── User.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── bank/
│                   ├── BankingApplicationTests.java
│                   └── service/
├── pom.xml
├── Dockerfile.old
└── README.md
```

## Copilot Modernization Prompts

### Initial Analysis
```
@workspace analyze this legacy banking application and create a comprehensive modernization assessment:

1. SECURITY ANALYSIS:
   - List all security vulnerabilities
   - Identify deprecated security patterns
   - Suggest modern authentication/authorization

2. PERFORMANCE BOTTLENECKS:
   - Identify synchronous blocking operations
   - Find inefficient database queries
   - Suggest reactive programming opportunities

3. CODE QUALITY ISSUES:
   - List deprecated APIs and their replacements
   - Identify tight coupling and suggest improvements
   - Find missing error handling and validation

4. MODERNIZATION PRIORITY:
   - Rank issues by severity and business impact
   - Create phased migration plan
   - Estimate effort and timeline

Generate a detailed markdown report with actionable recommendations.
```

### Dependency Modernization
```
@workspace examine the pom.xml file and create a comprehensive dependency upgrade plan:

1. CORE FRAMEWORK UPGRADES:
   - Spring Boot 1.5 → 3.2 migration path
   - Spring Security 4.x → 6.x changes
   - Spring Data JPA compatibility updates

2. JAVA VERSION MIGRATION:
   - Java 8 → 21 LTS upgrade considerations
   - New language features to adopt
   - Performance improvements to expect

3. TESTING FRAMEWORK UPDATES:
   - JUnit 4 → 5 migration
   - Mockito modernization
   - New testing patterns to implement

4. NEW DEPENDENCIES TO ADD:
   - Micrometer for metrics
   - OpenAPI for documentation
   - Testcontainers for integration tests

Create updated pom.xml with proper version management.
```

### Security Modernization
```
Modernize the Spring Security configuration for the banking application:

1. AUTHENTICATION IMPROVEMENTS:
   - Replace deprecated WebSecurityConfigurerAdapter
   - Implement JWT-based authentication
   - Add OAuth2 Resource Server support
   - Configure password encoding with modern algorithms

2. AUTHORIZATION ENHANCEMENTS:
   - Implement method-level security
   - Add role-based access control
   - Configure CORS for API access
   - Add security headers configuration

3. API SECURITY:
   - Implement rate limiting
   - Add request validation
   - Configure CSRF protection
   - Add audit logging

Generate complete SecurityConfig class with modern patterns.
```

### Performance Optimization
```
Transform this banking application to use modern performance patterns:

1. REACTIVE PROGRAMMING:
   - Convert blocking operations to reactive
   - Implement WebFlux where appropriate
   - Add async processing for transactions
   - Use virtual threads for concurrent operations

2. DATABASE OPTIMIZATION:
   - Add connection pooling configuration
   - Implement caching strategies
   - Optimize query patterns
   - Add database monitoring

3. OBSERVABILITY:
   - Add Micrometer metrics
   - Implement distributed tracing
   - Configure health checks
   - Add performance monitoring

Update all service classes with modern performance patterns.
```

## Expected Modernization Outcomes

### Before vs After Comparison

| Aspect | Before (Legacy) | After (Modern) |
|--------|----------------|----------------|
| Framework | Spring Boot 1.5 | Spring Boot 3.2+ |
| Java | 8 | 21 LTS |
| Security | Basic Auth | JWT + OAuth2 |
| API | REST (blocking) | Reactive REST |
| Testing | JUnit 4 | JUnit 5 + Testcontainers |
| Deployment | WAR file | Docker + K8s |
| Monitoring | Basic logging | Metrics + Tracing |

### Performance Improvements Expected
- **Startup Time**: 50% faster with Java 21
- **Memory Usage**: 30% reduction with modern GC
- **Throughput**: 3x improvement with reactive patterns
- **Security**: Zero known vulnerabilities

### Developer Experience Improvements
- Modern IDE support with Java 21
- Better debugging and profiling tools
- Automated testing with containers
- Improved build and deployment speed

## Testing Strategy for Modernization

### Unit Testing Modernization
```
/tests generate comprehensive unit tests for the modernized banking application:

1. SERVICE LAYER TESTS:
   - Account service with all business logic
   - Transaction service with edge cases
   - User service with validation scenarios

2. REPOSITORY LAYER TESTS:
   - Database operations with Testcontainers
   - Custom query methods
   - Transaction isolation tests

3. SECURITY TESTS:
   - Authentication scenarios
   - Authorization edge cases
   - Security configuration validation

Use JUnit 5, Mockito 4+, and modern testing patterns.
```

### Integration Testing
```
Create comprehensive integration tests for the banking API:

1. API ENDPOINT TESTS:
   - Happy path scenarios
   - Error handling validation
   - Security integration tests

2. DATABASE INTEGRATION:
   - Data persistence validation
   - Transaction rollback scenarios
   - Performance under load

3. SECURITY INTEGRATION:
   - Authentication flow tests
   - Authorization boundary tests
   - CORS and CSRF validation

Use TestRestTemplate, @SpringBootTest, and Testcontainers.
```
