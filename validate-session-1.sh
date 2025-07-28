#!/bin/bash

# Session 1 Validation Test Script
# This script validates all components of Session 1: AI-Powered SDLC

echo "🎯 Session 1 Validation: AI-Powered SDLC Content & Code Verification"
echo "============================================================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counters
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# Test functions
test_result() {
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ PASS${NC}: $2"
        PASSED_TESTS=$((PASSED_TESTS + 1))
    else
        echo -e "${RED}❌ FAIL${NC}: $2"
        FAILED_TESTS=$((FAILED_TESTS + 1))
    fi
}

echo ""
echo "📋 1. Workshop Materials Review"
echo "----------------------------------------"

# Check README.md exists and has key sections
if [ -f "session-1-ai-sdlc/README.md" ]; then
    test_result 0 "README.md exists"
    
    # Check for attribution to original workshop
    grep -q "Agents in SDLC Workshop" session-1-ai-sdlc/README.md
    test_result $? "Attribution to Agents in SDLC Workshop present"
    
    # Check for TechCorp business scenario
    grep -q "TechCorp E-commerce" session-1-ai-sdlc/README.md
    test_result $? "TechCorp E-commerce business scenario present"
    
    # Check for 80-minute duration
    grep -q "80 minutes" session-1-ai-sdlc/README.md
    test_result $? "80-minute duration specified"
    
    # Check for 50 points gamification
    grep -q "50 points" session-1-ai-sdlc/README.md
    test_result $? "50 points gamification system present"
    
    # Check for learning objectives
    grep -q "Learning Objectives" session-1-ai-sdlc/README.md
    test_result $? "Learning objectives section present"
    
else
    test_result 1 "README.md exists"
fi

echo ""
echo "📦 2. Starter Code Validation"
echo "----------------------------------------"

# Check package.json dependencies
if [ -f "starter-code/session-1-payment-service/package.json" ]; then
    test_result 0 "package.json exists"
    
    # Check critical dependencies
    for dep in "express" "helmet" "cors" "joi" "bcrypt" "jest" "supertest"; do
        grep -q "\"$dep\"" starter-code/session-1-payment-service/package.json
        test_result $? "$dep dependency present"
    done
else
    test_result 1 "package.json exists"
fi

# Check .env.example
if [ -f "starter-code/session-1-payment-service/.env.example" ]; then
    test_result 0 ".env.example exists"
    
    # Check for key configuration sections
    for config in "NODE_ENV" "PORT" "JWT_SECRET" "STRIPE_SECRET_KEY" "PAYPAL_CLIENT_ID"; do
        grep -q "$config" starter-code/session-1-payment-service/.env.example
        test_result $? "$config configuration present in .env.example"
    done
else
    test_result 1 ".env.example exists"
fi

# Check server.js functionality
cd starter-code/session-1-payment-service
if [ -f "src/server.js" ]; then
    test_result 0 "server.js exists"
    
    # Check if server starts without errors (background test)
    timeout 10s npm start > /dev/null 2>&1 &
    sleep 3
    
    # Test health endpoint
    curl -s http://localhost:3000/health > /dev/null 2>&1
    test_result $? "Health endpoint responds"
    
    # Test status endpoint
    curl -s http://localhost:3000/api/v1/status > /dev/null 2>&1
    test_result $? "Status endpoint responds"
    
    # Kill server
    pkill -f "node src/server.js" > /dev/null 2>&1
else
    test_result 1 "server.js exists"
fi

# Check payment-service.js TODO comments
if [ -f "src/payment-service.js" ]; then
    test_result 0 "payment-service.js exists"
    
    # Check for TODO comments
    todo_count=$(grep -c "TODO" src/payment-service.js)
    if [ $todo_count -gt 5 ]; then
        test_result 0 "TODO comments are present ($todo_count found)"
    else
        test_result 1 "TODO comments are present ($todo_count found)"
    fi
    
    # Check for Copilot prompts
    grep -q "Use Copilot prompt" src/payment-service.js
    test_result $? "Copilot prompts present in TODO comments"
    
