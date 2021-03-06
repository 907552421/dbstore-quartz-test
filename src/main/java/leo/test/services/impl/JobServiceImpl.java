package leo.test.services.impl;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import leo.test.beans.JobConfigBean;
import leo.test.beans.JobLogBean;
import leo.test.constants.JobConst;
import leo.test.em.JobStatus;
import leo.test.jobs.DelegateJob;
import leo.test.jobs.JobBiz;
import leo.test.mapper.JobLogMapper;
import leo.test.mapper.JobMapper;
import leo.test.services.JobService;
import leo.test.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * ClassName: JobServiceImpl<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/8 <br/>
 *
 * @author kuoyang.liang
 */

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobMapper jobMapper;

    @Autowired
    JobLogMapper jobLogMapper;

    @Autowired
    Scheduler scheduler;

    @Autowired
    ApplicationContext context;

    @Value("${job.default.group}")
    public String defaultJobGroup;

    @Transactional
    @Override
    public int save(JobConfigBean jobConfigBean) throws ParseException, ClassNotFoundException {
        //插入之前进行各种验证
        ValidateUtils.isNullOrBlankString(jobConfigBean.getJobName(), "jobName");
        ValidateUtils.isNullOrBlankString(jobConfigBean.getCron(), "Cron");
        ValidateUtils.isNullOrBlankString(jobConfigBean.getJobVersion().getVersion(), "Version");

        this.checkCron(jobConfigBean.getCron());

        this.checkJobBizClass(jobConfigBean.getJobClass());

        if (StringUtils.isBlank(jobConfigBean.getJobGroup())) {
            jobConfigBean.setJobGroup(this.defaultJobGroup);
        }
        if (jobConfigBean.getStatus() == null) {
            jobConfigBean.setStatus(JobStatus.START.CODE);
        }
        if (jobConfigBean.getCreateTime() == null) {
            jobConfigBean.setCreateTime(new Date());
        }

        if (this.jobMapper.listJobs(jobConfigBean.getJobName(), jobConfigBean.getJobGroup())) {
            throw new RuntimeException("Job 已存在");
        }

        //经过各种验证后进行实际的插入
        return this.jobMapper.save(jobConfigBean);
    }

    @Override
    public int update(JobConfigBean jobConfigBean) throws ParseException, ClassNotFoundException {
        //更新前进行各种验证
        ValidateUtils.isNullOrBlankString(jobConfigBean.getJobName(), "jobName");
        ValidateUtils.isNullOrBlankString(jobConfigBean.getCron(), "Cron");
        ValidateUtils.isNullOrBlankString(jobConfigBean.getJobVersion().getVersion(), "Version");

        this.checkCron(jobConfigBean.getCron());

        this.checkJobBizClass(jobConfigBean.getJobClass());

        if(this.jobMapper.listJobs(jobConfigBean.getJobName(), jobConfigBean.getJobGroup())){
            StringBuilder builder = new StringBuilder("待更新的job不存在 [");
            builder.append("jobGroup:").append(jobConfigBean.getJobGroup()).append(" ").append("jobName:").append(jobConfigBean.getJobName()).append("]");

            throw new RuntimeException(builder.toString());
        }

        return this.jobMapper.update(jobConfigBean);
    }



    @Scheduled(cron = "0/30 * * * * ?")
    @Override
    public void reScanJobConfig() {
        /* **************************************************************************************************** */
        /*    说明：    1.扫描数据库中job配置
        /*              2.如果数据库中有的job而schedule没有，检验job状态，加入schedule
        /*              3.如果数据库中有的job schedule中也有，检验job状态，如果有更新加入schedule
        /* **************************************************************************************************** */

        List<JobConfigBean> allJobConfigBeanList = this.jobMapper.queryAll();
        if (allJobConfigBeanList == null || allJobConfigBeanList.size() == 0) {
            return;
        }
        for (JobConfigBean jobConfigBean : allJobConfigBeanList) {
            try {
                JobDetail jobDetail = this.scheduler.getJobDetail(JobKey.jobKey(jobConfigBean.getJobName(), jobConfigBean.getJobGroup()));
                if (jobDetail == null && jobConfigBean.getStatus() == JobStatus.START.CODE) {
                    //在加入schedule之前进行各种校验
                    ValidateUtils.isNullOrBlankString(jobConfigBean.getJobName(), "jobName");
                    ValidateUtils.isNullOrBlankString(jobConfigBean.getJobGroup(),"jobGroup");
                    ValidateUtils.isNullOrBlankString(jobConfigBean.getCron(), "Cron");
                    ValidateUtils.isNullOrBlankString(jobConfigBean.getJobVersion().getVersion(), "Version");

                    this.checkCron(jobConfigBean.getCron());
                    this.checkJobBizClass(jobConfigBean.getJobClass());

                    //经过校验加入schedule
                    this.scheduleJob(jobConfigBean);
                    continue;
                }else if (jobDetail != null) {
                    JobConfigBean schedulingJobConfigBean = (JobConfigBean) jobDetail.getJobDataMap().get(JobConst.JOB_BIZ_CONFIG_BEAN);
                    if (jobConfigBean.getStatus() == JobStatus.START.CODE) {
                        if (!schedulingJobConfigBean.equals(jobConfigBean)) {
                            this.reScheduleJob(jobConfigBean);
                        }
                    }
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    //* **************************************************************************************************** */
    /*    说明：  scheduleJob和reScheduleJob只在扫描数据库job配置时被调用   */
    /* **************************************************************************************************** */
    @Override
    public void scheduleJob(JobConfigBean jobConfigBean) throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobConfigBean.getJobName()+"-"+ jobConfigBean.getId(), jobConfigBean.getJobGroup());

        JobDetail jobDetail = buildJobDetail(jobConfigBean);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withDescription(jobConfigBean.getCron())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobConfigBean.getCron()))
                .build();

        this.scheduler.scheduleJob(jobDetail,trigger);

    }

    @Override
    public void reScheduleJob(JobConfigBean jobConfigBean) throws SchedulerException, ClassNotFoundException {
        JobKey jobKey = JobKey.jobKey(jobConfigBean.getJobName(), jobConfigBean.getJobGroup());
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if(jobDetail != null){
            this.scheduler.deleteJob(jobKey);
        }
        this.scheduleJob(jobConfigBean);
    }

    @Transactional
    @Override
    public int saveLog(JobLogBean jobLogBean) {
        return this.jobLogMapper.save(jobLogBean);
    }


    private JobDetail buildJobDetail(JobConfigBean jobConfigBean) throws ClassNotFoundException {
        String jobName = jobConfigBean.getJobName();
        String jobGroup = jobConfigBean.getJobGroup();
        JobKey jobKey = new JobKey(jobName,jobGroup);

        Class<JobBiz> JobBizClass = (Class<JobBiz>) Class.forName(jobConfigBean.getJobClass());
        JobBiz jobBizBean = context.getBean(JobBizClass);


        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConst.JOB_SERVICE, this);
        jobDataMap.put(JobConst.JOB_BIZ_CONFIG_BEAN,jobConfigBean);
        jobDataMap.put(JobConst.JOB_BIZ,jobBizBean);


        return JobBuilder.newJob()
                .withIdentity(jobKey)
                .ofType(DelegateJob.class)
                .withDescription(jobConfigBean.getDesc())
                .setJobData(jobDataMap)
                .build();
    }

    private void checkCron(String cronExpression) throws ParseException {
        new CronExpression(cronExpression);
    }

    private void checkJobBizClass(String jobBizClass) throws ClassNotFoundException {
        Class<QuartzJobBean> jobBizBean = (Class<QuartzJobBean>) Class.forName(jobBizClass);
        context.getBean(jobBizBean);
    }
}
