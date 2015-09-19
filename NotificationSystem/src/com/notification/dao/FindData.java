package com.notification.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notification.notifier.Notifier;

public class FindData {

	String textUri = "mongodb://admin:1234@ds043981.mongolab.com:43981/proto";
	MongoClient m;
	
	public void checkUpdate(String pid){
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    mongoLogger.setLevel(Level.SEVERE); 
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		
		getDocsPresent(pid);
		Notifier n = new Notifier();
		//n.sendNotification("dd", "dd");	
		m.close();
	}
	
	public FindIterable<Document> retDocsPresent(String pid){
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		FindIterable<Document> iterable = collection.find(
		        new Document("pid", pid));
		return iterable;
	}
	
	public FindIterable<Document> retqueries(){
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		FindIterable<Document> iterable = collection.find(
		        new Document("pid", "-1"));
		return iterable;
	}
	
	public void getDocsPresent(String pid){
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    mongoLogger.setLevel(Level.SEVERE); 
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		FindIterable<Document> iterable = collection.find(
		        new Document("pid", pid));
		for (Document document : iterable) {
			Document d = (Document)document.get("dtl");
			System.out.println(d.toJson());
		}
	}
	
	public void insertQuery(String a){
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    mongoLogger.setLevel(Level.SEVERE); 
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		Document d = new Document();
		d.put("pid", "-1");
		Document atr1  = new Document();
		d.append("cond",atr1);
		collection.insertOne(d);		
		m.close();
	}
	
	
}
