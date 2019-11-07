package brian.example.boot.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@SpringBootApplication
public class BootExampleBatchApplication {
	
	@Autowired
	Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(BootExampleBatchApplication.class, args);
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();

		return jobLauncher;
	}
	
	@Bean("pgDatasource")
	public DataSource pgDataSource() {
		
		return DataSourceBuilder.create()
				.url(env.getProperty("pg.datasource.url"))
				.driverClassName("org.postgresql.Driver")
				.username(env.getProperty("pg.datasource.username"))
				.password(env.getProperty("pg.datasource.password"))
				.build();
	}
}
