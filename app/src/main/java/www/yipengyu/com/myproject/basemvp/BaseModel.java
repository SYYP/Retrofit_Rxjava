package www.yipengyu.com.myproject.basemvp;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;


/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/27 0027
 * Time: 14:23
 */
public class BaseModel {

    private Context mContext;
    private LifecycleProvider mLifecycleProvider;

    public BaseModel(Context context) {
        mContext = context;
    }

    public void injectLifecycle(LifecycleProvider lifecycleProvider) {
        mLifecycleProvider = lifecycleProvider;
    }

    public Context getContext() {
        return mContext;
    }

    public LifecycleProvider getLifecycleProvider() {
        return mLifecycleProvider;
    }

    public void destory() {

    }

}
