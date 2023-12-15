package brian.example.boot.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BootExampleBatchApplication {
	
	@Autowired
	Environment env;


	public static void main(String[] args) throws Exception{
		SpringApplication.run(BootExampleBatchApplication.class, args);
	}

/* If this commented is off, it will run all registered Job will be executed asynchronously
	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();

		return jobLauncher;
	}
*/

	@Bean("pgDatasource")
	public DataSource pgDataSource() {
		
		return DataSourceBuilder.create()
				.url(env.getProperty("pg.datasource.url"))
				.driverClassName(env.getProperty("pg.datasource.driverClassName"))
				.username(env.getProperty("pg.datasource.username"))
				.password(env.getProperty("pg.datasource.password"))
				.build();
	}
}
