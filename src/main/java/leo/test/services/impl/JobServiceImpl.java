package leo.test.services.impl;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import leo.test.beans.JobBean;
import leo.test.constants.JobConst;
import leo.test.em.JobStatus;
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
    Scheduler scheduler;

    @Autowired
    ApplicationContext context;

    @Value("${job.default.group}")
    public String defaultJobGroup;

    @Transactional
    @Override
    public int insert(JobBean jobBean) throws ParseException, ClassNotFoundException {
        //插入之前进行各种验证
        ValidateUtils.isNullOrBlankString(jobBean.getJobName(), "jobName");
        ValidateUtils.isNullOrBlankString(jobBean.getCron(), "Cron");
        ValidateUtils.isNullOrBlankString(jobBean.getJobVersion().getVersion(), "Version");

        this.checkCron(jobBean.getCron());

        this.checkJobBizClass(jobBean.getJobClass());

        if (StringUtils.isBlank(jobBean.getJobGroup())) {
            jobBean.setJobGroup(this.defaultJobGroup);
        }
        if (jobBean.getStatus() == null) {
            jobBean.setStatus(JobStatus.START.CODE);
        }
        if (jobBean.getCreateTime() == null) {
            jobBean.setCreateTime(new Date());
        }

        if (this.jobMapper.existJob(jobBean.getJobName(), jobBean.getJobGroup())) {
            throw new RuntimeException("Job 已存在");
        }

        //经过各种验证后进行实际的插入
        return this.jobMapper.insert(jobBean);
    }

    @Override
    public int update(JobBean jobBean) throws ParseException, ClassNotFoundException {
        //更新前进行各种验证
        ValidateUtils.isNullOrBlankString(jobBean.getJobName(), "jobName");
        ValidateUtils.isNullOrBlankString(jobBean.getCron(), "Cron");
        ValidateUtils.isNullOrBlankString(jobBean.getJobVersion().getVersion(), "Version");

        this.checkCron(jobBean.getCron());

        this.checkJobBizClass(jobBean.getJobClass());

        if(this.jobMapper.existJob(jobBean.getJobName(),jobBean.getJobGroup())){
            StringBuilder builder = new StringBuilder("待更新的job不存在 [");
            builder.append("jobGroup:").append(jobBean.getJobGroup()).append(" ").append("jobName:").append(jobBean.getJobName()).append("]");

            throw new RuntimeException(builder.toString());
        }

        return this.jobMapper.update(jobBean);
    }

    @Scheduled(cron = "0 0/2 * * * ?")
    @Override
    public void reScanJobConfig() {
        /* **************************************************************************************************** */
        /*    说明：    1.扫描数据库中job配置
        /*              2.如果数据库中有的job而schedule没有，检验job状态，加入schedule
        /*              3.如果数据库中有的job schedule中也有，检验job状态，如果有更新加入schedule
        /* **************************************************************************************************** */

        List<JobBean> allJobBeanList = this.jobMapper.queryAll();
        if (allJobBeanList == null || allJobBeanList.size() == 0) {
            return;
        }
        for (JobBean jobBean : allJobBeanList) {
            try {
                JobDetail jobDetail = this.scheduler.getJobDetail(JobKey.jobKey(jobBean.getJobName(), jobBean.getJobGroup()));
                if (jobDetail == null && jobBean.getStatus() == JobStatus.START.CODE) {
                    //在加入schedule之前进行各种校验
                    ValidateUtils.isNullOrBlankString(jobBean.getJobName(), "jobName");
                    ValidateUtils.isNullOrBlankString(jobBean.getJobGroup(),"jobGroup");
                    ValidateUtils.isNullOrBlankString(jobBean.getCron(), "Cron");
                    ValidateUtils.isNullOrBlankString(jobBean.getJobVersion().getVersion(), "Version");

                    this.checkCron(jobBean.getCron());
                    this.checkJobBizClass(jobBean.getJobClass());

                    //经过校验加入schedule
                    this.scheduleJob(jobBean);
                    continue;
                }
                JobBean schedulingJobBean = (JobBean) jobDetail.getJobDataMap().get(JobConst.JOBBEAN);
                if (jobBean.getStatus() == JobStatus.START.CODE) {
                    if (!schedulingJobBean.equals(jobBean)) {
                        this.reScheduleJob(jobBean);
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
    public void scheduleJob(JobBean jobBean) throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobBean.getJobName()+"-"+jobBean.getId(),jobBean.getJobGroup());

        JobDetail jobDetail = buildJobDetail(jobBean);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withDescription(jobBean.getCron())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobBean.getCron()))
                .build();

        this.scheduler.scheduleJob(jobDetail,trigger);

    }

    @Override
    public void reScheduleJob(JobBean jobBean) throws SchedulerException, ClassNotFoundException {
        JobKey jobKey = JobKey.jobKey(jobBean.getJobName(), jobBean.getJobGroup());
        JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
        if(jobDetail != null){
            this.scheduler.deleteJob(jobKey);
        }
        this.scheduleJob(jobBean);
    }


    private JobDetail buildJobDetail(JobBean jobBean) throws ClassNotFoundException {
        String jobName = jobBean.getJobName();
        String jobGroup = jobBean.getJobGroup();
        JobKey jobKey = new JobKey(jobName,jobGroup);

        Class<QuartzJobBean> jobBeanClass = (Class<QuartzJobBean>) Class.forName(jobBean.getJobClass());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JobConst.JOBBEAN,jobBean);

        return JobBuilder.newJob()
                .withIdentity(jobKey)
                .ofType(jobBeanClass)
                .withDescription(jobBean.getDesc())
                .setJobData(jobDataMap)
                .build();
    }

    private void checkCron(String cronExpression) throws ParseException {
        new CronExpression(cronExpression);
    }

    private void checkJobBizClass(String jobBizClass) throws ClassNotFoundException {
        Class<QuartzJobBean> jobBizBean = (Class<QuartzJobBean>) Class.forName(jobBizClass);
        QuartzJobBean quartzJobBean = context.getBean(jobBizBean);
    }
}
