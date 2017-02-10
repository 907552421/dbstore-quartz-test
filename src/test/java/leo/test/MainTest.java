package leo.test;
/**
 * Created by kuoyang.liang on 2017/2/10.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName: MainTest<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/10 <br/>
 *
 * @author kuoyang.liang
 */

public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

}
