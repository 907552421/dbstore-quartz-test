package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/14.
 */

import leo.test.beans.JobLogBean;
import leo.test.mapper.JobLogMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * ClassName: JobLogMapperTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/14 <br/>
 *
 * @author kuoyang.liang
 */
public class JobLogMapperTest extends BaseUnitTest {

    @Autowired
    private JobLogMapper jobLogMapper;

    @Test
    public void testSave(){
        JobLogBean jobLogBean = new JobLogBean(
                null,
                "group",
                "jobName",
                "name",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                5L,
                false,
                "1.2.3",
                "leo.test.abc",
                "tags",
                "remarks"
        );

        int result = jobLogMapper.save(jobLogBean);
        Assert.assertEquals(result,1);
    }

}
