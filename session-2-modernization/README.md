# 🔧 Session 2: Code Modernization with AI

**Duration:** 45 minutes  
**Difficulty:** Intermediate-Advanced  
**Points Available:** 60 points

## 🎯 Learning Objectives

By the end of this session, you will:
- ✅ Use **Edit Mode** for controlled multi-file refactoring
- ✅ Master **Advanced Prompt Engineering** techniques
- ✅ Modernize legacy applications systematically
- ✅ Apply **Image Recognition** for UI modernization
- ✅ Implement **Security Best Practices** during migration

## 💡 Business Scenario

**Legacy Banking Application Modernization**

MegaBank has a 10-year-old Spring Boot 1.5 application that needs urgent modernization:

**Current State:**
- Spring Boot 1.5 (EOL)
- Java 8 (security vulnerabilities)
- Monolithic architecture
- No containerization
- Limited test coverage
- UI from 2014

**Target State:**
- Spring Boot 3.2+
- Java 21 LTS
- Microservices architecture
- Containerized deployment
- 90%+ test coverage
- Modern React UI

**Business Impact:**
- $500K/year security compliance costs
- 40% developer productivity loss
- Customer complaints about slow UI

## 🛠️ Setup & Prerequisites

### Required Tools
- VS Code with GitHub Copilot extensions
- Java 21 JDK
- Maven 3.9+
- Docker Desktop
- Node.js 20+ (for UI modernization)

### Workshop Repository
```bash
# Clone the legacy application
git clone https://github.com/workshop/legacy-banking-app
cd legacy-banking-app/session-2
```

### Latest Copilot Features for This Session
- **Edit Mode**: Multi-file controlled editing
- **Image Input**: UI mockup analysis
- **Model Selection**: Claude Sonnet 3.5 for code analysis
- **Custom Instructions**: Repository-specific guidance

## 👣 Step-by-Step Walkthrough

### Phase 1: Legacy Code Analysis (10 minutes)

#### 🎯 Checkpoint 2.1: Comprehensive Code Audit
**Points:** 15 points

1. **Open Edit Mode**:
   - Open Copilot Chat
   - Select **Edit** from mode dropdown
   - Add all Java files to working set

2. **Advanced Analysis Prompt**:
   ```
   @workspace perform a comprehensive modernization analysis of this Spring Boot application. Create a detailed migration report covering:

   TECHNICAL DEBT ANALYSIS:
   - Deprecated APIs and their modern replacements
   - Security vulnerabilities in dependencies
   - Performance bottlenecks and inefficiencies
   - Code smells and anti-patterns

   MODERNIZATION ROADMAP:
   - Step-by-step migration plan from Spring Boot 1.5 to 3.2
   - Java 8 to Java 21 upgrade path
   - Database migration considerations
   - Breaking changes and compatibility issues

   ARCHITECTURE IMPROVEMENTS:
   - Microservices decomposition strategy
   - API design improvements
   - Caching and performance optimizations
   - Observability and monitoring enhancements

   Generate a markdown report with priority levels and effort estimates.
   ```

3. **Expected Output**: Detailed migration report with:
   - Technical debt assessment
   - Priority-based action items
   - Risk analysis
   - Timeline estimates

#### 🎯 Checkpoint 2.2: Dependency Modernization
**Points:** 10 points

1. **Analyze `pom.xml`**:
   ```
   @workspace examine the Maven dependencies in pom.xml and create a modernization plan:
   
   1. Identify all deprecated dependencies
   2. Find modern replacements with version numbers
   3. Highlight breaking changes
   4. Suggest new dependencies for observability
   5. Create a staged upgrade plan to minimize risk
   ```

2. **Use Claude Sonnet 3.5** (select from model picker):
   - Better for complex dependency analysis
   - Superior reasoning about breaking changes

### Phase 2: Systematic Code Modernization (20 minutes)

#### 🎯 Checkpoint 2.3: Spring Boot Migration
**Points:** 20 points

1. **Configure Edit Mode**:
   - Add `src/main/java/**/*.java` to working set
   - Add `pom.xml` and configuration files

2. **Multi-File Modernization Prompt**:
   ```
   Modernize this Spring Boot application to version 3.2 with the following requirements:

   CORE FRAMEWORK:
   - Upgrade to Spring Boot 3.2.x
   - Replace deprecated @EnableWebMvc with @EnableWebFlux where appropriate
   - Update configuration classes to use modern annotations
   - Replace WebMvcConfigurer with WebFluxConfigurer where needed

   SECURITY UPDATES:
   - Migrate Spring Security configuration to 6.0+ patterns
   - Replace deprecated WebSecurityConfigurerAdapter
   - Implement modern JWT handling
   - Add OAuth2 Resource Server configuration

   JPA & DATABASE:
   - Update to Spring Data JPA 3.0+ syntax
   - Replace deprecated repository methods
   - Add connection pooling configuration
   - Implement audit logging

   OBSERVABILITY:
   - Add Micrometer metrics
   - Configure distributed tracing
   - Add health check endpoints
   - Implement structured logging

   Apply all changes across the working set systematically.
   ```

3. **Review and Apply Changes**:
   - Examine each proposed change
   - Accept/reject modifications selectively
   - Test compilation after changes

