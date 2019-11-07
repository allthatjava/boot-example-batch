package brian.example.boot.batch.example2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import brian.example.boot.batch.example2.converter.Ex2Converter;
import brian.example.boot.batch.example2.listener.Ex2Listener;
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
	@Bean("ex2Reader")
	public FlatFileItemReader<Ex2> reader(){
		
		BeanWrapperFieldSetMapper<Ex2> mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(Ex2.class);
		
		return new FlatFileItemReaderBuilder<Ex2>()
				.name("ex2Reader")
				.resource(new ClassPathResource("ex2-data.csv"))
				.delimited()
				.names(new String[] {"firstName", "lastName"})
				.fieldSetMapper(mapper)
				.build();
	}
	
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
        return jobBuilderFactory.get("executeEx2Job")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(ex2Step1)				// Within ex2Job thread, ex2Step1 will be executed
            .build();
    }
	
	// Step1
	@Bean("ex2Step1")
	public Step stepEx2(StepBuilderFactory step, 
						Ex2Converter ex2Processor,
						FlatFileItemReader<Ex2> reader,
						ItemWriter<Ex2> ex2Writer) 
	{
		return step.get("ex2Bean")
				.<Ex2, Ex2>chunk(10)
				.reader(reader)
				.processor(ex2Processor)
				.writer(ex2Writer)
				.build();
	}
}
