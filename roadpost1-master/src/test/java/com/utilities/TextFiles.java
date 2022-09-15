package com.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TextFiles {
	
	public static String ImeiToAdd;
	public static File file1 = new File("C:\\Users\\sumanth.manda\\eclipse-workspace\\demo\\roadpost1\\Resources\\Data\\TextFiles\\NewIMEIs.txt");
    public static File file2 = new File("C:\\Users\\sumanth.manda\\eclipse-workspace\\demo\\roadpost1\\Resources\\Data\\TextFiles\\OldIMEIs.txt");
    public static String NewImeis;
    public static String OldImeis = null;
    
    public static String CompareTwoTextFiles() throws Exception
    {
    	FileReader newimeis =new FileReader(file1);
    	FileReader oldimeis =new FileReader(file2);
    	BufferedReader bnewimeis = new BufferedReader(newimeis);
    	BufferedReader boldimeis = new BufferedReader(oldimeis);
    	
    	String NewImeis=bnewimeis.readLine();
    	ArrayList<String> ar =new ArrayList<String>();
    	while ((OldImeis = boldimeis.readLine())!=null)
    	{
    		ar.add(OldImeis);
    	}
    	int j=ar.size();
    	int i;
    	
    	for(i=0;i<=j;i++)
    	{
    		if (ar.contains(NewImeis))
    		{
    			System.out.println(NewImeis+"is a used Imei");
    			NewImeis =bnewimeis.readLine();
    		}
    	}
    	System.out.println(NewImeis+"is a unused Imei");
    	ImeiToAdd=NewImeis;
    	return ImeiToAdd;
    }
    
    public static void writedata()throws Exception
    {
    	Scanner sc=new Scanner(file2);
    	StringBuffer buffer = new StringBuffer();
    	while(sc.hasNextLine())
    	{
    		buffer.append(sc.nextLine()+System.lineSeparator());
    	}
    	String fileContents=buffer.toString();
    	sc.close();
    	
    	FileWriter fr=new FileWriter(file2);
    	BufferedWriter writer=new BufferedWriter(fr);
    	writer.write(fileContents+ImeiToAdd);
    	writer.close();
    	System.out.println("the Imei and Serial Number"+ImeiToAdd+"is added to UsedImeis");
    }
}