#### 🎯 Checkpoint 2.4: Java 21 Language Features
**Points:** 10 points

1. **Modern Java Features**:
   ```
   @workspace modernize the Java code to use Java 21 features:
   
   - Replace traditional switch statements with switch expressions
   - Use pattern matching where applicable
   - Implement record classes for DTOs
   - Use text blocks for multi-line strings
   - Apply sealed classes for type hierarchies
   - Implement virtual threads for async processing
   - Use new collection factory methods
   ```

2. **Performance Optimization**:
   ```
   /optimize analyze this code for Java 21 performance improvements:
   - Memory usage optimizations
   - Garbage collection benefits
   - Virtual thread implementation
   - Stream API enhancements
   ```

### Phase 3: UI Modernization with Image Analysis (10 minutes)

#### 🎯 Checkpoint 2.5: UI Analysis from Screenshots
**Points:** 15 points

1. **Prepare UI Screenshot**:
   - Take screenshot of current banking UI
   - Create mockup of desired modern interface

2. **Image-Based Analysis** (ensure GPT-4o or Claude 3.5 selected):
   ```
   [Attach UI screenshot]
   
   Analyze this banking application interface and create a modernization plan:

   CURRENT UI ANALYSIS:
   - Identify outdated design patterns
   - List accessibility issues
   - Note mobile responsiveness problems
   - Highlight security concerns in UI

   MODERN UI PROPOSAL:
   - Suggest modern component library (Material-UI, Ant Design)
   - Recommend responsive design patterns
   - Propose accessibility improvements
   - Design mobile-first approach

   IMPLEMENTATION PLAN:
   - Generate React component structure
   - Create TypeScript interfaces
   - Implement modern authentication UI
   - Add progressive web app features

   Create a detailed implementation guide.
   ```

3. **Generate Modern React Components**:
   ```
   Based on the UI analysis, generate modern React components for:
   - Dashboard with real-time data
   - Transaction history with infinite scroll
   - Mobile-responsive navigation
   - Accessible form components
   - Dark/light theme support
   ```

### Phase 4: Testing & Deployment Modernization (5 minutes)

#### 🎯 Checkpoint 2.6: Test Modernization
**Points:** 10 points

1. **Modern Testing Strategy**:
   ```
   @workspace modernize the testing approach for the banking application:

   UNIT TESTING:
   - Migrate from JUnit 4 to JUnit 5
   - Implement parameterized tests
   - Add Mockito 4+ features
   - Create test containers for integration tests

   API TESTING:
   - Generate REST Assured test suites
   - Implement contract testing with Pact
   - Add security testing scenarios
   - Create performance test templates

   UI TESTING:
   - Generate Playwright test automation
   - Add visual regression tests
   - Implement accessibility testing
   - Create mobile testing scenarios

   Generate complete test suites with modern patterns.
   ```

2. **Containerization Strategy**:
   ```
   @workspace create a complete containerization strategy:
   - Multi-stage Dockerfile for Java app
   - Docker Compose for local development
   - Kubernetes deployment manifests
   - Helm charts for production deployment
   - Security scanning in container pipeline
   ```

## 📍 Final Validation Checklist

### ✅ Modernization Requirements
- [ ] Spring Boot upgraded to 3.2+
- [ ] Java 21 features implemented
- [ ] Security configuration modernized
- [ ] Database access layer updated
- [ ] Modern UI components created
- [ ] Comprehensive test suite
- [ ] Containerization complete
- [ ] CI/CD pipeline updated

### ✅ Copilot Features Mastered
- [ ] Edit Mode for multi-file changes
- [ ] Image analysis for UI modernization
- [ ] Advanced prompt engineering
- [ ] Model selection optimization
- [ ] Working set management

## 🎉 Session Wrap-Up

### Key Modernization Insights

1. **Systematic Approach**:
   - Start with dependency analysis
   - Plan migration in phases
   - Test at each stage
   - Document breaking changes

2. **AI-Assisted Migration**:
   - Use Edit Mode for large refactoring
   - Leverage image analysis for UI updates
   - Apply different models for different tasks
   - Validate changes incrementally

3. **Risk Mitigation**:
   - Feature flagging for gradual rollout
   - Comprehensive testing strategy
   - Rollback procedures
   - Performance monitoring

### 🏆 Achievement Unlocked
If you completed all checkpoints:
- **🔧 Legacy Modernizer**: Successfully upgraded a complex application
- **🎨 UI Transformer**: Modernized interface with AI assistance
- **⚡ Code Optimizer**: Applied modern language features effectively

### Business Impact Achieved
- **Security**: Eliminated vulnerabilities through modern dependencies
- **Performance**: 40% improvement through Java 21 optimizations
- **Developer Experience**: Modern tooling and patterns
- **User Experience**: Contemporary, accessible interface

---

**🚀 Ready for Session 3? Let's tackle Infrastructure as Code with advanced DevOps patterns!**

### Common Migration Pitfalls to Avoid

#### Dependency Hell
- Always check compatibility matrices
- Test in isolated environments
- Use dependency management tools

#### Breaking Changes
- Read migration guides thoroughly
- Test edge cases extensively
- Have rollback plans ready

#### Performance Regressions
- Benchmark before and after
- Monitor memory usage
- Profile critical paths
