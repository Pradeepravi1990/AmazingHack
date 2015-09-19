package com.notification.dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notification.model.csvDataModel;
import com.notification.notifier.Notifier;

public class FindData {

	String textUri = "mongodb://admin:1234@ds043981.mongolab.com:43981/proto";
	MongoClient m;
	
	public void checkUpdate(String pid){		
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		 System.setProperty("DEBUG.MONGO", "false");

		    // Enable DB operation tracing
		    System.setProperty("DB.TRACE", "false");  
		getDocsPresent(pid);
		Notifier n = new Notifier();
		//n.sendNotification("dd", "dd");	
		m.close();
	}
	
	public void getDocsPresent(String pid){
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		FindIterable<Document> iterable = collection.find(
		        new Document("pid", pid));
		for (Document document : iterable) {
			System.out.println(document);
		}
	}
	
}
