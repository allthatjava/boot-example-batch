package brian.example.boot.batch.example3;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import brian.example.boot.batch.example3.model.Ex3;

/**
 * Example 3. Read from Database -> transform -> Write to file (csv)
 * 
 * @author allthatjava
 *
 */
@Configuration
@EnableBatchProcessing
public class Example3Config {
	
	@Autowired
	Environment env;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
	
	// Job
    @Bean("ex3Job")
    public Job executeEx3Job(
    		JobBuilderFactory jobBuilderFactory,
//    		@Qualifier("ex2Listener") Ex2Listener listener,
    		@Qualifier("ex3Step") Step ex3Step
    		) {
        return jobBuilderFactory.get("executeEx3Job")
            .incrementer(new RunIdIncrementer())
//            .listener(listener)
            .start(ex3Step)				// Within ex2Job thread, ex2Step1 will be executed
            .build();
    }
	
	// Step1
	@Bean("ex3Step")
	public Step stepEx3(StepBuilderFactory step,
						@Qualifier("ex3Proc") ItemProcessor<Ex3, Ex3> processor,
						@Qualifier("ex3Reader") ItemReader<Ex3> reader,
						@Qualifier("ex3Writer") ItemWriter<Ex3> writer)
	{
		return step.get("ex3Step")
				.<Ex3, Ex3>chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
}
