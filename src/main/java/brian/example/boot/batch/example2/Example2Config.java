package brian.example.boot.batch.example2;

import brian.example.boot.batch.example2.listener.Ex2Listener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import brian.example.boot.batch.example2.processor.Ex2Converter;
import brian.example.boot.batch.example2.model.Ex2;

/**
 * Example 2. Read from File (csv) -> transform -> Write to Database
 * 
 * @author allth
 *
 */
@Configuration
@EnableBatchProcessing
public class Example2Config {
	
	// Reader
	@Autowired
	@Qualifier("ex2Reader")
	private JdbcCursorItemReader<Ex2> reader;
	
	// Processor
	@Bean("ex2Processor")
	public Ex2Converter processor1() {
		return new Ex2Converter();
	}
	
	// Job
    @Bean("ex2Job")
    public Job executeEx2Job(
    		JobBuilderFactory jobBuilderFactory,
    		@Qualifier("ex2Listener") Ex2Listener listener,
    		@Qualifier("ex2Step1") Step ex2Step1
    		) {
        return jobBuilderFactory.get("ex2Job")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(ex2Step1)				// Within ex2Job thread, ex2Step1 will be executed
            .build();
    }
	
	// Step1
	@Bean("ex2Step1")
	public Step stepEx2(StepBuilderFactory step, 
						Ex2Converter ex2Processor,
						JdbcCursorItemReader<Ex2> reader,
						@Qualifier("ex2Writer") ItemWriter<Ex2> ex2Writer)
	{
		return step.get("ex2Step1")
				.<Ex2, Ex2>chunk(10)
				.reader(reader)
				.processor(ex2Processor)
				.writer(ex2Writer)
				.build();
	}
}
