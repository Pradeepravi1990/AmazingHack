package com.notification.init;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.notification.dao.FindData;

public class AppCli extends Thread{
	public void run(){  
		System.out.println("Product Notifier Started and running..."); 
		Scanner as = null;
			try {
				as = new Scanner(System.in);
				System.out.println("> Please configure the system before using");
				System.out.println("> Type 'exit' to terminate ");
				System.out.println("> Type 1 to add subscriber with conditions");
				System.out.println("> Type 2 to view all data ");				
				System.out.println("> Type 3 to continue normal operation");
				String inp = "";
				while(!inp.equalsIgnoreCase("exit")){
					System.out.print("> ");
					
					inp = as.nextLine();
					if(inp.equalsIgnoreCase("1")){
						System.out.println("> <Subscirber mail ID> : <category> : < match operation > : value");
						System.out.println("> Example@yahoo.com : author : = : Rowlling ");
						String query = as.nextLine();
						String[] qsplit = query.split(":");
						if(qsplit.length < 4){
							System.out.println("Specify a valid input");
						}else{
							FindData fd = new FindData();
							fd.insertQuery(query);
						}
					}else if(inp.equalsIgnoreCase("2")){
						System.out.println(" Enter a valid id Eg : 13579");
						String path = as.nextLine();
						FindData fd = new FindData();
						fd.checkUpdate(path);
					}
					else if(inp.equalsIgnoreCase("3")){
						System.out.println("Please enter the shared path location ( any accesible folder on this system ) ");
						String path = as.nextLine();
						kickFolderListerner(path);
					}
					
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(as !=null)as.close();
			}
			
		}
	
	public void kickFolderListerner(String path){
		TimerTask timerTask = new FolderListerner(path);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 10*1000);
        System.out.println("Listening to changes in "+ path);

	}
}
