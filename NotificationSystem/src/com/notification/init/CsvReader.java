package com.notification.init;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.notification.model.csvDataModel;

public class CsvReader {
	public List<csvDataModel> read(String inputPath) throws IOException{		
		List<csvDataModel> loadedData = new ArrayList<csvDataModel>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputPath));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] splitStr = line.split(",");
					csvDataModel dm = new csvDataModel();
					dm.setId(splitStr[0]);
					dm.setCategory(splitStr[1]);
					dm.setData(splitStr[2]);
					loadedData.add(dm);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}finally{
			br.close();
		}
		
		return loadedData;
	}
}
