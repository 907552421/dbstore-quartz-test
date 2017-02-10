package leo.test.beans;
/**
 * Created by kuoyang.liang on 2017/2/10.
 */

import leo.test.utils.Regexs;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * ClassName: JobVersion<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2017/2/10 <br/>
 *
 * @author kuoyang.liang
 */
public class JobVersion implements Comparable<JobVersion> {
    private String version;

    @Override
    public int compareTo(JobVersion o) {

        int returnValue = 0;
        if(this.getVersion().equals(o.getVersion()))
            return returnValue;

        String[] segVersionO = o.getVersion().split(".");
        String[] segVersionThis = this.getVersion().split(".");
        int count = segVersionO.length <= segVersionThis.length ? segVersionO.length : segVersionThis.length;
        for(int i=0;i<count;i++){
            if(segVersionO.equals(segVersionThis))
                continue;
            returnValue = Integer.valueOf(segVersionThis[i]).compareTo(Integer.valueOf(segVersionO[i]));
        }
        if(returnValue == 0){
            returnValue = Integer.valueOf(segVersionThis.length).compareTo(Integer.valueOf(segVersionO.length));
        }
        return returnValue;
    }

    public JobVersion(){

    }

    public JobVersion(String version) {
        if(!Pattern.matches(Regexs.VERSIONREGEXSTRING,version))
            throw new RuntimeException("给定的版本字符串不符合标准");
        this.version = version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }



}
