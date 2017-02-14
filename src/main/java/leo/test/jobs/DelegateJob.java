package leo.test.jobs;
/**
 * Created by kuoyang.liang on 2017/2/13.
 */

import leo.test.beans.JobConfigBean;
import leo.test.constants.JobConst;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: DelegateJob<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/13 <br/>
 *
 * @author kuoyang.liang
 */
public class DelegateJob extends QuartzJobBean{

    @Transactional
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobBiz jobBiz = (JobBiz) context.getJobDetail().getJobDataMap().get(JobConst.JOB_BIZ);
        JobConfigBean jobConfigBean = (JobConfigBean) context.getJobDetail().getJobDataMap().get(JobConst.JOB_BIZ_CONFIG_BEAN);

        jobBiz.doJob(jobConfigBean);
    }
}
