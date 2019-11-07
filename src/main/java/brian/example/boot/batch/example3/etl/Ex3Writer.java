package brian.example.boot.batch.example3.etl;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import brian.example.boot.batch.example3.model.Ex3;

@Component("ex3Writer")
public class Ex3Writer implements ItemWriter<Ex3>{

	@Override
	public void write(List<? extends Ex3> items) throws Exception {
		
		 FlatFileItemWriter<Ex3> flatFileItemWriter = new FlatFileItemWriter<>();
		 flatFileItemWriter.setShouldDeleteIfExists(true);
		 flatFileItemWriter.setResource(new FileSystemResource("output/Ex3-output.cvs"));
		 flatFileItemWriter.setLineAggregator(
				 (ex3) -> { return ex3.getTitle() + "," + ex3.getGenre() + "," + ex3.getYear(); });
		 flatFileItemWriter.doWrite(items);
	}
}