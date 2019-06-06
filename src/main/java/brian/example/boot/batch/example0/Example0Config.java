package brian.example.boot.batch.example0;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import brian.example.boot.batch.example0.converter.PersonItemProcessor;
import brian.example.boot.batch.example0.listener.JobCompletionNotificationListener;
import brian.example.boot.batch.example0.model.Person;

@Configuration
@EnableBatchProcessing
public class Example0Config {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // tag::readerwriterprocessor[]
    @Bean("step1Reader")
    public FlatFileItemReader<Person> reader() {
    	
    	BeanWrapperFieldSetMapper<Person> mapper = new BeanWrapperFieldSetMapper<>();
    	mapper.setTargetType(Person.class);
    	
        return new FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
            .delimited()
            .names(new String[]{"firstName", "lastName"})
            .fieldSetMapper(mapper)
            .build();
    }

    @Bean("step1Processor")
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean("step1Writer")
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean("step1Job")
    public Job importUserJob(
    		@Qualifier("step1Listener") JobCompletionNotificationListener listener, 
    		@Qualifier("step1") Step step1
//    		, @Qualifier("ex1Step") Step ex1Step    		
    		) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
//            .flow(step1)
            .start(step1)
//            .next(ex1Step)
//            .end()
            .build();
    }

    @Bean("step1")
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("step1")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
    // end::jobstep[]
}
