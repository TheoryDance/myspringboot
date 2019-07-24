package com.theorydance.myspringboot.datahandler;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogMaster {
	
	@Value("${logsbyproduce.dir}")
	private String logsDir;
	@Value("${logsbyproduce.index}")
	private String index;
	@Value("${logsbyproduce.type}")
	private String type;
	@Value("${logsbyproduce.pool.size}")
	private int poolSize;

	public void uploadLogsToES() throws Exception{
		File dir = new File(logsDir);
		if(!dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
		for (File file : files) {
			executorService.submit(new LogHandler(file, index, type));
		}
		executorService.shutdown();
		
	}
}
