const express = require('express');
const session = require('express-session');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const cors = require('cors');
const helmet = require('helmet');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(helmet());
app.use(cors());
app.use(express.json());

// BROKEN: Memory leak - sessions stored in memory without cleanup
app.use(session({
  secret: 'not-so-secret-key', // SECURITY ISSUE: Hardcoded secret
  resave: true,
  saveUninitialized: true,
  cookie: { secure: false } // SECURITY ISSUE: Should be true in production
}));

// Mock database (in-memory storage - PROBLEM: No persistence)
let users = [];
let products = [];
let orders = [];
let sessions = {}; // MEMORY LEAK: Never cleaned up

// BROKEN: No error handling middleware
// BROKEN: No logging
// BROKEN: No rate limiting
// BROKEN: No input validation

// User registration endpoint
app.post('/api/register', async (req, res) => {
  try {
    const { username, password, email } = req.body;
    
    // BROKEN: No input validation
    // BROKEN: No duplicate user check
    
    const hashedPassword = await bcrypt.hash(password, 10);
    const user = {
      id: users.length + 1,
      username,
      email,
      password: hashedPassword,
      createdAt: new Date()
    };
    
    users.push(user);
    
    // BROKEN: Sending sensitive data back
    res.json({ user });
  } catch (error) {
    // BROKEN: Exposing internal errors
    res.status(500).json({ error: error.message });
  }
});

// User login endpoint
app.post('/api/login', async (req, res) => {
  const { username, password } = req.body;
  
  // BROKEN: No rate limiting on login attempts
  // BROKEN: Vulnerable to timing attacks
  
  const user = users.find(u => u.username === username);
  if (!user) {
    return res.status(401).json({ error: 'Invalid credentials' });
  }
  
  const validPassword = await bcrypt.compare(password, user.password);
  if (!validPassword) {
    return res.status(401).json({ error: 'Invalid credentials' });
  }
  
  // BROKEN: JWT secret should be from environment
  const token = jwt.sign({ userId: user.id }, 'hardcoded-jwt-secret');
  
  // MEMORY LEAK: Session data accumulates
  sessions[token] = {
    userId: user.id,
    loginTime: new Date(),
    data: {} // This grows without bounds
  };
  
  res.json({ token });
});

// Product search endpoint - PERFORMANCE ISSUE
app.get('/api/products/search', (req, res) => {
  const { query, category, minPrice, maxPrice } = req.query;
  
  // BROKEN: Inefficient search - O(n) for every search
  // BROKEN: No caching
  // BROKEN: Synchronous operation blocks event loop
  
  let results = products;
  
  if (query) {
    // PERFORMANCE ISSUE: Case-sensitive search, no indexing
    results = results.filter(p => 
      p.name.includes(query) || p.description.includes(query)
    );
  }
  
  if (category) {
    results = results.filter(p => p.category === category);
  }
  
  if (minPrice) {
    results = results.filter(p => p.price >= parseFloat(minPrice));
  }
  
  if (maxPrice) {
    results = results.filter(p => p.price <= parseFloat(maxPrice));
  }
  
  // BROKEN: No pagination - could return thousands of results
  res.json(results);
});

// Payment processing endpoint - CRITICALLY BROKEN
app.post('/api/payment', async (req, res) => {
  const { token, amount, orderId } = req.body;
  
  // BROKEN: No authentication check
  // BROKEN: No input validation
  // BROKEN: No idempotency
  
  try {
    // BROKEN: This will fail - Stripe not properly configured
    const stripe = require('stripe')('sk_test_invalid_key');
    
    const charge = await stripe.charges.create({
      amount: amount * 100, // BROKEN: No validation of amount
      currency: 'usd',
      source: token,
      description: `Order ${orderId}`
    });
    
    // BROKEN: No transaction logging
    // BROKEN: No error recovery
    
    res.json({ success: true, chargeId: charge.id });
  } catch (error) {
    // BROKEN: Exposing payment errors to client
    console.log('Payment error:', error); // BROKEN: Console.log instead of proper logging
    res.status(500).json({ error: error.message });
  }
});

// Order creation endpoint
app.post('/api/orders', (req, res) => {
  const { userId, items, totalAmount } = req.body;
  
  // BROKEN: No authentication
  // BROKEN: No stock validation
  // BROKEN: No price validation
  
  const order = {
    id: orders.length + 1,
    userId,
    items,
    totalAmount,
    status: 'pending',
    createdAt: new Date()
  };
  
  orders.push(order);
  
  // BROKEN: No inventory updates
  // BROKEN: No order confirmation email
  
  res.json(order);
});

// User profile endpoint
app.get('/api/profile', (req, res) => {
  const token = req.headers.authorization?.split(' ')[1];
  
  // BROKEN: No proper JWT validation
  // BROKEN: Sessions never expire
  
  if (!token || !sessions[token]) {
    return res.status(401).json({ error: 'Unauthorized' });
  }
  
  const session = sessions[token];
  const user = users.find(u => u.id === session.userId);
  
  if (!user) {
    return res.status(404).json({ error: 'User not found' });
  }
  
  // BROKEN: Sending password hash
  res.json(user);
});

// Health check endpoint - BROKEN
app.get('/health', (req, res) => {
  // BROKEN: No actual health checks
  // BROKEN: No dependency validation
  res.json({ status: 'OK' }); // Always returns OK even if things are broken
});

// BROKEN: No 404 handler
// BROKEN: No error handling middleware

// Start server
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`); // BROKEN: Console.log instead of proper logging
  
  // Seed some initial data for testing
  products = [
    { id: 1, name: 'Laptop', description: 'High-performance laptop', price: 999.99, category: 'electronics', stock: 10 },
    { id: 2, name: 'Coffee Mug', description: 'Ceramic coffee mug', price: 12.99, category: 'kitchen', stock: 50 },
    { id: 3, name: 'Book', description: 'Programming book', price: 29.99, category: 'books', stock: 25 }
  ];
});

// MEMORY LEAK SIMULATOR: This will cause gradual memory increase
setInterval(() => {
  // Simulate growing session data
  Object.keys(sessions).forEach(token => {
    sessions[token].data[Date.now()] = 'some data that accumulates';
  });
}, 5000);

module.exports = app;
