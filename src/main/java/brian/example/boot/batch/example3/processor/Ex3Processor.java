package brian.example.boot.batch.example3.processor;

import brian.example.boot.batch.example3.model.Ex3PersonIn;
import brian.example.boot.batch.example3.model.Ex3PersonOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component("ex3Processor")
public class Ex3Processor implements ItemProcessor<Ex3PersonIn, Ex3PersonOut> {

    private static final Logger log = LoggerFactory.getLogger(Ex3Processor.class);


    private JobExecution jobExecution;

    @BeforeStep
    private void beforeStep(StepExecution stepExecution){
        this.jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public Ex3PersonOut process(final Ex3PersonIn person) throws Exception {

        Ex3PersonOut transformedPerson = new Ex3PersonOut();
        transformedPerson.setFirstName("<<"+person.getFirstName());
        transformedPerson.setLastName(person.getLastName()+">>");
        transformedPerson.setCreateTimestamp(new Timestamp(System.currentTimeMillis()));
        transformedPerson.setJobName(jobExecution.getJobInstance().getJobName());

        if( log.isInfoEnabled() ) {
        	log.info("Converting {} into {}", person, transformedPerson );
        }

        return transformedPerson;
    }

}
