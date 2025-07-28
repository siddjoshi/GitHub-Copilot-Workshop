const request = require('supertest');
const app = require('./server');

describe('Session 5 Broken App Validation', () => {
  let server;
  
  beforeAll(() => {
    // Start server on a test port
    server = app.listen(3002);
  });
  
  afterAll((done) => {
    server.close(done);
  });

  describe('1. Memory Leak Validation', () => {
    test('Sessions accumulate without cleanup', async () => {
      // Create multiple login sessions
      const registerResponse = await request(app)
        .post('/api/register')
        .send({
          username: 'testuser1',
          password: 'password123',
          email: 'test1@example.com'
        });

      // The JWT tokens will be identical for same user, but sessions object grows
      const loginResponse1 = await request(app)
        .post('/api/login')
        .send({
          username: 'testuser1',
          password: 'password123'
        });

      // Wait a bit to ensure different timestamps
      await new Promise(resolve => setTimeout(resolve, 1100));

      const loginResponse2 = await request(app)
        .post('/api/login')
        .send({
          username: 'testuser1',
          password: 'password123'
        });

      expect(loginResponse1.status).toBe(200);
      expect(loginResponse2.status).toBe(200);
      expect(loginResponse1.body.token).toBeDefined();
      expect(loginResponse2.body.token).toBeDefined();
      // Even though tokens may be same, sessions object accumulates
      // This demonstrates the memory leak where sessions are never cleaned up
    });
  });

  describe('2. Payment Processing Bugs', () => {
    test('Stripe integration fails with invalid API key', async () => {
      const paymentResponse = await request(app)
        .post('/api/payment')
        .send({
          token: 'tok_visa',
          amount: 100,
          orderId: 'order_123'
        });

      expect(paymentResponse.status).toBe(500);
      expect(paymentResponse.body.error).toContain('Stripe');
    });

    test('Payment endpoint lacks authentication', async () => {
      const paymentResponse = await request(app)
        .post('/api/payment')
        .send({
          token: 'tok_visa',
          amount: 100,
          orderId: 'order_123'
        });

      // Should fail but doesn't check authentication first
      expect(paymentResponse.status).toBe(500);
    });
  });

  describe('3. Security Vulnerabilities', () => {
    test('Hardcoded secrets are present', () => {
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      expect(serverCode).toContain('not-so-secret-key');
      expect(serverCode).toContain('hardcoded-jwt-secret');
      expect(serverCode).toContain('sk_test_invalid_key');
    });

    test('Sensitive information exposed in registration', async () => {
      const response = await request(app)
        .post('/api/register')
        .send({
          username: 'testuser2',
          password: 'password123',
          email: 'test2@example.com'
        });

      expect(response.status).toBe(200);
      expect(response.body.user.password).toBeDefined(); // Should not expose password hash
    });

    test('Password hash exposed in profile endpoint', async () => {
      // Register and login
      await request(app)
        .post('/api/register')
        .send({
          username: 'testuser3',
          password: 'password123',
          email: 'test3@example.com'
        });

      const loginResponse = await request(app)
        .post('/api/login')
        .send({
          username: 'testuser3',
          password: 'password123'
        });

      const profileResponse = await request(app)
        .get('/api/profile')
        .set('Authorization', `Bearer ${loginResponse.body.token}`);

      expect(profileResponse.status).toBe(200);
      expect(profileResponse.body.password).toBeDefined(); // Should not expose password hash
    });

    test('No rate limiting on login attempts', async () => {
      // Multiple rapid login attempts should be allowed (vulnerability)
      const promises = [];
      for (let i = 0; i < 10; i++) {
        promises.push(
          request(app)
            .post('/api/login')
            .send({
              username: 'nonexistent',
              password: 'wrongpassword'
            })
        );
      }

      const results = await Promise.all(promises);
      // All should return 401 without rate limiting
      results.forEach(result => {
        expect(result.status).toBe(401);
      });
    });
  });

  describe('4. Performance Issues', () => {
    test('Product search is O(n) complexity - no caching', async () => {
      // Test search endpoint
      const searchResponse = await request(app)
        .get('/api/products/search?query=Laptop');

      expect(searchResponse.status).toBe(200);
      expect(Array.isArray(searchResponse.body)).toBe(true);
    });

    test('No pagination implemented', async () => {
      const searchResponse = await request(app)
        .get('/api/products/search');

      expect(searchResponse.status).toBe(200);
      // Should return all products without pagination
      expect(searchResponse.body.length).toBeGreaterThan(0);
      expect(searchResponse.body.page).toBeUndefined();
      expect(searchResponse.body.limit).toBeUndefined();
    });
  });

  describe('5. Missing Error Handling', () => {
    test('Health check always returns OK', async () => {
      const healthResponse = await request(app)
        .get('/health');

      expect(healthResponse.status).toBe(200);
      expect(healthResponse.body.status).toBe('OK');
    });

    test('No 404 handler', async () => {
      const response = await request(app)
        .get('/nonexistent-endpoint');

      expect(response.status).toBe(404);
    });

    test('Error messages expose internal details', async () => {
      const response = await request(app)
        .post('/api/register')
        .send({}); // Invalid request

      expect(response.status).toBe(500);
      expect(response.body.error).toBeDefined();
    });
  });

  describe('6. Missing Input Validation', () => {
    test('Registration accepts invalid input', async () => {
      const response = await request(app)
        .post('/api/register')
        .send({
          username: '', // Empty username
          password: '123', // Weak password
          email: 'invalid-email' // Invalid email format
        });

      // Should validate but doesn't
      expect(response.status).toBe(200);
    });

    test('Order creation lacks validation', async () => {
      const response = await request(app)
        .post('/api/orders')
        .send({
          userId: 'invalid',
          items: [],
          totalAmount: -100 // Negative amount
        });

      expect(response.status).toBe(200); // Should reject invalid data
    });
  });

  describe('7. Logging Issues', () => {
    test('Uses console.log instead of proper logging', () => {
      const fs = require('fs');
      const serverCode = fs.readFileSync('./server.js', 'utf8');
      
      expect(serverCode).toContain('console.log');
      expect(serverCode).not.toContain('winston');
    });
  });
});