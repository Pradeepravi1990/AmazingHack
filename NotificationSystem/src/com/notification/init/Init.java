package com.notification.init;
public class Init {
	
	public static void main(String[] args) {
		try {
			AppCli app = new AppCli();
			app.start();			
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

}
