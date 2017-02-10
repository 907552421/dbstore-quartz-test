package leo.test.exp;
/**
 * Created by kuoyang.liang on 2017/2/9.
 */

/**
 * ClassName: ParamException<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/9 <br/>
 *
 * @author kuoyang.liang
 */
public class ParamException extends RuntimeException {

    String param;
    String msg;

    public ParamException(String param, String msg) {
        this.param = param;
        this.msg = msg;
    }

    public String getParam() {
        return param;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getMessage() {
        return msg + " [" + param + "] ";
    }
}
