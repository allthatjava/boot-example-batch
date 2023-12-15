package brian.example.boot.batch.example3;

import brian.example.boot.batch.example3.listener.Ex3Listener;
import brian.example.boot.batch.example3.model.Ex3PersonIn;
import brian.example.boot.batch.example3.model.Ex3PersonOut;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableBatchProcessing
public class Example3Config {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("ex3Reader")
    public JdbcCursorItemReader<Ex3PersonIn> ex0Reader;

    @Autowired
    @Qualifier("ex3Processor")
    public ItemProcessor<Ex3PersonIn, Ex3PersonOut> ex0Processor;

    @Autowired
    @Qualifier("ex3Writer")
    public JdbcBatchItemWriter<Ex3PersonOut> writer;

    @Bean("ex3Job")
    public Job example3(
    		@Qualifier("ex3Listener") Ex3Listener listener,
    		@Qualifier("ex3Step1") Step step1
    		) {
        return jobBuilderFactory.get("ex3job")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
//            .flow(step1)
            .start(step1)
//            .end()
            .build();
    }

    @Bean("ex3Step1")
    public Step step1() {
        return stepBuilderFactory.get("ex3Step1")
            .<Ex3PersonIn, Ex3PersonOut> chunk(10)
            .reader(ex0Reader)
            .processor(ex0Processor)
            .writer(writer)
            .allowStartIfComplete(true)
            .build();
    }
}
