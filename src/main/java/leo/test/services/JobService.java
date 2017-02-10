package leo.test.services;

import leo.test.beans.JobBean;
import org.quartz.SchedulerException;

import java.text.ParseException;

/**
 * Created by kuoyang.liang on 2017/2/8.
 */
public interface JobService {

    int insert(JobBean jobBean) throws ParseException, ClassNotFoundException;

    int update(JobBean jobBean) throws ParseException, ClassNotFoundException;

    void reScanJobConfig();

    void scheduleJob(JobBean jobBean) throws SchedulerException, ClassNotFoundException;

    void reScheduleJob(JobBean jobBean) throws SchedulerException, ClassNotFoundException;

}
