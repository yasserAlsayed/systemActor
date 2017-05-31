package com.systemActor.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * All Actors will extends <code>AbstractActor</code>
 * it is Thread with BlockingQueue to save messages ordered
 * I used non Blocking Methods to avoid blocking .
 * with every message with notify its consumer.
 * **/
public class FileScanner extends AbstractActor {
	private static Logger logger = LoggerFactory.getLogger(FileScanner.class); 
	
	/**
	 * When message push to FileScanner it will pass it FileParser to parse.
	 * **/
	@Override
	protected void doAction(Object message)  {
		logger.info("File Scanner Fetch message Name -->:"+message.toString());
		try {
			FileParser.queue.put(message);
		} catch (InterruptedException e) {
			logger.error("Error on add message to Parser");
		}
	}
	
	

}
