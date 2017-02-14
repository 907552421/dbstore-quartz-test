package leo.test.aspects;
/**
 * Created by kuoyang.liang on 2017/2/13.
 */

import leo.test.beans.JobConfigBean;
import leo.test.beans.JobLogBean;
import leo.test.mapper.JobLogMapper;
import leo.test.services.JobService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * ClassName: JobAspect<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/13 <br/>
 *
 * @author kuoyang.liang
 */

@Component
@Aspect
public class JobAspect {

    @Autowired
    private JobService jobService;

    @Pointcut("this(leo.test.jobs.JobBiz)")
    private void pointCutMethod() {
    }

    @Around("pointCutMethod()")
    public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        JobConfigBean jobConfigBean = (JobConfigBean) proceedingJoinPoint.getArgs()[0];
        JobLogBean jobLogBean = new JobLogBean(
            null,
                jobConfigBean.getJobGroup(),
                jobConfigBean.getJobName(),
                jobConfigBean.getName(),
                new Timestamp(System.currentTimeMillis()),
                null,
                null,
                false,
                jobConfigBean.getJobVersion().getVersion(),
                jobConfigBean.getJobClass(),
                null,
                null
        );

        Long t1 = System.nanoTime();
        try{
            Object object =  proceedingJoinPoint.proceed();
            jobLogBean.setSuccess(true);
            return object;
        } catch (Throwable e) {
            jobLogBean.setSuccess(false);
            String str = e.getMessage();
            jobLogBean.setRemarks(str.length() > 1023 ? str.substring(0,1023) : str);
            e.printStackTrace();
            throw e;
        } finally {
            jobLogBean.setTakeMs((System.nanoTime() - t1)/1000000);
            jobLogBean.setEndTime(new Timestamp(System.currentTimeMillis()));
            this.jobService.saveLog(jobLogBean);
        }

    }
}
