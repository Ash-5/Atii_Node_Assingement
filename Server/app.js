const express = require('express');
var app= express();

const mongoo= require('mongodb').MongoClient
app.use(express.json());

const url = "mongodb://localhost:27017";

mongoo.connect(url, (err, db) => {

    if (err) {
        console.log("Error while connecting mongo client")
    } else {

        const myDb = db.db('mydb')
        const collection = myDb.collection('mytable')
        console.log("Mongo Connected!");
        
        app.post('/detail', (req, res) => {
            console.log("Request url /detail => ", req.body);
            
            const newUser = {
                phone: req.body.phone,
                name: req.body.name,
                age: req.body.age
            }
            const query = { phone: newUser.phone }

            collection.findOne(query, (err, result) => {
                if (result == null) {
                    collection.insertOne(newUser, (err, result) => {
                        res.status(200).json(newUser);
                    })
                } else {
                    res.status(404).send()
                }
            })
        })
    }
});

app.listen(3000, ()=>{
    console.log("Server has started");
});