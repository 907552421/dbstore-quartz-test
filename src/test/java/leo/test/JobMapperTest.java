package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import leo.test.beans.JobBean;
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
        JobBean jobBean = new JobBean();
        jobBean.setJobGroup("g");
        jobBean.setJobName("g");
        jobBean.setName("a");
        jobBean.setCron("b");
        jobBean.setJobClass("b");
        jobBean.setJobVersion(new JobVersion("22.2"));
        jobBean.setStatus(1);

        int returnValue = this.jobMapper.insert(jobBean);
        Assert.assertEquals(returnValue,1);
}

    @Test
    public void testExistJob(){
        boolean returnValue = this.jobMapper.existJob("g","g");
        Assert.assertTrue(returnValue);
    }

    @Test
    public void testQueryAll(){
        List<JobBean> jobBeanList = this.jobMapper.queryAll();
        Assert.assertTrue(jobBeanList.size() > 0);

    }


}
