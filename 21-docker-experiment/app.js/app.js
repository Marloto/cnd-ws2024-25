// app.js
const express = require('express');
const Redis = require('ioredis');
const { MongoClient } = require('mongodb');
const app = express();

// Environmental Variables (PORT, MONGO_URL, ...)
const port = process.env.PORT || 3000;
const mongoUrl = process.env.MONGO_URL || 'mongodb://localhost:27017/stresstest';
const redisHost = process.env.REDIS_HOST || 'localhost';
const redisPort = parseInt(process.env.REDIS_PORT) || 6379;

// Redis Client
const redis = new Redis({
  host: redisHost,
  port: redisPort,
  retry_strategy: (times) => Math.min(times * 100, 3000),
});

// MongoDB Client
const mongoClient = new MongoClient(mongoUrl);
let db;
let healthStatus = true;

// Verbindung zu MongoDB herstellen
async function connectToMongo() {
  try {
    await mongoClient.connect();
    console.log('MongoDB connected');
  } catch (err) {
    console.error('MongoDB connection error:', err);
  }
}
connectToMongo();

// Middleware für JSON
app.use(express.json());

// Basis-Route
app.get('/', (req, res) => {
  res.json({
    status: 'ok',
    hostname: require('os').hostname(),
    timestamp: new Date().toISOString()
  });
});

// CPU-Last erzeugen
app.get('/stress/cpu/:seconds', (req, res) => {
  const seconds = parseInt(req.params.seconds) || 5;
  const start = Date.now();
  
  while (Date.now() - start < seconds * 1000) {
    // CPU-intensive Operation
    Math.random() * Math.random();
  }
  
  res.json({ 
    message: `CPU stress test completed after ${seconds} seconds`,
    hostname: require('os').hostname()
  });
});

// Redis Counter
app.get('/counter', async (req, res) => {
  try {
    const count = await redis.incr('visitor_count');
    res.json({ 
      count,
      hostname: require('os').hostname()
    });
  } catch (err) {
    res.status(500).json({ error: 'Redis error', details: err.message });
  }
});

// Redis Counter
app.get('/hello', async (req, res) => {
  res.json({message: 'Hello, World!', hostname: require('os').hostname()})
});

// MongoDB Logger
app.post('/log', async (req, res) => {
  try {
    const result = await db.collection('logs').insertOne({
      timestamp: new Date(),
      ...req.body
    });
    res.json({ 
      inserted: result.insertedId,
      hostname: require('os').hostname()
    });
  } catch (err) {
    res.status(500).json({ error: 'MongoDB error', details: err.message });
  }
});

// Absturz simulieren
app.get('/crash', (req, res) => {
  res.json({ message: 'Crashing in 1 second...' });
  setTimeout(() => process.exit(1), 1000);
});

// Health-Check
app.get('/health', (req, res) => {
  if(healthStatus) {
    res.status(200).json({ status: 'ok' });
  } else {
    res.status(503).json({ status: 'error' });
  }
});

// Switch State
app.get('/toggle-health', (req, res) => {
  healthStatus = !healthStatus;
  res.status(204).send();
});

// Server starten
app.listen(port, () => {
  console.log(`App listening at http://localhost:${port}`);
});