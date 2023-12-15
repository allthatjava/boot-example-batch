package brian.example.boot.batch.example3.reader;

import brian.example.boot.batch.example3.model.Ex3PersonIn;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("ex3Reader")
public class Ex3Reader extends JdbcCursorItemReader<Ex3PersonIn> {
    @Autowired
    public Ex3Reader(@Qualifier("pgDatasource") DataSource dataSource){
        this.setDataSource(dataSource);
        this.setSql("SELECT first_name, last_name FROM person");
        this.setRowMapper(new BeanPropertyRowMapper<>(Ex3PersonIn.class));
    }
}
