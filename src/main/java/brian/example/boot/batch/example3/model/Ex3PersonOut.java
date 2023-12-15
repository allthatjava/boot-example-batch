package brian.example.boot.batch.example3.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Ex3PersonOut {
    private String lastName;
    private String firstName;
    private Timestamp createTimestamp;
    private String jobName;

    public Ex3PersonOut() {}

    public Ex3PersonOut(String firstName, String lastName, Timestamp createTimestamp, String jobName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createTimestamp = createTimestamp;
        this.jobName = jobName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public String toString(){
        return "First Name:"+firstName+",Last name:"+lastName+",Creation Time:"+createTimestamp+",Job Name:"+jobName;
    }
}
