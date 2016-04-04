package com.mikalai.library.actions;


import com.mikalai.library.dao.ElectronicBookDAO;
import com.mikalai.library.utils.Constants;
import com.mikalai.library.utils.ZipFile;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileAction extends ActionSupport {
	private static final Logger LOG = LogManager.getLogger();

	@Inject
	private ElectronicBookDAO electronicBookDAO;


	private static final long serialVersionUID = 1L;

	private int bookDescriptionId;
	private int id;
	
	private String message;



	private File file;


	private String fileContentType;
	private String fileFileName;

	


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

		int total = (int) file.length();

		FileInputStream in = null;
		FileOutputStream out = null;
		
		String extention = fileFileName.substring(fileFileName.lastIndexOf('.') + 1, fileFileName.length());
		int fileId = electronicBookDAO.getNewFileId();
		String archiveName = fileId + ".zip";
		/* add electronic book*/
		electronicBookDAO.addElectronicBook(bookDescriptionId, archiveName,total,extention);
			
		
				
		String targetPath = Constants.PATH_FILES + "\\" + fileFileName;
		String zipPath = Constants.PATH_FILES + "\\" + archiveName;
		
		File docDestination = new File ( targetPath);
    	try {
    		in = new FileInputStream( file );
    		out = new FileOutputStream( docDestination );
    		int c;
        try {
			while ((c = in.read()) != -1) {
			    out.write(c);

			}
			
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
    	ZipFile.compress(targetPath, zipPath, fileFileName);
    	docDestination.delete();
    	
    	return Action.SUCCESS;
	}


	
	public String download()  {

		fileFileName = id + ".zip";
		String path = Constants.PATH_FILES + "\\";
		try {
			inputStream = new FileInputStream(new File(path + fileFileName));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(),e);
			setMessage("File not founded!");
			return Action.ERROR;
		}
		return "sendFile"; 
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


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

}
