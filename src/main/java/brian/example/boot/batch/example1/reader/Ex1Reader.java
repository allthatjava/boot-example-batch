package brian.example.boot.batch.example1.reader;

import brian.example.boot.batch.example0.model.Person;
import brian.example.boot.batch.example1.model.Ex1;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component("ex1Reader")
public class Ex1Reader extends FlatFileItemReader<Ex1> {
    public Ex1Reader(){
        this.setResource(new ClassPathResource("ex1-data.csv"));
        this.setLinesToSkip(0); // Skip the header but, we don't have header in the file, so it is 0

        this.setLineMapper(new DefaultLineMapper<Ex1>(){{
            setLineTokenizer( new DelimitedLineTokenizer(){{
                setNames("first name", "last name");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Ex1>(){{
                setTargetType(Ex1.class);
            }});
        }});
    }
}
