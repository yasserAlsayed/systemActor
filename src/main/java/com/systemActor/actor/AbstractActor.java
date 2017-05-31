package com.systemActor.actor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractActor implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(AbstractActor.class);
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

	public void start() {
		thread = new Thread(this, this.getClass().getName());
		thread.start();
		setRunning(true);
	}

	public void stop() {
		thread.interrupt();
	}

	protected abstract void doAction(Object message);

	public void addMsg(Object message) throws InterruptedException{
		queue.put(message);
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
