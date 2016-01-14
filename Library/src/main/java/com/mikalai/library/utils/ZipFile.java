package com.mikalai.library.utils;


import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 * Provides work with zip archive
 * 
 * @author Mikalai_Churakou
 */
public class ZipFile {
	public static void compress(String InFile,String OutFile,String fileName) throws Exception
	   {
	    String outFilename  = OutFile;
	    String inFilename   = InFile;
	    
	    
	    FileInputStream in = new FileInputStream(inFilename);
	    ZipOutputStream z = new ZipOutputStream(new FileOutputStream(outFilename));
	    
	    ZipEntry ze = new ZipEntry(fileName);
	    z.putNextEntry(ze);
	    byte[] buffer = new byte[1024];
	    int cnt;
	        while ((cnt = in.read(buffer)) > 0)
	   {
	      z.write(buffer, 0, cnt);
	      
	    }
	    in.close();
	    z.close();
	    
	   
	   return;
	    
	  }

}