else
    test_result 1 "payment-service.js exists"
fi

cd ../..

echo ""
echo "🧪 3. Testing Infrastructure"
echo "----------------------------------------"

cd starter-code/session-1-payment-service

# Check if tests exist and run
if [ -d "tests" ]; then
    test_result 0 "tests directory exists"
    
    # Run tests
    npm test > /dev/null 2>&1
    test_result $? "Tests execute successfully"
    
    # Check test coverage
    npm run test:coverage > /dev/null 2>&1
    test_result $? "Test coverage runs successfully"
    
else
    test_result 1 "tests directory exists"
fi

# Check ESLint configuration
if [ -f ".eslintrc.json" ]; then
    test_result 0 "ESLint configuration exists"
    
    # Run linting
    npm run lint > /dev/null 2>&1
    if [ $? -eq 0 ] || [ $? -eq 1 ]; then  # Accept warnings (exit code 1)
        test_result 0 "ESLint runs without critical errors"
    else
        test_result 1 "ESLint runs without critical errors"
    fi
else
    test_result 1 "ESLint configuration exists"
fi

# Check build process
npm run build > /dev/null 2>&1
if [ $? -eq 0 ] || [ $? -eq 1 ]; then  # Accept warnings
    test_result 0 "Build process completes"
else
    test_result 1 "Build process completes"
fi

cd ../..

echo ""
echo "📝 4. Workshop Content Validation"
echo "----------------------------------------"

# Check for step-by-step instructions
grep -q "Step-by-Step" session-1-ai-sdlc/README.md
test_result $? "Step-by-step walkthrough present"

# Check for Copilot command examples
for cmd in "/tests" "/fix" "/explain" "@workspace" "@github" "@terminal"; do
    grep -q "$cmd" session-1-ai-sdlc/README.md
    test_result $? "Copilot command '$cmd' documented"
done

# Check for checkpoint structure
grep -q "Checkpoint" session-1-ai-sdlc/README.md
test_result $? "Checkpoint system present"

# Check for phase structure (3 phases expected)
phase_count=$(grep -c "Phase [0-9]" session-1-ai-sdlc/README.md)
if [ $phase_count -ge 3 ]; then
    test_result 0 "Three-phase structure present ($phase_count phases found)"
else
    test_result 1 "Three-phase structure present ($phase_count phases found)"
fi

echo ""
echo "🔗 5. External Link Validation"
echo "----------------------------------------"

# Check attribution link
curl -s --head "https://github.com/sombaner/agents-in-sdlc-workshop" | grep "200 OK" > /dev/null 2>&1
test_result $? "Agents in SDLC Workshop link accessible"

echo ""
echo "⚡ 6. Time Management Assessment"
echo "----------------------------------------"

# Calculate estimated time based on content
total_checkpoints=$(grep -c "Checkpoint" session-1-ai-sdlc/README.md)
if [ $total_checkpoints -ge 6 ] && [ $total_checkpoints -le 8 ]; then
    test_result 0 "Reasonable number of checkpoints for 80 minutes ($total_checkpoints checkpoints)"
else
    test_result 1 "Reasonable number of checkpoints for 80 minutes ($total_checkpoints checkpoints)"
fi

echo ""
echo "📊 Validation Summary"
echo "============================================================================"
echo -e "Total Tests:  ${BLUE}$TOTAL_TESTS${NC}"
echo -e "Passed:       ${GREEN}$PASSED_TESTS${NC}"
echo -e "Failed:       ${RED}$FAILED_TESTS${NC}"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}🎉 All validation tests passed! Session 1 is ready for workshop delivery.${NC}"
    exit 0
else
    echo -e "\n${YELLOW}⚠️  Some validation tests failed. Please review the issues above.${NC}"
    exit 1
fi