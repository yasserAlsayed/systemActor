package com.systemActor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
@Test
public class ActorSystemSpec {
	
	private static Logger logger = LoggerFactory.getLogger(ActorSystemSpec.class); 
	private ActorSystem actorSystem; 
	private Properties props;
	@BeforeClass
	public void beforeTesting() throws Exception{
		logger.info("Testing start ....");
		actorSystem=new ActorSystem();
	}
	@Test
	public void GivenConfigFieWhenStartThenLoadIt() throws IOException{
		props=actorSystem.loadProperties();
		assertThat("There is no Configurations found !!",false, equalTo(props.isEmpty()));
	}
	
	@Test
	public void whenPathNotFoundThenCreateIt() throws IOException{
		//actorSystem.setDefaulPath(props.get(SystemConstants.defaulPathKey).toString());
		actorSystem.checkFolderExist(props.get(SystemConstants.defaulPathKey).toString());
		assertThat(props.get(SystemConstants.defaulPathKey).toString(), is(equalTo(actorSystem.getDefaulPath())));
	}
	@Test
	public void whenSystemUpThenActorsMustBeRunning(){
		actorSystem.startActors();
		assertThat("System Not Ready to parse", true, equalTo(actorSystem.isUpandRunning()));
	}
	
	@Test
	public void WhenAddMessageFileThenNotifyScanner()  {
		Boolean isCreated=actorSystem.pushMessage();
		assertThat("File Not Created",true, equalTo(isCreated));
	}
	
	@AfterClass
	public void aftrerTesting(){
		logger.info(".... Testing End ");
	}
	
	

}
