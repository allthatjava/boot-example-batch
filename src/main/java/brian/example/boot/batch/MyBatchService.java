package brian.example.boot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MyBatchService {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("ex0Job")
    private Job myJob0;

    @Autowired
    @Qualifier("ex1Job")
    private Job myJob1;

    @Autowired
    @Qualifier("ex2Job")
    private Job myJob2;

    @Autowired
    @Qualifier("ex3Job")
    private Job myJob3;

    public String runMyJob() throws Exception{
        JobExecution jobExe0 = jobLauncher.run(myJob0, new JobParameters());
        System.out.println("JobEx0:"+jobExe0.getStatus());
        JobExecution jobExe1 = jobLauncher.run(myJob1, new JobParameters());
        System.out.println("JobEx0:"+jobExe0.getStatus()+",JobEx1:"+jobExe1.getStatus());
        JobExecution jobExe2 = jobLauncher.run(myJob2, new JobParameters());
        System.out.println("JobEx0:"+jobExe0.getStatus()+",JobEx1:"+jobExe1.getStatus()+",JobEx2:"+jobExe2.getStatus());
        JobExecution jobExe3 = jobLauncher.run(myJob3, new JobParameters());
        System.out.println("JobEx0:"+jobExe0.getStatus()+",JobEx1:"+jobExe1.getStatus()+",JobEx2:"+jobExe2.getStatus()+",JobEx3:"+jobExe3.getStatus());

        return "MyJob started with status: JobEx0:"+jobExe0.getStatus()+",JobEx1:"+jobExe1.getStatus()+",JobEx2:"+jobExe2.getStatus()+",JobEx3:"+jobExe3.getStatus();
    }
}
