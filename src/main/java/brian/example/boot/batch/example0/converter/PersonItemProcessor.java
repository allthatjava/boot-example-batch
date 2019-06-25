package brian.example.boot.batch.example0.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import brian.example.boot.batch.example0.model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName+"@", lastName+"#");

        if( log.isInfoEnabled() ) {
        	log.info("Converting {} into {}", person, transformedPerson );
        }

        return transformedPerson;
    }

}
