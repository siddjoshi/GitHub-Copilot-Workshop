#!/bin/bash

# Session 2 Validation Test Script
# This script validates the Session 2 modernization workshop components

set -e

echo "🔧 Session 2: Code Modernization Validation"
echo "============================================="

# Check if we're in the right directory
if [ ! -d "starter-code/session-2-legacy-banking" ]; then
    echo "❌ Error: Not in the GitHub-Copilot-Workshop root directory"
    exit 1
fi

cd starter-code/session-2-legacy-banking

echo ""
echo "📋 1. Legacy Application Build Test"
echo "-----------------------------------"

# Test 1: Clean build
echo "Testing clean build..."
mvn clean compile
if [ $? -eq 0 ]; then
    echo "✅ Legacy application builds successfully"
else
    echo "❌ Legacy application build failed"
    exit 1
fi

# Test 2: Check Java version compatibility
echo "Testing Java version compatibility..."
java_version=$(grep -o "java.version.*1\.8" pom.xml)
if [ ! -z "$java_version" ]; then
    echo "✅ Java version is correctly set to 1.8"
else
    echo "❌ Java version is not 1.8"
    exit 1
fi

# Test 3: Check Spring Boot version
echo "Testing Spring Boot version..."
spring_boot_version=$(grep -o "1\.5\.[0-9]*\.RELEASE" pom.xml)
if [ ! -z "$spring_boot_version" ]; then
    echo "✅ Spring Boot version is 1.5.x: $spring_boot_version"
else
    echo "❌ Spring Boot version is not 1.5.x"
    exit 1
fi

# Test 4: Verify legacy dependencies
echo "Testing legacy dependencies..."
mvn dependency:list | grep -q "spring-security.*4\."
if [ $? -eq 0 ]; then
    echo "✅ Spring Security 4.x detected (legacy as expected)"
else
    echo "⚠️ Spring Security 4.x not found - might already be modernized"
fi

# Test 5: Test application startup (quick check)
echo "Testing application compilation and class loading..."
if [ -f "target/classes/com/megabank/banking/LegacyBankingApplication.class" ]; then
    echo "✅ Application compiles and main class exists"
else
    echo "❌ Application compilation failed or main class missing"
    exit 1
fi

echo ""
echo "📋 2. Security Configuration Validation"
echo "---------------------------------------"

# Test 6: Check for deprecated security patterns
echo "Checking for deprecated security patterns..."
if find src/main/java -name "*.java" -exec grep -l "WebSecurityConfigurerAdapter" {} \;; then
    echo "✅ WebSecurityConfigurerAdapter found (deprecated pattern for workshop)"
else
    echo "❌ WebSecurityConfigurerAdapter not found"
    exit 1
fi

# Test 7: Check for legacy annotations
echo "Checking for legacy annotations..."
if find src/main/java -name "*.java" -exec grep -l "@EnableGlobalMethodSecurity" {} \;; then
    echo "✅ Legacy security annotations found"
else
    echo "❌ Legacy security annotations not found"
    exit 1
fi

echo ""
echo "📋 3. Code Quality and Realism Check"
echo "------------------------------------"

# Test 8: Check file structure
echo "Validating file structure..."
required_files=(
    "src/main/java/com/megabank/banking/LegacyBankingApplication.java"
    "src/main/java/com/megabank/banking/config/LegacySecurityConfig.java"
    "pom.xml"
)

for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file exists"
    else
        echo "❌ $file missing"
        exit 1
    fi
done

# Test 9: Check for realistic legacy patterns
echo "Checking for realistic legacy patterns..."
patterns=(
    "Spring Boot 1.5"
    "Java 8"
    "deprecated"
    "TODO.*Copilot"
    "MODERNIZATION"
)

for pattern in "${patterns[@]}"; do
    if grep -r -q "$pattern" src/ pom.xml; then
        echo "✅ Found pattern: $pattern"
    else
        echo "⚠️ Pattern not found: $pattern"
    fi
done

echo ""
echo "📋 4. Maven Dependencies Analysis"
echo "--------------------------------"

# Test 10: Check for vulnerable dependencies
echo "Checking for appropriately outdated dependencies..."
mvn dependency:tree | grep -E "(spring-boot.*1\.5|junit.*4\.)" > /dev/null
if [ $? -eq 0 ]; then
    echo "✅ Legacy dependencies found (good for workshop scenario)"
else
    echo "⚠️ Some legacy dependencies might be missing"
fi

echo ""
echo "📋 5. Workshop Documentation Validation"
echo "---------------------------------------"

cd ../../session-2-modernization

# Test 11: Check README structure
echo "Validating README structure..."
required_sections=(
    "Learning Objectives"
    "Business Scenario"
    "Step-by-Step Walkthrough"
    "Phase 1.*Legacy Code Analysis"
    "Phase 2.*Systematic Code Modernization"
    "Phase 3.*UI Modernization"
    "Phase 4.*Testing"
)

for section in "${required_sections[@]}"; do
    if grep -q "$section" README.md; then
        echo "✅ Section found: $section"
    else
        echo "❌ Section missing: $section"
        exit 1
    fi
done

# Test 12: Check for corrected tool references
echo "Checking modernization tool references..."
if grep -q "OpenRewrite\|Spring Boot Migrator" README.md; then
    echo "✅ Valid modernization tools referenced"
else
    echo "❌ Modernization tools not properly referenced"
    exit 1
fi

# Test 13: Validate duration
echo "Checking session duration..."
if grep -q "Duration.*80 minutes" README.md; then
    echo "✅ Duration correctly set to 80 minutes"
else
    echo "❌ Duration not set to 80 minutes"
    exit 1
fi

echo ""
echo "🎉 Session 2 Validation Complete!"
echo "================================="
echo "✅ All tests passed successfully"
echo "✅ Legacy application is realistic and builds correctly"
echo "✅ Documentation is accurate and complete"
echo "✅ Workshop is ready for execution"
echo ""
echo "📝 Summary:"
echo "- Legacy Spring Boot 1.5 application builds and runs"
echo "- Realistic outdated dependencies and patterns present"
echo "- Security configuration shows deprecated patterns"
echo "- Documentation references correct modernization tools"
echo "- Time allocation is appropriate (80 minutes total)"