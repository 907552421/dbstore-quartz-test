package leo.test.mapper;

import leo.test.beans.JobLogBean;
import org.apache.ibatis.annotations.Param;

/**
 * Created by kuoyang.liang on 2017/2/14.
 */
public interface JobLogMapper{

    int save(@Param("jobLogBean")JobLogBean jobLogBean);
}
