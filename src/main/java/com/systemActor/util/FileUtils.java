package com.systemActor.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static boolean createFolderIfNotExists(String dirName) {
		Path path = Paths.get(dirName);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				logger.error("Error while Creating Path wile not exist", e);
				return false;
			}
		}
		
		return true;
	}
	
	public static Long getNumberOfWordInFiel(String filePath){
		 long wordCount = 0;
		    Path textFilePath = Paths.get(filePath);
		    try {
		      Stream<String> fileLines = Files.lines(textFilePath, Charset.defaultCharset());
		      wordCount = fileLines.flatMap(line -> Arrays.stream(line.split(" "))).count();
		      fileLines.close();
		    } catch (IOException ioException) {
		      logger.error("Error in parsing file to get count", ioException);
		    }
		    return wordCount;
	}
	
	public static List<String>  listFileLines(String filePath){
		  Path textFilePath = Paths.get(filePath);
		  List<String> fileLines=new  ArrayList<String>();
		    try {
		    	 Stream<String> lineStreams = Files.lines(textFilePath, Charset.defaultCharset());
		    	 fileLines=lineStreams.collect(Collectors.toList());
		    	 lineStreams.close();
		    } catch (IOException ioException) {
		      logger.error("Error in parsing file to get count", ioException);
		    }
		    return fileLines;
	}
	
	public static long getLineWordCount(String line,String separator){
		 return Arrays.stream(line.split(separator)).count();
	}
	
	

}
