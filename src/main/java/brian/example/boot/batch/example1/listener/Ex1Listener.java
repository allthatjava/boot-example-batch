package brian.example.boot.batch.example1.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import brian.example.boot.batch.example1.Example1Config;

@Component("ex1Listener")
public class Ex1Listener extends JobExecutionListenerSupport{
	
	private static final Logger log = LoggerFactory.getLogger(Ex1Listener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("<<< Job[{}] Started ======", jobExecution.getJobInstance().getJobName());

		deleteFile(Example1Config.fileWritePath1);
		deleteFile(Example1Config.fileWritePath2);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("=== Job[{}] Finished ===>>>", jobExecution.getJobInstance().getJobName());
	}
	
	private void deleteFile(final String filePath) {
		// Delete the file if exists
		Path filePath1 = Paths.get(filePath);
		
		try {
			Files.delete(filePath1);
		} catch (IOException e) {
			log.error("Error while trying to delete the file: {}", filePath);
		}
	}
}
