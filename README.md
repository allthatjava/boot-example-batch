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
Read the data from file, transform then write to the csv file. See the files under the package brian.example.boot.batch.exmaple1.

- Ex1Converter.java
- Ex1Listener.java
- Ex1.java
- Example1Config.java

__Source Data File__ : ex1-data.csv

```
Jill1,Doe1
Joe2,Doe2
Justin3,Doe3
Jane4,Doe4
John5,Doe5
```

__Target Data File__ : output/Ex1_Converted_1.csv, output/Ex1_Converted_2.csv


## Example 2. Read from File (csv) -> transform -> Write to Database
Read the data from file, transform then write to the Database. See the files under the package brian.example.boot.batch.example2. 
 
- Ex2Convert.java
- Ex2Listener.java
- Ex2.java
- Writer.java
- Example2Config.java

__Source Data File__ : ex2-data.csv

```
AAA,BBB
CCC,DDD
EEE,FFF
HHH,JJJ
KKK,LLL
```

__Table Name__ : people

```
CREATE TABLE people
(
    person_id integer NOT NULL DEFAULT nextval('people_person_id_seq'::regclass),
    first_name character varying(20) COLLATE pg_catalog."default",
    last_name character varying(20) COLLATE pg_catalog."default"
)
```



## Example 3. Read from Database -> transform -> Write to file (csv)
 
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
## Example 4. Read from Database -> transform -> Write to Database

