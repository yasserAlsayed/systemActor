package com.systemActor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systemActor.actor.Aggregator;
import com.systemActor.actor.FileParser;
import com.systemActor.actor.FileScanner;
import com.systemActor.util.FileUtils;

public class ActorSystem {
	 
	private String defaulPath;
	private static Logger logger = LoggerFactory.getLogger(ActorSystem.class); 
	private boolean isUpandRunning=false;
	private FileScanner fileScanner=new  FileScanner();
	private FileParser fileParser=new FileParser();
	private Aggregator msgAggregator =new Aggregator();
	/**
	 * Entry Point for my Application before start must define your default path in <code> config.properties </code>***/
	public static void main(String[] args) {
		try {
			logger.info("--> Start Application");
			ActorSystem actorSystem=new ActorSystem();
			actorSystem.startApp();
			actorSystem.pushMessage();
		} catch (Exception e) {
			logger.error("Error while Start application",e);
		}
	}
	
	
	public void startApp() throws IOException {
		loadProperties();
		startActors();
	}
	/**
	 * This method to load config.properties file and read configuration before start**/
	public Properties loadProperties() throws IOException {
		String resourceName = "config.properties"; // could also be a constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		/**
		 * Using try with resources handle close resource. safety without waste machine resources**/
		try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
		    props.load(resourceStream);
		}
		checkFolderExist(props.get(SystemConstants.defaulPathKey).toString());
		return props;
	}
	
	public void checkFolderExist(String filePath){
		if(FileUtils.createFolderIfNotExists(filePath))
		 this.setDefaulPath(filePath);
	}

	public String getDefaulPath() {
		return defaulPath;
	}

	public void setDefaulPath(String defaulPath) {
		this.defaulPath = defaulPath;
	}
	/**
	 * This method to monitor the states of the Actors running/stopped****/
	public void startActors() {
		fileScanner.start();fileParser.start();msgAggregator.start();
		
		if(fileScanner.isRunning() && fileParser.isRunning() && msgAggregator.isRunning())
			setUpandRunning(true);
	}
	
	/**
	 * This Mehod simulate the client which will push message to actors to parsing ,whatever
	 * It may be Streamer , Messaging system ,Socket ,.....
	 * Program will read and push to the actors sequential
	 * ***/
	public boolean pushMessage(){
		List<String> lines = Arrays.asList("1st line", "2nd line");
		Path path;
		try {
			path = Files.write(Paths.get(getDefaulPath()+"/file"+System.currentTimeMillis()+".txt"), lines, StandardCharsets.UTF_8,StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			fileScanner.addMsg(path.toFile());
			return true;
		} catch (Exception e) {
			logger.error("Erro while Push Message",e);
			return false;
		}
	}

	public boolean isUpandRunning() {
		return isUpandRunning;
	}

	public void setUpandRunning(boolean isUpandRunning) {
		this.isUpandRunning = isUpandRunning;
	}
	
}
