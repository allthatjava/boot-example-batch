package brian.example.boot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchRunner implements CommandLineRunner {

    @Autowired
    private MyBatchService myBatchService;

    @Override
    public void run(String... args) throws Exception {
        // Call the method to manually trigger the job
        String result = myBatchService.runMyJob();
        System.out.println(result);
    }
}
