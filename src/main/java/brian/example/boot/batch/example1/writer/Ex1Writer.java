package brian.example.boot.batch.example1.writer;

import brian.example.boot.batch.example1.model.Ex1;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

public class Ex1Writer extends FlatFileItemWriter<Ex1> {

    public Ex1Writer(String filePath) {

        // Field Extractor
        BeanWrapperFieldExtractor<Ex1> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"firstName", "lastName"});

        // Aggregator
        DelimitedLineAggregator<Ex1> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(",");
        aggregator.setFieldExtractor(extractor);

        setResource(new FileSystemResource(filePath));
        setLineAggregator(aggregator);
    }
}
