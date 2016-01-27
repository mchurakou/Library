package com.mikalai.library.actions;



import com.davidjc.ajaxfileupload.action.FileUpload;
import com.mikalai.library.ajax_json.AjaxResult;
import com.mikalai.library.dao.ElectronicBookDB;
;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.ZipFile;
import com.opensymphony.xwork2.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.io.*;
import java.util.Map;

public class FileAction extends FileUpload  implements SessionAware{
	private static final Logger LOG = LogManager.getLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AjaxResult result;

	private Map<String, Object> session;
	private int bookDescriptionId;
	private int id;
	
	private String fileName;
	private String message;
	
	

	


	private InputStream inputStream;
	




	public String execute()  {
		return Action.SUCCESS;	
		
	}
	
	
	/**
	 * Upload 
	 * @throws Exception
	 * @throws Exception 
	 * 
	 */
	public String upload() throws Exception {

		session.put(Constants.ATTRIBUTE_PERCENT, 0);
		
		File uploadedFile = this.getUpload();
		int total = (int) uploadedFile.length();
		//String contentType = this.getUploadContentType();
		String fileName = this.getUploadFileName();
		
				
		FileInputStream in = null;
		FileOutputStream out = null;
		
		String extention = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
		int fileId = ElectronicBookDB.getNewFileId();
		String archiveName = fileId + ".zip";
		/* add electronic book*/
		ElectronicBookDB.addElectronicBook(bookDescriptionId, archiveName,total,extention);
			
		
				
		String targetPath = Constants.PATH_FILES + "\\" + fileName;
		String zipPath = Constants.PATH_FILES + "\\" + archiveName;
		
		File docDestination = new File ( targetPath);
    	try {
    		in = new FileInputStream( uploadedFile );
    		out = new FileOutputStream( docDestination );
    		int c;
    		int shag = 0;
        try {
			while ((c = in.read()) != -1) {
			    out.write(c);
			    shag ++;
			    
			    if (shag == 10000){
			    	int current = (int)docDestination.length();
			    	int percent = 100 * current / total;
			    	session.put(Constants.ATTRIBUTE_PERCENT, percent);
			    	shag = 0;
			    }
			}
			
	    	session.put(Constants.ATTRIBUTE_PERCENT, 100);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
			
		}

    	}finally {
    		if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }
    	ZipFile.compress(targetPath, zipPath, fileName);
    	docDestination.delete();
    	
    	return Action.SUCCESS;
	}
	
	public String uploadProgress(){
		
		int percent =  Integer.parseInt(session.get(Constants.ATTRIBUTE_PERCENT).toString());
		result = new AjaxResult(percent);
		
		return Action.SUCCESS;
	}

	
	/**
	 * Download 
	 * @throws Exception
	 * @throws Exception 
	 * 
	 */
	public String download()  {
		
		fileName = id + ".zip";
		String path = Constants.PATH_FILES + "\\";
		try {
			inputStream = new FileInputStream(new File(path + fileName));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(),e);
			setMessage("File not founded!");
			return Action.ERROR;
		}
		return "sendFile"; 
	}
	
	public AjaxResult getResult() {
		return result;
	}


	public void setResult(AjaxResult result) {
		this.result = result;
	}


	
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	


	public int getBookDescriptionId() {
		return bookDescriptionId;
	}


	public void setBookDescriptionId(int bookDescriptionId) {
		this.bookDescriptionId = bookDescriptionId;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}





	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

}
