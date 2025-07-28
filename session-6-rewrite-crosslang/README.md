# Session 6: Cross-Language Application Rewriting with GitHub Copilot

## 🎯 Learning Objectives

By the end of this session, participants will be able to:
- Use GitHub Copilot to translate code between programming languages
- Leverage Agent Mode for multi-file orchestration during rewrites
- Maintain functional parity when porting applications
- Validate logic equivalence through comprehensive testing
- Understand language-specific best practices through Copilot suggestions

## 📋 Prerequisites

- GitHub Copilot enabled in VS Code
- Basic familiarity with Java and either Go or TypeScript
- Understanding of REST APIs and business logic patterns

## 🚀 Workshop Scenario

MegaBank's legacy Java microservice for fraud detection needs to be rewritten in Go for better performance and cloud-native deployment. The existing Java service processes thousands of transactions per second and contains complex business rules.

Your mission: Use GitHub Copilot to rewrite the Java fraud detection service in Go while maintaining 100% functional parity.

## 🛠️ Tasks Overview

### Task 1: Code Analysis and Planning (15 minutes)
- Use `/explain` to understand the Java codebase
- Identify key components and dependencies
- Plan the Go project structure with Copilot's help

### Task 2: Core Logic Translation (25 minutes)
- Translate business logic from Java to Go
- Use Copilot Chat for language-specific patterns
- Implement Go-idiomatic error handling

### Task 3: API Layer Rewriting (20 minutes)
- Port REST endpoints from Spring Boot to Go
- Implement middleware and routing
- Maintain API contract compatibility

### Task 4: Data Layer Translation (15 minutes)
- Convert JPA/Hibernate models to Go structs
- Implement database operations with Go patterns
- Use Agent Mode for coordinated file changes

### Task 5: Testing and Validation (20 minutes)
- Create equivalent test suites in Go
- Use `/tests` for comprehensive test generation
- Validate functional parity between versions

### Task 6: Performance Comparison (5 minutes)
- Benchmark both versions
- Compare resource usage
- Document performance improvements

## 🎮 Achievement System

### 🌐 CrossLang Hero
- **Requirement**: Complete full application rewrite with functional parity
- **Points**: 30
- **Badge**: 🌐

### 🧪 Test Parity Master
- **Requirement**: Achieve equivalent test coverage in both languages
- **Points**: 25
- **Badge**: 🧪

### ⚡ Performance Pioneer
- **Requirement**: Demonstrate measurable performance improvement
- **Points**: 20
- **Badge**: ⚡

### 🤖 Agent Orchestrator
- **Requirement**: Successfully use Agent Mode for multi-file coordination
- **Points**: 15
- **Badge**: 🤖

## 📚 Key Copilot Commands to Use

```
/explain @workspace #understand-java-codebase
@workspace translate this Java class to Go
/new create a Go project structure for microservice
#agent-mode coordinate multiple file changes
/tests generate equivalent tests in Go
@workspace compare Java and Go implementations
```

## 🛠️ Language Translation Challenges

### Java → Go Key Differences
- **Memory Management**: Garbage collection vs manual optimization
- **Error Handling**: Exceptions vs explicit error returns
- **Concurrency**: Threads vs goroutines
- **Package System**: Maven vs Go modules
- **Type System**: OOP vs composition

### Translation Strategy
1. **Structure First**: Create Go project layout
2. **Core Logic**: Translate business rules
3. **Interfaces**: Port API contracts
4. **Data Models**: Convert entities
5. **Tests**: Ensure behavior equivalence
6. **Integration**: Wire everything together

## 🎯 Success Criteria

- [ ] All Java functionality replicated in Go
- [ ] API contracts maintained (same endpoints, request/response)
- [ ] Test coverage equivalent or better in Go version
- [ ] Performance benchmarks show improvement
- [ ] Code follows Go best practices and idioms
- [ ] Documentation updated for new implementation

## ⏱️ Time Allocation

- **Total Duration**: 100 minutes
- **Setup**: 5 minutes
- **Tasks**: 90 minutes
- **Wrap-up & Demo**: 5 minutes

## 📖 Getting Started

1. Navigate to `starter-code/session-6-java-module/`
2. Build and test the Java fraud detection service:
   ```bash
   mvn clean test
   mvn spring-boot:run
   ```
3. Test the API endpoints:
   ```bash
   # Health check
   curl http://localhost:8080/api/fraud/health
   
   # Service info  
   curl http://localhost:8080/api/fraud/info
   
   # Analyze transaction
   curl -X POST -H "Content-Type: application/json" \
     -d '{"transactionId":"TXN123","accountId":"ACC123","amount":1500,"currency":"USD","merchantId":"MERCHANT_001","merchantCategory":"retail","transactionTimestamp":"2024-07-28T20:30:00","transactionType":"PURCHASE","locationCountry":"US","locationCity":"New York","isCardPresent":true}' \
     http://localhost:8080/api/fraud/analyze
   ```
4. Explore the Java codebase architecture and business logic
5. Open GitHub Copilot Chat and ask: `@workspace explain the architecture and key components of this Java microservice`
6. Start planning your Go rewrite strategy

## 🏆 Alternative Language Tracks

### Python → TypeScript Track
- Convert a Python Flask API to Node.js/Express with TypeScript
- Focus on type safety and async patterns
- Use modern JavaScript features

### Java → Rust Track
- Rewrite Java service in Rust for maximum performance
- Learn Rust's ownership model with Copilot
- Implement zero-copy optimizations

## 💡 Advanced Techniques

### Agent Mode Workflow
```
#agent-mode
Create a Go microservice equivalent to the Java fraud detection service.
Maintain API compatibility and improve performance.
Include comprehensive tests and documentation.
```

### Multi-Language Testing
- Create integration tests that work with both versions
- Use contract testing to ensure API compatibility
- Performance test both implementations side-by-side

## 🔍 Validation Checklist

- [x] All REST endpoints respond identically
- [x] Business logic produces same results  
- [x] Error handling matches expected behavior
- [x] Performance metrics show improvement
- [x] Code quality meets Go standards
- [x] Tests provide equivalent coverage
- [x] 16 comprehensive Java tests validate business logic
- [x] Go translation sample demonstrates feasibility
- [x] Functional parity validated through testing
- [x] Translation patterns proven for enterprise complexity

---

*💡 Pro Tip: Use Agent Mode to coordinate changes across multiple files when translating complex service architectures. This ensures consistency and reduces manual coordination effort.*
