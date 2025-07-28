const express = require('express');
const helmet = require('helmet');
const cors = require('cors');
const compression = require('compression');
const rateLimit = require('express-rate-limit');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Security middleware
app.use(helmet());
app.use(cors({
  origin: process.env.NODE_ENV === 'production' 
    ? ['https://techcorp.com', 'https://api.techcorp.com']
    : ['http://localhost:3000', 'http://localhost:3001']
}));

// Performance middleware
app.use(compression());

// Rate limiting - Copilot will help enhance this
const limiter = rateLimit({
  windowMs: parseInt(process.env.RATE_LIMIT_WINDOW) * 60 * 1000, // 15 minutes
  max: parseInt(process.env.RATE_LIMIT_MAX_REQUESTS), // limit each IP to 100 requests per windowMs
  message: {
    error: 'Too many requests from this IP, please try again later.',
    code: 'RATE_LIMIT_EXCEEDED'
  }
});

app.use('/api/', limiter);

// Body parsing middleware
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true }));

// Health check endpoint
app.get('/health', (req, res) => {
  res.status(200).json({
    status: 'healthy',
    timestamp: new Date().toISOString(),
    service: 'payment-service',
    version: process.env.npm_package_version || '1.0.0',
    environment: process.env.NODE_ENV
  });
});

// Basic route - Copilot will help build the payment processing logic
app.get('/api/v1/status', (req, res) => {
  res.json({
    message: 'TechCorp Payment Service is running',
    version: process.env.npm_package_version || '1.0.0',
    endpoints: {
      payments: '/api/v1/payments',
      health: '/health',
      metrics: '/metrics'
    }
  });
});

// TODO: Copilot will help implement these payment routes
// app.use('/api/v1/payments', require('./routes/payments'));
// app.use('/api/v1/refunds', require('./routes/refunds'));
// app.use('/api/v1/webhooks', require('./routes/webhooks'));

// Error handling middleware
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({
    error: 'Something went wrong!',
    message: process.env.NODE_ENV === 'development' ? err.message : 'Internal server error'
  });
});

// 404 handler
app.use('*', (req, res) => {
  res.status(404).json({
    error: 'Endpoint not found',
    path: req.originalUrl,
    method: req.method
  });
});

// Start server
if (require.main === module) {
  app.listen(PORT, () => {
    console.log(`🚀 Payment Service running on port ${PORT}`);
    console.log(`📊 Health check: http://localhost:${PORT}/health`);
    console.log(`🔗 API Status: http://localhost:${PORT}/api/v1/status`);
  });
}

module.exports = app;
