package com.ex.demo1;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);

        MongoClient mongoClient = MongoClients.create("mongodb://localhost");
        MongoDatabase db = mongoClient.getDatabase("users");
        MongoCollection<Document> collection = db.getCollection("profile");

        Document newDocument = new Document("username", "Yan")
                .append("locationLatitude", 37.233345)
                .append("locationLongitude", 35.262134);

        collection.insertOne(newDocument);

   //     db.drop();

        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());

        }

    }

}
