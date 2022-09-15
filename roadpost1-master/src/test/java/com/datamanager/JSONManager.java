package com.datamanager;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.testng.Assert;
import com.utilities.UtilityMethods;



public class JSONManager
{
	private File jFile;
	private JSONParser parser = new JSONParser();
	private static Logger log = LogManager.getLogger("Verify");
	
	
	public void readJsonContent(String pnode, String key, String filePath){
		try {
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
			
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			System.out.println(parent.get(key));
		}
		catch (IOException | ParseException e) {
			log.error("Exception while reading key - "+key+" from JSON file - "+ filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while reading key - "+key+" from JSON file - "+ filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void writeJsonContent(String pnode, String key, String value, String filePath){
		try 
		{
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
		
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			parent.put(key, value);
			updateFile(root_Object, filePath);
		}
		catch (IOException | ParseException e) {
			log.error("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while writing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	
	public void removeJsonContent(String pnode, String key, String filePath){
		try {
			jFile = new File(filePath);
			JSONObject root_Object = (JSONObject) parser.parse(new FileReader(jFile));
		
			JSONObject parent = (JSONObject) root_Object.get(pnode);
			parent.remove(key);
			updateFile(root_Object, filePath);
		}
		catch (IOException | ParseException e) {
			log.error("Exception while removing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception while removing key - "+key+" to JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	
	private void updateFile(JSONObject root_Object, String filePath){
		FileWriter file;
		try {
			file = new FileWriter(filePath);
			file.write(root_Object.toJSONString());
			file.flush();
			file.close();
		}
		catch (IOException e) {
			log.error("Exception accessing JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
			/*Custom*/Assert.fail("Exception accessing JSON file - "+filePath+e.getMessage()+UtilityMethods.getStackTrace());
		}
	}
	public List<String> getValueFromArrayJsonKey(String response,String pNode, String ... key_List) {
		List<String> text = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(response);
			
				JSONArray array_Nodes = (JSONArray) object.get(pNode);
				Iterator<JSONObject> i = array_Nodes.iterator();
				while (i.hasNext())
				{
					int j = 0;
					JSONObject innerObject = i.next();
					for(int k=0;k<=key_List.length-1;k++)
					text.add((String) innerObject.get(key_List[k]));
//					text.add((String) innerObject.get(keys[j+1]));
					
				}
			
		} catch (Exception e) {
			log.error("Exception while reading key - " + pNode
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ pNode + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}
	public List<String> getValueFromSimpleJsonKey(String response, String... array_Key) {
//		String text = null;
		List<String> text = new ArrayList<>();
		try {
				JSONParser parser = new JSONParser();
				JSONObject object = (JSONObject) parser.parse(response);
				for(int i=0;i<=array_Key.length-1;i++)
				text.add(object.get(array_Key[i]).toString());
		  }
		catch (Exception e) {
			log.error("Exception while reading key - " + array_Key
					+ " from JSON - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
			/* Custom */Assert.fail("Exception while reading key - "
					+ array_Key + " from JSON  - " + e.getMessage()
					+ UtilityMethods.getStackTrace());
		}
		return text;
	}

}
