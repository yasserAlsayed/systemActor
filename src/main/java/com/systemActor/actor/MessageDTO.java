package com.systemActor.actor;

import com.systemActor.actor.FileParser.ParserEvent;

public class MessageDTO {
	
	private ParserEvent event;
	private String message;
	private String fileName;
	
	public MessageDTO(ParserEvent event, String message,String fileName) {
		this.event = event;
		this.message = message;
		this.fileName=fileName;
	}
	
	public MessageDTO(ParserEvent event,String fileName) {
		this.event = event;
		this.fileName=fileName;
	}


	public ParserEvent getEvent() {
		return event;
	}
	public void setEvent(ParserEvent event) {
		this.event = event;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
