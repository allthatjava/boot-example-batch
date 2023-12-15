package brian.example.boot.batch.example0.processor;

import brian.example.boot.batch.example0.model.PersonOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import brian.example.boot.batch.example0.model.Person;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component("ex0Processor")
public class PersonItemProcessor implements ItemProcessor<Person, PersonOut> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    private JobExecution jobExecution;

    @BeforeStep
    private void beforeStep(StepExecution stepExecution){
        this.jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public PersonOut process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        PersonOut transformedPerson = new PersonOut();
        transformedPerson.setFirstName(firstName+"@");
        transformedPerson.setLastName(lastName+"#");
        transformedPerson.setCreateTimestamp(new Timestamp(System.currentTimeMillis()));
        transformedPerson.setJobName(jobExecution.getJobInstance().getJobName());

        if( log.isInfoEnabled() ) {
        	log.info("Converting {} into {}", person, transformedPerson );
        }

        return transformedPerson;
    }

}
