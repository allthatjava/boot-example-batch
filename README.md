# Spring Boot Batch Examples
A few examples for how to use Spring Boot Batch


```
#To make them run in parallel I've set the following properties
spring.main.allow-bean-definition-overriding=true

# To triggered batch job start manually, sets as false. Otherwise, jobs will run automatically
spring.batch.job.enabled=false
```

### To run job in parallel
Once you set up the above properties("spring.main.allow-bean-definition-overriding") to true, the following setup will override the default JobLauncher that runs sequentially to simultaneously.

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

### To run the job sequentially,
```java
@Component
public class BatchRunner implements CommandLineRunner {

    @Autowired
    private MyBatchService myBatchService;

    @Override
    public void run(String... args) throws Exception {
        // Call the method to manually trigger the job
        String result = myBatchService.runMyJob();
        System.out.println(result);
    }
}
```
```java
@Service
public class MyBatchService {
    @Autowired
    private JobLauncher jobLauncher;
    //.. declare Job beans
    public String runMyJob() throws Exception {
        JobExecution jobExe0 = jobLauncher.run(myJob0, new JobParameters());
        System.out.println("JobEx0:" + jobExe0.getStatus());
        JobExecution jobExe1 = jobLauncher.run(myJob1, new JobParameters());
        System.out.println("JobEx0:" + jobExe0.getStatus() + ",JobEx1:" + jobExe1.getStatus());
        JobExecution jobExe2 = jobLauncher.run(myJob2, new JobParameters());
        System.out.println("JobEx0:" + jobExe0.getStatus() + ",JobEx1:" + jobExe1.getStatus() + ",JobEx2:" + jobExe2.getStatus());
        JobExecution jobExe3 = jobLauncher.run(myJob3, new JobParameters());
        System.out.println("JobEx0:" + jobExe0.getStatus() + ",JobEx1:" + jobExe1.getStatus() + ",JobEx2:" + jobExe2.getStatus() + ",JobEx3:" + jobExe3.getStatus());

        return "MyJob started with status: JobEx0:" + jobExe0.getStatus() + ",JobEx1:" + jobExe1.getStatus() + ",JobEx2:" + jobExe2.getStatus() + ",JobEx3:" + jobExe3.getStatus();
    }
}
```

## Example Data ##
Data in the database
```sql 
CREATE TABLE public.person (
	first_name varchar NULL,
	last_name varchar NULL
);

insert into person (first_name, last_name) 
values( 'Jill1','Doe1'),
( 'Joe2', 'Doe2'),
( 'Justin3', 'Doe3'),
( 'Jane4', 'Doe4'),
( 'John5', 'Doe5');

CREATE TABLE public.person_out (
	first_name varchar NULL,
	last_name varchar NULL,
	created_timestamp timestamp NULL,
	job_name varchar NULL
);
```


## Example 0. Read __from File__ (csv) -> transform -> Write to PostgreSQL Database
Main part is read from flatfile and write to database

__READ File__ 
```java
@Component("ex0Reader")
public class Ex0Reader extends FlatFileItemReader<Person> {
    public Ex0Reader(){
        this.setResource(new ClassPathResource("ex1-data.csv"));
        this.setLinesToSkip(0); // Skip the header but, we don't have header in the file, so it is 0

        this.setLineMapper(new DefaultLineMapper<Person>(){{
                setLineTokenizer( new DelimitedLineTokenizer(){{
                    setNames("first name", "last name");
                }});
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                    setTargetType(Person.class);
                }});
            }});
    }
}
```
__Write to DB__ 
```java
// Writer
@Component("ex0Writer")
public class Ex0Writer extends JdbcBatchItemWriter<PersonOut> {

    @Autowired
    public Ex0Writer(@Qualifier("pgDatasource") DataSource dataSource){
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setSql("Insert Into person_out (first_name, last_name, created_timestamp, job_name) values (:firstName, :lastName, :createTimestamp, :jobName)");
        this.setDataSource(dataSource);
    }
}
```

## Example 1. Read __from File__ (csv) -> transform -> Write to file (csv)
Read the data from file, transform then write to the csv file. See the files under the package brian.example.boot.batch.exmaple1.

- Ex1Listener.java
- Ex1.java
- Ex1Converter1.java
- Ex1Converter2.java 
- Ex1Reader.java
- Ex1Writer.java
- Example1Config.java

__Source Data File__ : ex1-data.csv
__Output Data File__ : output/Ex1_Converted_1.csv, output/Ex1_Converted_2.csv

```
Jill1,Doe1
Joe2,Doe2
Justin3,Doe3
Jane4,Doe4
John5,Doe5
```

__Target Data File__ : output/Ex1_Converted_1.csv, output/Ex1_Converted_2.csv


## Example 2. Read from Database -> transform -> Write to File
Read the data from Database, transform then write to file  
 
- Ex2Convert.java
- Ex2Listener.java
- Ex2.java
- Writer.java
- Example2Config.java


## Example 3. Read from Database -> transform -> Write to Database
 
- Ex3Processtor.java
- JobCompletionNotificationListener.java
- Ex3PersonIn.java
- Ex3PersonOut.java
- Ex3Reader.java
- Ex3Writer.java
- Example3Config.java
- 
```
CREATE TABLE  movie
(
    movie_id SERIAL PRIMARY KEY,
    title character varying(20) COLLATE pg_catalog."default",
    genre character varying(20) COLLATE pg_catalog."default",
    year integer
)
```
 
```
insert into movie (title, genre, year) values('ABC','Action',2011);
insert into movie (title, genre, year) values('DEF','Drama',2012);
insert into movie (title, genre, year) values('HIJ','Sci-Fi',2013);
insert into movie (title, genre, year) values('KLM','Thriller',2014);
insert into movie (title, genre, year) values('OPQ','Comedy',2015);
insert into movie (title, genre, year) values('RST','Reality',2016);
```

