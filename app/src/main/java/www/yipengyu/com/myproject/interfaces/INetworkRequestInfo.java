package www.yipengyu.com.myproject.interfaces;

import java.util.HashMap;

/**
 * 设置请求头
 */
public interface INetworkRequestInfo {
    HashMap<String, String> getRequestHeaderMap();
    boolean isDebug();
}
