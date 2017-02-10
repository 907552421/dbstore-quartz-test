package leo.test.jobs;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

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
public class DemoJob extends QuartzJobBean{
    final static Logger LOGGER = LoggerFactory.getLogger(QuartzJobBean.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("demo job");
    }
}
