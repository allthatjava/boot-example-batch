package brian.example.boot.batch.example3.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import brian.example.boot.batch.example3.model.Ex3;

@Component("ex3Proc")
class Ex3Processor implements ItemProcessor<Ex3, Ex3>{ 

    private static final Logger log = LoggerFactory.getLogger(Ex3Writer.class);
    
	@Override
	public Ex3 process(Ex3 item) throws Exception {
		
		Ex3 newEx3 = new Ex3( item.getMovieId(), "["+item.getTitle()+"]", "["+item.getGenre()+"]", item.getYear()); 
		
		if( log.isInfoEnabled() ) {
        	log.info("Ex3 Converting {} into {}", item, newEx3 );
        }

		return newEx3;
	}
}