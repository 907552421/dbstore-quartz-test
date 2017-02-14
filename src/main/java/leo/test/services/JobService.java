package leo.test.services;

import leo.test.beans.JobConfigBean;
import leo.test.beans.JobLogBean;
import org.quartz.SchedulerException;

import java.text.ParseException;

/**
 * Created by kuoyang.liang on 2017/2/8.
 */
public interface JobService {

    int save(JobConfigBean jobConfigBean) throws ParseException, ClassNotFoundException;

    int update(JobConfigBean jobConfigBean) throws ParseException, ClassNotFoundException;

    int saveLog(JobLogBean jobLogBean);

    void reScanJobConfig();

    void scheduleJob(JobConfigBean jobConfigBean) throws SchedulerException, ClassNotFoundException;

    void reScheduleJob(JobConfigBean jobConfigBean) throws SchedulerException, ClassNotFoundException;
}
