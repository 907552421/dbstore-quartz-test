package leo.test.jobs.impl;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

import leo.test.beans.JobConfigBean;
import leo.test.jobs.JobBiz;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * ClassName: DemoJob<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/9 <br/>
 *
 * @author kuoyang.liang
 */

@Component
public class DemoJob implements JobBiz{

    final static Logger LOGGER = LoggerFactory.getLogger(QuartzJobBean.class);

    @Override
    public void doJob(JobConfigBean jobConfigBean) {
        LOGGER.info("demo job");
    }
}
