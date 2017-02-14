package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import leo.test.beans.JobConfigBean;
import leo.test.beans.JobVersion;
import leo.test.mapper.JobMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * ClassName: JobMapperTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/8 <br/>
 *
 * @author kuoyang.liang
 */
public class JobMapperTest extends  BaseUnitTest{

    @Autowired
    JobMapper jobMapper;

    @Test
    public void testInsert(){
        JobConfigBean jobConfigBean = new JobConfigBean();
        jobConfigBean.setJobGroup("g");
        jobConfigBean.setJobName("g");
        jobConfigBean.setName("a");
        jobConfigBean.setCron("b");
        jobConfigBean.setJobClass("b");
        jobConfigBean.setJobVersion(new JobVersion("22.2"));
        jobConfigBean.setStatus(1);

        int returnValue = this.jobMapper.save(jobConfigBean);
        Assert.assertEquals(returnValue,1);
}

    @Test
    public void testExistJob(){
        boolean returnValue = this.jobMapper.listJobs("g","g");
        Assert.assertTrue(returnValue);
    }

    @Test
    public void testQueryAll(){
        List<JobConfigBean> jobConfigBeanList = this.jobMapper.queryAll();
        Assert.assertTrue(jobConfigBeanList.size() > 0);

    }


}
