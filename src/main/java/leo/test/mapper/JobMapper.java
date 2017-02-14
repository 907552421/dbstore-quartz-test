package leo.test.mapper;
/**
 * Created by kuoyang.liang on 2017/2/8.
 */

import leo.test.beans.JobConfigBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: JobMapper<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/8 <br/>
 *
 * @author kuoyang.liang
 */
public interface JobMapper {

    int save(@Param("taskJobBean") JobConfigBean jobConfigBean);

    int update(@Param("jobConfigBean") JobConfigBean jobConfigBean);

    Boolean listJobs(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);

    List<JobConfigBean> queryAll();

}
