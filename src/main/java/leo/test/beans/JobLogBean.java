package leo.test.beans;
/**
 * Created by kuoyang.liang on 2017/2/13.
 */

import java.sql.Timestamp;

/**
 * ClassName: JobLogBean<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/13 <br/>
 *
 * @author kuoyang.liang
 */
public class JobLogBean {
    private Integer id;
    private String jobGroup;
    private String jobName;
    private String name;
    private Timestamp startTime;
    private Timestamp endTime;
    private Long takeMs;
    private Boolean success;
    private String version;
    private String jobClass;
    private String tags;
    private String remarks;

    public JobLogBean() {
    }

    public JobLogBean(Integer id, String jobGroup, String jobName, String name, Timestamp startTime, Timestamp endTime, Long takeMs, Boolean success, String version, String jobClass, String tags, String remarks) {
        this.id = id;
        this.jobGroup = jobGroup;
        this.jobName = jobName;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.takeMs = takeMs;
        this.success = success;
        this.version = version;
        this.jobClass = jobClass;
        this.tags = tags;
        this.remarks = remarks;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("JobLogBean{");
        sb.append("id=").append(id);
        sb.append(", jobGroup='").append(jobGroup).append('\'');
        sb.append(", jobName='").append(jobName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", jobClass='").append(jobClass).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", takeMs=").append(takeMs);
        sb.append(", version=").append(version);
        sb.append(", success=").append(success);
        sb.append(", remarks='").append(remarks).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Long getTakeMs() {
        return takeMs;
    }

    public void setTakeMs(Long takeMs) {
        this.takeMs = takeMs;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
