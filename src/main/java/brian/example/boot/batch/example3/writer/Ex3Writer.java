package brian.example.boot.batch.example3.writer;

import brian.example.boot.batch.example3.model.Ex3PersonOut;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("ex3Writer")
public class Ex3Writer extends JdbcBatchItemWriter<Ex3PersonOut> {

    @Autowired
    public Ex3Writer(@Qualifier("pgDatasource") DataSource dataSource){
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.setSql("Insert Into person_out (first_name, last_name, created_timestamp, job_name) values (:firstName, :lastName, :createTimestamp, :jobName)");
        this.setDataSource(dataSource);
    }
}
