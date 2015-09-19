package com.notification.init;

import java.io.File;
import java.io.IOException;
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
							System.out.println("> Got new file...uploading " + file.getPath());							
							try {
								List<csvDataModel> dataToBeUpdated = new CsvReader().read(file.getAbsolutePath());
								mcrud.store.add(dataToBeUpdated);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							filesChecked.add(file.getPath());
						}
					}
				}
			} else {
				System.out.println("Shared folder up to date");
			}
		}
	}

}
