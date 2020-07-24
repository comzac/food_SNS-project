package com.ssafy.sub.exception;

import java.io.IOException;

public class FileStorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileStorageException(String msg) {
		super(msg);
	}

	public FileStorageException(String msg, IOException ex) {
		super(msg, ex);
	}
	
}
