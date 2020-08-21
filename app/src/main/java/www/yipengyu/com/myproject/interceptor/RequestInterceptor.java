package www.yipengyu.com.myproject.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.yipengyu.com.myproject.interfaces.INetworkRequestInfo;
import www.yipengyu.com.myproject.log.klog.KLog;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/22 0022
 * Time: 14:45
 */
public class RequestInterceptor implements Interceptor {
    private INetworkRequestInfo mINetworkRequestInfo;
    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.mINetworkRequestInfo = networkRequestInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder=chain.request()
                .newBuilder();
        if(mINetworkRequestInfo!=null){
            for (String key : mINetworkRequestInfo.getRequestHeaderMap().keySet()) {
                builder.addHeader(key, mINetworkRequestInfo.getRequestHeaderMap().get(key));
            }
            filterHeader(builder ,chain);
//            builder.addHeader("X-Shadow-Token", SPUtils.getDefaultInstance().getString(ISpKey.IUserKey.cosPlayToken));
//            builder.addHeader("X-User-ID", UserUtils.getUserId() + "");
        }
        return null;
    }
    private void filterHeader(Request.Builder builder, Chain chain) {
//        if (chain.request().url().toString().contains(INoTokenURL.IS_DEVICE_BINDING)) {
//            KLog.e("接口不需要添加 X-User-Token");
//        } else {
//            builder.addHeader("X-User-Token", SPUtils.getDefaultInstance().getString(ISpKey.IUserKey.userToken));
//        }
    }
}
