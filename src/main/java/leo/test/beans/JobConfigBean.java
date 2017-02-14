package leo.test.beans;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import org.apache.commons.lang3.NotImplementedException;

import java.util.Date;

/**
 * ClassName: JobConfigBean<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/8 <br/>
 *
 * @author kuoyang.liang
 */
public class JobConfigBean {
    private Integer id;
    private String jobGroup;
    private String jobName;
    private String name;
    private String cron;
    private String jobClass;
    private JobVersion jobVersion;

    @Override
    public boolean equals(Object obj) {
        JobConfigBean targetJobConfigBean =  (JobConfigBean)obj;
        throw new NotImplementedException("not impl");
    }

    private String tags;
    private String desc;
    private String jsonData;
    private Integer status;
    private Date createTime;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobConfigBean{");
        sb.append("id=").append(id);
        sb.append(", jobGroup='").append(jobGroup).append('\'');
        sb.append(", jobName='").append(jobName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", cron='").append(cron).append('\'');
        sb.append(", desp='").append(desc).append('\'');
        sb.append(", jobClass='").append(jobClass).append('\'');
        sb.append(", jsonData='").append(jsonData).append('\'');
        sb.append(", status=").append(status);
        sb.append(", jobVersion=").append(jobVersion);
        sb.append('}');
        return sb.toString();
    }

    public JobConfigBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public JobVersion getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(JobVersion jobVersion) {
        this.jobVersion = jobVersion;
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

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
