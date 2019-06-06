# Spring Boot Batch Examples
A few examples for how to use Spring Boot Batch


To make them run in parallel I've set the following properties

```
spring.main.allow-bean-definition-overriding=true
```

Once you set up the above properties to true, the following setup will override the default JobLauncher that runs sequentially to simultaneously.

```
	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		jobLauncher.afterPropertiesSet();

		return jobLauncher;
	}
```

## Example 0. Read __from File__ (csv) -> transform -> Write to H2 in-memory Database


## Example 1. Read __from File__ (csv) -> transform -> Write to file (csv)
Read the data from file, transform then write to the csv file
* ex1-data.csv
* Ex1Converter.java
* Ex1Listener.java
* Ex1.java
* Example1Config.java


## Example 2. Read from File (csv) -> transform -> Write to Database






## Example 3. Read from Database -> transform -> Write to file (csv)
 
 
## Example 4. Read from Database -> transform -> Write to Database

