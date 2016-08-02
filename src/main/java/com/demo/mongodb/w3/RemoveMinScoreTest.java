package com.demo.mongodb.w3;


import static com.mongodb.client.model.Filters.eq;
import java.io.IOException;
import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.demo.mongodb.w2.Helper.*;

public class RemoveMinScoreTest {
	public static void main(String[] args) throws IOException {
        MongoDatabase database = new MongoClient().getDatabase("school");

        MongoCollection<Document> collection = database.getCollection("students");

      
        MongoCursor<Document> cursor = collection.find().iterator();
       
        try {
            while( cursor.hasNext() ) {
            	Document lowest = null;
                Document object = cursor.next();
                System.out.println(" Student " + object) ;
                ArrayList<Document> scores =(ArrayList<Document>) object.get("scores");
                for (Document score : scores) {
					if(score.get("type").toString().equalsIgnoreCase("homework")){
						if (lowest == null) lowest = score;
						Float scoreLocal = Float.parseFloat(score.get("score").toString());
						Float lowestLocal = Float.parseFloat(lowest.get("score").toString());
						System.err.println("scoreLocal : " + scoreLocal + " lowestLocal: " + lowestLocal);
						if (scoreLocal < lowestLocal) lowest = score;
					}
				}
				System.err.println("Min: " + lowest);
				scores.remove(lowest);
				object.put("scores", scores);
				prettyPrintJSON(object);
				System.out.println(eq("_id",object.get("_id")));
				//collection.updateOne(eq("_id", `), arg1)
				collection.updateOne(eq("_id",object.get("_id")),  new Document("$set", new Document("scores", scores)));
            }
        }
        finally {
            cursor.close();
        }
    }
}