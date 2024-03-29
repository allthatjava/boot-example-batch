package brian.example.boot.batch.example1;

import brian.example.boot.batch.example1.listener.Ex1Listener;
import brian.example.boot.batch.example1.writer.Ex1Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import brian.example.boot.batch.example1.processor.Ex1Converter1;
import brian.example.boot.batch.example1.processor.Ex1Converter2;
import brian.example.boot.batch.example1.model.Ex1;

/**
 * Example 1. Read from File (csv) -> transform -> Write to file (csv)
 * 
 * @author allth
 *
 */
@Configuration
@EnableBatchProcessing
public class Example1Config {
	
	public static final String fileWritePath1 = "output/Ex1_Converted_1.csv"; 
	public static final String fileWritePath2 = "output/Ex1_Converted_2.csv"; 

//	// Reader
//	@Bean("ex1Reader")
//	public FlatFileItemReader<Ex1> reader(){
//
//		BeanWrapperFieldSetMapper<Ex1> mapper = new BeanWrapperFieldSetMapper<>();
//		mapper.setTargetType(Ex1.class);
//
//		return new FlatFileItemReaderBuilder<Ex1>()
//				.name("ex1Reader")
//				.resource(new ClassPathResource("ex1-data.csv"))
//				.delimited()
//				.names(new String[] {"firstName", "lastName"})
//				.fieldSetMapper(mapper)
//				.build();
//	}

	@Autowired
	@Qualifier("ex1Reader")
	private FlatFileItemReader<Ex1> ex1Reader;

	@Bean("ex1WriterStep1")
	public Ex1Writer ex1WriterStep1(){
		return new Ex1Writer(fileWritePath1);
	}

	@Bean("ex1WriterStep2")
	public Ex1Writer ex1WriterStep2(){
		return new Ex1Writer(fileWritePath2);
	}
	
	// Processor
//	@Bean
	public Ex1Converter1 processor1() {
		return new Ex1Converter1();
	}
//	@Bean
	public Ex1Converter2 processor2() {
		return new Ex1Converter2();
	}
	

	// Job
    @Bean("ex1Job")
    public Job executeEx1Job(
    		JobBuilderFactory jobBuilderFactory,
    		@Qualifier("ex1Listener") Ex1Listener listener,
    		@Qualifier("ex1Step1") Step ex1Step1,
    		@Qualifier("ex1Step2") Step ex1Step2
    		) {
        return jobBuilderFactory.get("ex1Job")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(ex1Step1)				// Within ex1Job thread, ex1Step1 and ex1Step2 will be executed sequentially
            .next(ex1Step2)
            .build();
    }
	
	// Step1
	@Bean("ex1Step1")
	public Step stepEx1_1(StepBuilderFactory step, @Qualifier("ex1WriterStep1") Ex1Writer ex1Writer)
	{
		return step.get("ex1Step1")
				.<Ex1, Ex1>chunk(10)
				.reader(ex1Reader)
				.processor(processor1())
				.writer(ex1Writer)
				.allowStartIfComplete(true)
				.build();
	}
	
	// Step2
	@Bean("ex1Step2")
	public Step stepEx1_2(StepBuilderFactory step, @Qualifier("ex1WriterStep2") Ex1Writer ex1Writer)
	{
		return step.get("ex1Step2")
				.<Ex1, Ex1>chunk(10)
				.reader(ex1Reader)
				.processor(processor2())
				.writer(ex1Writer)
				.allowStartIfComplete(true)
				.build();
	}
}
