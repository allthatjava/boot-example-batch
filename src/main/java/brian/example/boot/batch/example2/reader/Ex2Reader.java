package brian.example.boot.batch.example2.reader;

import brian.example.boot.batch.example0.model.Person;
import brian.example.boot.batch.example2.model.Ex2;
import brian.example.boot.batch.example3.model.Ex3PersonIn;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("ex2Reader")
public class Ex2Reader extends JdbcCursorItemReader<Ex2> {
    @Autowired
    public Ex2Reader(@Qualifier("pgDatasource") DataSource dataSource){
        this.setDataSource(dataSource);
        this.setSql("SELECT first_name, last_name FROM person");
        this.setRowMapper(new BeanPropertyRowMapper<>(Ex2.class));
    }
}
