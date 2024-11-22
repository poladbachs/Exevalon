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
    // Connect to the MongoDB database
    const client = await MongoClient.connect(mongoUrl, { useNewUrlParser: true });
    const db = client.db(dbName);

    // Insert the form data into the MongoDB collection
    await db.collection('formData').insertOne(formData);

    // Close the database connection
    client.close();

    res.json({ message: 'Form data received and stored successfully!', formData });
  } catch (error) {
    console.error('Error storing form data in MongoDB:', error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});


// Start the server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
