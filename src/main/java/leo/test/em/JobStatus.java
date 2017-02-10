package leo.test.em;

/**
 * Created by kuoyang.liang on 2017/2/9.
 */
public enum JobStatus {

    NONE(0),
    START(1),
    STOP(2);

    public int CODE;


    JobStatus(int code) {
        CODE = code;
    }
}
