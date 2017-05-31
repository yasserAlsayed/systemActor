package com.systemActor.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systemActor.util.FileUtils;

public class Aggregator  implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(Aggregator.class); 
	
	public static Map<String, Long> fileWordsCountMap=new HashMap<String, Long>();
	private static final String SEPARATOR=" ";
	public static BlockingQueue<Object> queue = new ArrayBlockingQueue<Object>(1024);
	private boolean isRunning = false;
	public static Thread thread;
	
	@Override
	public void run() {
		while (true) {
			try {
				Object message = queue.take();
				if (message != null) {
					doAction(message);
					Thread.sleep(2);
				}
			} catch (Exception e) {
				logger.error("Error While Scan Msg", e);
			}
		}
	}
	/**
	 * We use MessageDTO as Object Carries data between actors
	 * when new message reach Queue it will parse and define message text, and its event
	 * 
	 * we use fileWordsCountMap as Map to contains all Files had parsed and their counts
	 * */
	protected void doAction(Object message) {
		MessageDTO msg=(MessageDTO)message;
		logger.info("Aggregator message Name -->:"+msg.getEvent());
		
		switch (msg.getEvent()) {
			case START:
				logger.info("Start Reading File -->:"+msg.getFileName());
				/*** When start init Map with key and initial value 0**/
				fileWordsCountMap.put(msg.getFileName(), 0l);
			break;
			
			case NEW_LINE:
				/*** When new Line Event it will add new Line words count to previous one and continue till reading all file lines**/
				long wordCount=FileUtils.getLineWordCount(msg.getMessage(), SEPARATOR)+ fileWordsCountMap.get(msg.getFileName());
				fileWordsCountMap.put(msg.getFileName(),wordCount);
			break;
				
			case END:
				logger.info("End Reading File -->:"+msg.getFileName());
				logger.info("File "+ msg.getFileName() +" contains {" +fileWordsCountMap.get(msg.getFileName())+"} words.");
				/** Print the count to console ***/
				System.out.println("File "+ msg.getFileName() +" contains {" +fileWordsCountMap.get(msg.getFileName())+"} words.");
			break;

		default:
			break;
		}
		
	}

	public void start() {
		thread = new Thread(this, this.getClass().getName());
		thread.start();
		setRunning(true);
	}

	public void stop() {
		thread.interrupt();
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
