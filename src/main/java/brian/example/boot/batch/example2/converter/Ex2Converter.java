package brian.example.boot.batch.example2.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import brian.example.boot.batch.example2.model.Ex2;


public class Ex2Converter implements ItemProcessor<Ex2, Ex2>{
	

    private static final Logger log = LoggerFactory.getLogger(Ex2Converter.class);

	@Override
	public Ex2 process(Ex2 item) throws Exception {

		Ex2 newEx1 = new Ex2( "["+item.getFirstName(), item.getLastName()+"]"); 
		
		if( log.isInfoEnabled() ) {
        	log.info("Ex2 Converting {} into {}", item, newEx1 );
        }

		return newEx1;
	}
}
