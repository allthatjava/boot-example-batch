package brian.example.boot.batch.example2.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component("ex2Listener")
public class Ex2Listener extends JobExecutionListenerSupport{
	
	private static final Logger log = LoggerFactory.getLogger(Ex2Listener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("<<< Job[{}] Started ======", jobExecution.getJobInstance().getJobName());

		// Initialize the database table
//		deleteFile(Example1Config.fileWritePath1);
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("=== Job[{}] Finished ===>>>", jobExecution.getJobInstance().getJobName());

//		deleteFile(Example1Config.fileWritePath2);
	}
	
	private void deleteFile(final String filePath) {
		
	}
}
