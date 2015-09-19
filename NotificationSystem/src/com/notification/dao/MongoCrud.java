package com.notification.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.notification.model.csvDataModel;
import com.notification.notifier.Notifier;

public class MongoCrud extends Thread{
	
	String textUri = "mongodb://admin:1234@ds043981.mongolab.com:43981/proto";
	MongoClient m;
	public ArrayBlockingQueue<List<csvDataModel>> store = new ArrayBlockingQueue<List<csvDataModel>>(100);
	public HashMap<String, String> change = new HashMap<String, String>();
	public void run(){
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
	    mongoLogger.setLevel(Level.SEVERE); 
		MongoClientURI uri = new MongoClientURI(textUri);
		m = new MongoClient(uri);
		while(true){
			try {
				if(!store.isEmpty()){
					connectUpdate(store.poll());
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public void close(){
		m.close();
	}
	
	public void connectUpdate(List<csvDataModel> csvDmList){
		
		MongoDatabase db = m.getDatabase("proto");
		MongoCollection<Document> collection = db.getCollection("products");
		List<Document> updateData = prepDocs(csvDmList);
		for (Document document : updateData) {
			saveChange(document);
		}		
		collection.insertMany(updateData);
		System.out.println("Updating db with the latest file...");
		
	}
	
	private List<Document> prepDocs(List<csvDataModel> csvDmList){
		List<Document> docList= new ArrayList<Document>();
		for (csvDataModel csvDataModel : csvDmList) {
			Document dcmtPr = new Document();
			Document dcmtDtl = new Document();
			dcmtDtl.put(csvDataModel.getCategory(), csvDataModel.getData());
			dcmtPr.put("pid",csvDataModel.getId().toString());
			dcmtPr.append("dtl",dcmtDtl);
			docList.add(dcmtPr);
		}
		return docList;
	}
	
	public void saveChange(Document chkDocument){
		FindData fd = new FindData();
		FindIterable<Document>  ad = fd.retDocsPresent(chkDocument.get("pid").toString());
		fd.retqueries();
		for (Document document : ad) {
			System.out.println(document);
		}
	}
	
	
	
}
