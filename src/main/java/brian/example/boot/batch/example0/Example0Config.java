package brian.example.boot.batch.example0;

import brian.example.boot.batch.example0.model.PersonOut;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brian.example.boot.batch.example0.listener.JobCompletionNotificationListener;
import brian.example.boot.batch.example0.model.Person;

@Configuration
//@EnableBatchProcessing
public class Example0Config {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("ex0Reader")
    public FlatFileItemReader<Person> ex0Reader;

    @Autowired
    @Qualifier("ex0Processor")
    public ItemProcessor<Person, PersonOut> ex0Processor;

    @Autowired
    @Qualifier("ex0Writer")
    public JdbcBatchItemWriter<PersonOut> writer;

    // tag::jobstep[]
    @Bean("ex0Job")
    public Job importUserJob(
    		@Qualifier("step1Listener") JobCompletionNotificationListener listener, 
    		@Qualifier("step1") Step step1
//    		, @Qualifier("ex1Step") Step ex1Step    		
    		) {
        return jobBuilderFactory.get("ex0job")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
//            .flow(step1)
            .start(step1)
//            .next(ex1Step)
//            .end()
            .build();
    }

    @Bean("step1")
    public Step step1() {
        return stepBuilderFactory.get("step1")
            .<Person, PersonOut> chunk(10)
            .reader(ex0Reader)
            .processor(ex0Processor)
            .writer(writer)
                .allowStartIfComplete(true)
            .build();
    }
    // end::jobstep[]
}
