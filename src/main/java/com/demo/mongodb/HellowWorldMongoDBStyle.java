package com.demo.mongodb;
import com.mongodb.*;
import java.net.UnknownHostException;

public class HellowWorldMongoDBStyle {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("hello");
        DBObject document = collection.findOne();
        System.out.println(document);

        DBCursor documents = collection.find();
        // Iterate through the cursor
        for (DBObject d : documents) {
            System.out.println(d);
        }
        // Or
        while (documents.hasNext()) {
            System.out.println(documents.next());
        }
        
        // Close the cursor
        documents.close();
    }
}