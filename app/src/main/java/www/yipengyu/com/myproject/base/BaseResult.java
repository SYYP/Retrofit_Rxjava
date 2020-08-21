package www.yipengyu.com.myproject.base;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/22 0022
 * Time: 10:15
 */
public class BaseResult<T> implements Serializable {
     public Integer code;
     public String msg;
     public Integer total=0;
     public T Data;

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", total=" + total +
                ", Data=" + Data +
                '}';
    }
}
