package brian.example.boot.batch.example0.reader;

import brian.example.boot.batch.example0.model.Person;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component("ex0Reader")
public class Ex0Reader extends FlatFileItemReader<Person> {
    public Ex0Reader(){
        this.setResource(new ClassPathResource("ex0-data.csv"));
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
