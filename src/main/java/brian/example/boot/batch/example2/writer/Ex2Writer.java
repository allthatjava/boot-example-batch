package brian.example.boot.batch.example2.writer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import brian.example.boot.batch.example1.model.Ex1;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import brian.example.boot.batch.example2.model.Ex2;

@Component("ex2Writer")
public class Ex2Writer extends FlatFileItemWriter<Ex2> {

	public Ex2Writer() {
		// Field Extractor
		BeanWrapperFieldExtractor<Ex2> extractor = new BeanWrapperFieldExtractor<>();
		extractor.setNames(new String[]{"firstName", "lastName"});

		// Aggregator
		DelimitedLineAggregator<Ex2> aggregator = new DelimitedLineAggregator<>();
		aggregator.setDelimiter(",");
		aggregator.setFieldExtractor(extractor);

		this.setResource(new FileSystemResource("output/Ex2_Converted.csv"));
		this.setLineAggregator(aggregator);
	}
}
