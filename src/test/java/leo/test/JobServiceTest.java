package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

import leo.test.beans.JobBean;
import leo.test.beans.JobVersion;
import leo.test.jobs.DemoJob;
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
        JobBean jobBean = new JobBean();
        jobBean.setJobGroup("d");
        jobBean.setJobName("d");
        jobBean.setName("a");
        jobBean.setCron("0/5 * * * * ?");
        jobBean.setJobClass(DemoJob.class.getName());
        jobBean.setJobVersion(new JobVersion("1.1"));
        jobBean.setStatus(1);
        int returnValue =  this.jobService.insert(jobBean);
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
