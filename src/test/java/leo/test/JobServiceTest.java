package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

import leo.test.beans.JobConfigBean;
import leo.test.beans.JobVersion;
import leo.test.jobs.impl.DemoJob;
import leo.test.services.JobService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

/**
 * ClassName: JobServiceTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/9 <br/>
 *
 * @author kuoyang.liang
 */
public class JobServiceTest extends BaseUnitTest{

    final static Logger LOGGER = LoggerFactory.getLogger(BaseUnitTest.class);

    @Autowired
    JobService jobService;

    @Test
    public void testInsert() throws ParseException, ClassNotFoundException {
        JobConfigBean jobConfigBean = new JobConfigBean();
        jobConfigBean.setJobGroup("d");
        jobConfigBean.setJobName("d");
        jobConfigBean.setName("a");
        jobConfigBean.setCron("0/5 * * * * ?");
        jobConfigBean.setJobClass(DemoJob.class.getName());
        jobConfigBean.setJobVersion(new JobVersion("1.1"));
        jobConfigBean.setStatus(1);
        int returnValue =  this.jobService.save(jobConfigBean);
        Assert.assertTrue(returnValue == 1);
        LOGGER.info("returnValue is {}",returnValue);
    }

    @Test
    public void testUpdate(){

    }

    @Test
    public void testReScan(){
        this.jobService.reScanJobConfig();
    }

}
