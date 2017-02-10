package leo.test.utils;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

import leo.test.exp.ParamException;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: ValidateUtils<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/9 <br/>
 *
 * @author kuoyang.liang
 */
public class ValidateUtils {

    public static void isNullOrBlankString(String value,String paramName) {
        if (value == null || StringUtils.isBlank(value)) {
            throw new ParamException(paramName, "参数为null或者空");
        }
    }
}
