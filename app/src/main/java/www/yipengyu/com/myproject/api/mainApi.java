package www.yipengyu.com.myproject.api;

import android.content.Context;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import www.yipengyu.com.myproject.base.ApiBase;
import www.yipengyu.com.myproject.base.BaseResult;
import www.yipengyu.com.myproject.utils.ContextUtils;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/24 0024
 * Time: 15:19
 */
public class mainApi extends ApiBase {
    private static volatile mainApi mainApi;

    public mainApi() {
        super("https://gank.io/api/v2/", ContextUtils.getContext());
    }

    public static mainApi getInstance() {
        if (mainApi == null) {
            synchronized (mainApi.class) {
                if (mainApi == null) {
                    mainApi = new mainApi();
                }
            }
        }
        return mainApi;
    }


//    interface MainServers {
//        @GET("banners")
//        @Headers("Cache-Control:no-store")
//        Observable<BaseResult<>>
//    }
}
