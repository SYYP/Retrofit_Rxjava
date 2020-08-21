package www.yipengyu.com.myproject.base;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/21 0021
 * Time: 15:07
 */
public class BaseEvent<T> {

    private int code;
    private T Data;

    public BaseEvent(int code) {
        this.code = code;
    }

    public BaseEvent(int code, T data) {
        this.code = code;
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
