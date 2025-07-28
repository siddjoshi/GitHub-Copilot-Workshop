const request = require('supertest');
const app = require('../src/server');

describe('Payment Service API', () => {
  describe('Health Check', () => {
    it('should return healthy status', async () => {
      const response = await request(app)
        .get('/health')
        .expect(200);

      expect(response.body).toHaveProperty('status', 'healthy');
      expect(response.body).toHaveProperty('service', 'payment-service');
      expect(response.body).toHaveProperty('timestamp');
    });
  });

  describe('API Status', () => {
    it('should return service status', async () => {
      const response = await request(app)
        .get('/api/v1/status')
        .expect(200);

      expect(response.body).toHaveProperty('message');
      expect(response.body).toHaveProperty('endpoints');
      expect(response.body.message).toContain('TechCorp Payment Service');
    });
  });

  describe('404 Handling', () => {
    it('should return 404 for unknown endpoints', async () => {
      const response = await request(app)
        .get('/unknown-endpoint')
        .expect(404);

      expect(response.body).toHaveProperty('error', 'Endpoint not found');
      expect(response.body).toHaveProperty('path', '/unknown-endpoint');
    });
  });
});