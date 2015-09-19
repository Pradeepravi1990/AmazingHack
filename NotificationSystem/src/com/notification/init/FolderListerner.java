package com.notification.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;

import com.notification.dao.MongoCrud;
import com.notification.model.csvDataModel;

public class FolderListerner extends TimerTask {

	String FolderPath;
	Set<String> filesChecked = new HashSet<String>();
	MongoCrud mcrud = new MongoCrud();

	public FolderListerner(String path) {
		FolderPath = path;
		mcrud.start();
	}

	@Override
	public void run() {
		File[] files = new File(this.FolderPath).listFiles();
		if (files == null) {
			System.out.println("> invalid folder name or access to the path might be denied");
			System.out.println("> please check and redo");
		} else {
			if (files.length != filesChecked.size()) {
				for (File file : files) {
					if (file.isFile()) {
						if (!filesChecked.contains(file.getPath())) {
							System.out.println("> Got new file - uploading " + file.getPath());							
							try {
								List<csvDataModel> dataToBeUpdated = new CsvReader().read(file.getAbsolutePath());
								mcrud.store.add(dataToBeUpdated);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							filesChecked.add(file.getPath());
							copyAndDelete(file, new File("/home/pradeep/temp/"+file.getName()));
						}
					}
				}
			} else {
				System.out.println(">>> - Checking Shared folder status - up to date");
			}
		}
	}
	
	public void copyAndDelete(File afile,File bfile){
		InputStream inStream = null;
		OutputStream outStream = null;
			
	    	try{
	    		inStream = new FileInputStream(afile);
	    	    outStream = new FileOutputStream(bfile);	        	
	    	    byte[] buffer = new byte[1024];	    		
	    	    int length;	    	    
	    	    while ((length = inStream.read(buffer)) > 0){	    	  
	    	    	outStream.write(buffer, 0, length);	    	 
	    	    }	    	 
	    	    inStream.close();
	    	    outStream.close();	    	    
	    	    afile.delete();	    	    
	    	    System.out.println("File is movedl successful!");
	    	    
	    	}catch(IOException e){
	    	    e.printStackTrace();
	    	}
	}

}
