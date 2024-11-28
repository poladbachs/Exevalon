// server.js

const express = require('express');
const bodyParser = require('body-parser');
const { MongoClient } = require('mongodb');

const app = express();
const port = 3000;

const mongoUrl = 'mongodb://localhost:27017';
const dbName = 'local';

// Middleware to parse JSON and URL-encoded data
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// CORS setup (you may need to adjust this based on your needs)
app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
  res.header('Access-Control-Allow-Headers', 'Content-Type');
  next();
});

// Endpoint to handle form data
app.post('/api/submit-form', async (req, res) => {
  const formData = req.body;

  try {
    // You can process the form data here (e.g., log it, validate it, etc.)
    console.log('Form data received:', formData);

    // Send a success response to the client
    res.json({ message: 'Form data received successfully!', formData });
  } catch (error) {
    console.error('Error handling form data:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


// Start the server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
