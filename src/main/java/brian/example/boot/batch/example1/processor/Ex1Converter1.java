package brian.example.boot.batch.example1.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import brian.example.boot.batch.example1.model.Ex1;


public class Ex1Converter1 implements ItemProcessor<Ex1, Ex1>{
	

    private static final Logger log = LoggerFactory.getLogger(Ex1Converter1.class);

	@Override
	public Ex1 process(Ex1 item) throws Exception {

		Ex1 newEx1 = new Ex1( "["+item.getFirstName(), item.getLastName()+"]"); 
		
		if( log.isInfoEnabled() ) {
        	log.info("Ex-1-1 Converting {} into {}", item, newEx1 );
        }

		return newEx1;
	}
}
