package www.yipengyu.com.myproject.base;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

import okhttp3.internal.platform.Platform;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.yipengyu.com.myproject.interceptor.CacheInterceptor;
import www.yipengyu.com.myproject.interceptor.Level;
import www.yipengyu.com.myproject.interceptor.LoggingInterceptor;
import www.yipengyu.com.myproject.interceptor.RequestInterceptor;
import www.yipengyu.com.myproject.interceptor.ResponseInterceptor;
import www.yipengyu.com.myproject.interfaces.INetworkRequestInfo;
import www.yipengyu.com.myproject.log.klog.KLog;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/22 0022
 * Time: 14:07
 */
public class ApiBase {
    //设置缓存大小
    private static final int CACHE_SIZE = 100 * 1024 * 1024;
    private Cache mCache = null;
    private Context context;
    protected static INetworkRequestInfo networkRequestInfo;
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor sHttpsResponseInterceptor;
    public final Retrofit retrofit;
    private File httpCacheDirectory;

    public ApiBase(String baseUrl, Context context) {
        this.context = context;
        if (networkRequestInfo == null) {
            new RuntimeException("ApiBase ..initNetworkRequestInfo 请先初始化");
        }
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    private OkHttpClient getOkHttpClient() {

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(context.getCacheDir(), "goldze_cache");
        }
        try {
            if (mCache == null) {
                mCache = new Cache(httpCacheDirectory, CACHE_SIZE);
            }
        } catch (Exception e) {
            KLog.e("Could not create http cache", e);
        }

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        okHttpClient.cache(mCache);
        okHttpClient.addInterceptor(new CacheInterceptor(context));
        okHttpClient.addInterceptor(sHttpsRequestInterceptor);
        okHttpClient.addInterceptor(sHttpsResponseInterceptor);
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        /*出现错误尝试请求20次*/
        httpClient.dispatcher().setMaxRequestsPerHost(2);
        return httpClient;
    }

    /**
     * 设置LOG
     *
     * @param builder
     */
    private void setLoggingLevel(OkHttpClient.Builder builder) {
        LoggingInterceptor logging = new LoggingInterceptor
                .Builder()//构建者模式
                .loggable(networkRequestInfo.isDebug()) //是否开启日志打印
                .setLevel(Level.BASIC) //打印的等级
                .log(Platform.INFO) // 打印类型
                .request("Request") // request的Tag
                .response("Response")// Response的Tag
                .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                .build();
        builder.addInterceptor(logging);
    }

    /**
     * 在appliaction oncreat 中先设置使用
     *
     * @param requestInfo
     */
    public static void initNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
        sHttpsResponseInterceptor = new ResponseInterceptor();
    }

    /**
     * 封装线程管理和订阅的过程
     */
    public Observable ApiSubscribe(Observable observable, Observer observer, LifecycleProvider lifecycl) {
        if (lifecycl == null) {
            observable.compose(RxAdapter.schedulersTransformer())
                    .compose(RxAdapter.getsErrorTransformer())
                    .subscribe(observer);
        } else {
            observable.compose(RxAdapter.bindUntilEvent(lifecycl))
                    .compose(RxAdapter.schedulersTransformer())
                    .compose(RxAdapter.getsErrorTransformer())
                    .subscribe(observer);
        }
        return observable;
    }

    public void printApiNameLog(String apiName, String args) {
        KLog.i(apiName);
        KLog.i(args);
    }
}
