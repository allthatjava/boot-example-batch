package brian.example.boot.batch.example3.listener;

import brian.example.boot.batch.example3.model.Ex3PersonOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("ex3Listener")
public class Ex3Listener extends JobExecutionListenerSupport {
	
	private static final Logger log = LoggerFactory.getLogger(Ex3Listener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public Ex3Listener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info( "===0 Job[{}] Finished ===>>> Time to verify the results", jobExecution.getJobInstance().getJobName() );

			// To confirm what has been added into database in log file.
			getAllPeople();
		}
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("<<<0 Job[{}] Started ======", jobExecution.getJobInstance().getJobName());

		// Clean up output table before execute
//		deleteAllPeople();
	}

	private void getAllPeople() {
		jdbcTemplate.query("SELECT first_name, last_name, created_timestamp, job_name FROM person_out ",
			(rs, row) -> new Ex3PersonOut(
				rs.getString(1),
				rs.getString(2),
				rs.getTimestamp(3),
					rs.getString(4))
		).forEach(person -> log.info("Found <{}> in the database.", person));
	}

	private void deleteAllPeople() {
		try {
			jdbcTemplate.execute("DELETE FROM person_out");
		} catch (DataAccessException e) {
			log.error("Error while trying to delete the table people");
		}
	}
}
