package com.systemActor.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.systemActor.util.FileUtils;
/**
 * All Actors will extends <code>AbstractActor</code>
 * it is Thread with BlockingQueue to save messages ordered
 * I used non Blocking Methods to avoid blocking .
 * with every message with notify its consumer.
 * **/
public class FileParser extends AbstractActor {
	private static Logger logger = LoggerFactory.getLogger(FileParser.class); 
	
	public enum ParserEvent{
		START,NEW_LINE,END;
	}
	
	
	/**
	 the core of this application it here it will create 3 event star,new line ,end Reading
	 and pass every event to Aggregator to make it buiness
	  **/
	@Override
	protected void doAction(Object message) {
		logger.info("File Scanner Fetch message Name -->:"+message.toString());
		try {
			/**Start to read event , notify  Aggregator*/
			Aggregator.queue.put(new MessageDTO(ParserEvent.START,message.toString()));
			FileUtils.listFileLines(message.toString()).forEach(line -> {
				try {
					/**new Line event , notify  Aggregator*/
					Aggregator.queue.put(new MessageDTO(ParserEvent.NEW_LINE, line,message.toString()));
				} catch (InterruptedException e) {
					logger.error("Error while Sending new line to aggrgator Actor",e);
				}
			});
			/**End reading file event , notify  Aggregator*/
			Aggregator.queue.put(new MessageDTO(ParserEvent.END,message.toString()));
		} catch (Exception e) {
			logger.error("Error while Sending start/End parser event to aggrgator Actor",e);
		}
	}
	

}
