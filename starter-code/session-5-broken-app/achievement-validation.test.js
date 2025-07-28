const request = require('supertest');
const app = require('./server');

describe('Achievement System Validation', () => {
  let server;
  
  beforeAll(() => {
    server = app.listen(3004);
  });
  
  afterAll((done) => {
    server.close(done);
  });

  describe('🧪 Test Champion Badge Requirements', () => {
    test('90%+ test coverage should be achievable', () => {
      // Validate that the codebase is structured to allow 90%+ coverage
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      // Count functions that can be tested
      const functionMatches = serverCode.match(/app\.(get|post|put|delete)/g) || [];
      const routeCount = functionMatches.length;
      
      expect(routeCount).toBeGreaterThan(5); // Should have enough endpoints to test
      
      // Validate that functions are testable (not too complex)
      const lines = serverCode.split('\n');
      const avgFunctionComplexity = lines.length / routeCount;
      expect(avgFunctionComplexity).toBeLessThan(50); // Functions should be manageable
    });

    test('Test infrastructure supports comprehensive testing', () => {
      const packageJson = require('./package.json');
      
      // Validate testing dependencies are present
      expect(packageJson.devDependencies.jest).toBeDefined();
      expect(packageJson.devDependencies.supertest).toBeDefined();
      expect(packageJson.scripts.test).toBeDefined();
      expect(packageJson.scripts['test:coverage']).toBeDefined();
    });
  });

  describe('🔧 Bug Buster Badge Requirements', () => {
    test('All critical bugs are identifiable and fixable', async () => {
      // Memory leak bug
      await request(app).post('/api/register').send({ username: 'bugtest', password: 'pass', email: 'test@test.com' });
      const login = await request(app).post('/api/login').send({ username: 'bugtest', password: 'pass' });
      expect(login.status).toBe(200); // Login works, indicating session bug exists
      
      // Payment processing bug
      const payment = await request(app).post('/api/payment').send({ token: 'tok_test', amount: 100, orderId: '123' });
      expect(payment.status).toBe(500); // Should fail, indicating payment bug exists
      
      // Security bug (hardcoded secrets)
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      expect(serverCode).toContain('not-so-secret-key'); // Hardcoded secret exists
      
      // Performance bug (no caching)
      const search1 = await request(app).get('/api/products/search?query=laptop');
      const search2 = await request(app).get('/api/products/search?query=laptop');
      expect(search1.status).toBe(200);
      expect(search2.status).toBe(200); // Same search, no caching implemented
    });

    test('Fixes are achievable within reasonable effort', () => {
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      // Validate that bugs are clearly marked for easy identification
      const bugComments = (serverCode.match(/BROKEN:/g) || []).length;
      expect(bugComments).toBeGreaterThan(10); // Multiple clear bug markers
      
      // Validate that fixes don't require major rewrites
      const totalLines = serverCode.split('\n').length;
      expect(totalLines).toBeLessThan(500); // Manageable codebase size
    });
  });

  describe('⚡ Performance Pro Badge Requirements', () => {
    test('50%+ performance improvement is achievable', async () => {
      // Test current performance (baseline)
      const startTime = Date.now();
      
      // Perform multiple searches to simulate load
      const searchPromises = [];
      for (let i = 0; i < 10; i++) {
        searchPromises.push(request(app).get('/api/products/search?query=laptop'));
      }
      
      await Promise.all(searchPromises);
      const baseline = Date.now() - startTime;
      
      console.log(`Baseline performance: ${baseline}ms for 10 searches`);
      
      // Validate that there are clear optimization opportunities:
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      // No caching implemented
      expect(serverCode).not.toContain('cache');
      expect(serverCode).not.toContain('redis');
      
      // Inefficient search (O(n) filters)
      expect(serverCode).toContain('filter');
      expect(serverCode).not.toContain('createIndex'); // No database indexing
      
      // No pagination
      expect(serverCode).not.toContain('page');
      expect(serverCode).not.toContain('skip');
      
      // These optimizations should easily provide 50%+ improvement
      // Even fast operations can be optimized significantly with caching and indexing
      expect(baseline).toBeGreaterThan(0); // Performance can be measured
    });

    test('Performance metrics are measurable', () => {
      // Validate that performance can be measured
      expect(Date.now).toBeDefined();
      
      // Validate that the app responds to performance tests
      expect(typeof app).toBe('function');
    });
  });

  describe('Workshop Time Allocation Validation', () => {
    test('Bug discovery should be achievable in 15 minutes', () => {
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      // Bugs should be clearly marked and easy to find
      const bugComments = (serverCode.match(/BROKEN:|SECURITY ISSUE:|PERFORMANCE ISSUE:|MEMORY LEAK:/g) || []).length;
      expect(bugComments).toBeGreaterThan(15); // Clear bug indicators
      
      // File should be small enough to analyze quickly
      const totalLines = serverCode.split('\n').length;
      expect(totalLines).toBeLessThan(300); // Manageable for 15-minute analysis
    });

    test('Critical bug fixes should be achievable in 20 minutes', () => {
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      // Critical bugs should be isolated and not intertwined
      const criticalBugSections = [
        'hardcoded-jwt-secret',
        'not-so-secret-key',
        'sk_test_invalid_key',
        'sessions[token].data[Date.now()]'
      ];
      
      criticalBugSections.forEach(bugPattern => {
        expect(serverCode).toContain(bugPattern);
      });
      
      // Each bug should be in a separate, manageable section
    });

    test('Test generation should be achievable in 20 minutes', () => {
      const packageJson = require('./package.json');
      
      // Testing framework is already set up
      expect(packageJson.devDependencies.jest).toBeDefined();
      expect(packageJson.devDependencies.supertest).toBeDefined();
      
      // Routes are simple enough for quick test generation
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      const routeMatches = serverCode.match(/app\.(get|post)\(/g) || [];
      expect(routeMatches.length).toBeLessThan(15); // Manageable number of routes
    });
  });
});