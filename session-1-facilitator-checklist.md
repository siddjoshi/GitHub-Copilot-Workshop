# 📋 Session 1 Facilitator Checklist

## Pre-Workshop Setup (30 minutes before)

### Environment Preparation
- [ ] Verify all participants have VS Code with GitHub Copilot extensions
- [ ] Confirm Node.js 18+ is installed on all machines
- [ ] Test repository clone: `git clone [repository-url]`
- [ ] Run validation script: `bash validate-session-1.sh`
- [ ] Prepare backup environment in case of technical issues

### Materials Check
- [ ] Session 1 README.md accessible
- [ ] Starter code dependencies installed (`npm install`)
- [ ] All external links working (especially attribution link)
- [ ] Presentation materials ready
- [ ] Timer set for 80 minutes

## Workshop Execution Checklist

### Introduction (5 minutes)
- [ ] Welcome and session overview
- [ ] Learning objectives review
- [ ] Attribution acknowledgment to original workshop
- [ ] Business scenario explanation (TechCorp E-commerce)
- [ ] Gamification system introduction (50 points, 7 checkpoints)

### Phase 1: Project Planning (20 minutes)

#### Checkpoint 1.1: GitHub Issue Creation (10 minutes)
- [ ] Demonstrate @github participant
- [ ] Guide issue template generation
- [ ] Verify participants can create issues
- [ ] **Points awarded:** 10 points
- [ ] **Common issues:** Participants may need GitHub authentication

#### Checkpoint 1.2: Development Plan Creation (10 minutes)  
- [ ] Introduce @workspace participant
- [ ] Show project structure analysis
- [ ] Guide technical specification creation
- [ ] **Points awarded:** 10 points
- [ ] **Common issues:** Large projects may take time to index

### Phase 2: Implementation (35 minutes)

#### Checkpoint 1.3: Payment Service Implementation (20 minutes)
- [ ] Review TODO comments in payment-service.js
- [ ] Demonstrate Copilot code generation
- [ ] Guide Luhn algorithm implementation
- [ ] Show error handling patterns
- [ ] **Points awarded:** 15 points
- [ ] **Common issues:** Copilot suggestions may vary; use as learning opportunity

#### Checkpoint 1.4: Testing & Quality Assurance (15 minutes)
- [ ] Demonstrate `/tests` slash command
- [ ] Show test coverage generation
- [ ] Guide test execution (`npm test`)
- [ ] Review ESLint warnings (expected)
- [ ] **Points awarded:** 10 points
- [ ] **Common issues:** Coverage thresholds may not be met initially

### Phase 3: CI/CD & Deployment (25 minutes)

#### Checkpoint 1.5: CI/CD Pipeline Generation (15 minutes)
- [ ] Introduce @terminal participant
- [ ] Guide GitHub Actions workflow creation
- [ ] Show deployment configuration examples
- [ ] Demonstrate security scanning integration
- [ ] **Points awarded:** 10 points
- [ ] **Common issues:** Complex pipelines may need simplification

#### Checkpoint 1.6: Monitoring Setup (10 minutes)
- [ ] Guide health check endpoint review
- [ ] Show logging implementation examples
- [ ] Demonstrate monitoring code generation
- [ ] Review metrics collection patterns
- [ ] **Points awarded:** 5 points
- [ ] **Common issues:** External monitoring services may require accounts

### Wrap-up & Discussion (10 minutes)
- [ ] Final validation run (`npm run build`)
- [ ] Points calculation and "AI Pioneer" badge awards
- [ ] Key learnings discussion
- [ ] Q&A session
- [ ] Next session preview

## Troubleshooting Guide

### Common Technical Issues

#### Server Won't Start
```bash
# Check port availability
lsof -i :3000
# Kill if needed
pkill -f "node src/server.js"
# Restart
npm start
```

#### NPM Install Failures
```bash
# Clear cache
npm cache clean --force
# Delete node_modules
rm -rf node_modules package-lock.json
# Reinstall
npm install
```

#### ESLint Errors
- **Expected:** Console warnings are acceptable in development
- **Unexpected:** Syntax errors should be addressed immediately
- **Solution:** Run `npm run lint` to see specific issues

#### Copilot Not Responding
- Check internet connection
- Verify GitHub Copilot subscription status
- Try restarting VS Code
- Use manual examples as fallback

### Time Management Tips

#### Running Behind Schedule
- **Phase 1:** Skip detailed issue descriptions, focus on workflow
- **Phase 2:** Use pre-implemented code examples if needed
- **Phase 3:** Show rather than implement complex CI/CD

#### Ahead of Schedule
- **Deep dive:** Explore advanced Copilot features
- **Bonus challenges:** Implement additional payment methods
- **Discussion:** Share real-world AI development experiences

## Quality Checkpoints

### Mid-Session Check (40 minutes in)
- [ ] All participants completed Phase 1 successfully
- [ ] Server is running for all participants
- [ ] Basic tests are passing
- [ ] No major technical blockers

### Final Validation
- [ ] Run validation script: `bash validate-session-1.sh`
- [ ] Verify all core endpoints responding
- [ ] Confirm test suite execution
- [ ] Check that TODO comments are being addressed

## Post-Session Actions

### Immediate (within 5 minutes)
- [ ] Collect participant feedback
- [ ] Note any technical issues encountered
- [ ] Save any custom solutions developed
- [ ] Prepare session summary for next facilitator

### Follow-up (within 24 hours)
- [ ] Send participants session materials and links
- [ ] Document lessons learned
- [ ] Update materials based on feedback
- [ ] Prepare improvements for next session

## Emergency Contacts & Resources

### Technical Support
- **Repository Issues:** [Create issue in workshop repository]
- **Copilot Support:** GitHub Copilot documentation
- **Node.js Issues:** Check Node.js compatibility guide

### Backup Materials
- **Pre-implemented solutions** available in `/solutions` directory
- **Alternative examples** for different Copilot responses
- **Offline materials** in case of internet issues

---

**Facilitator Notes:**
- Stay flexible with timing - AI responses can vary
- Encourage experimentation with different Copilot prompts
- Use participant questions as learning opportunities
- Remember: goal is learning, not perfect code implementation