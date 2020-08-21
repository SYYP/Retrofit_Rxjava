package www.yipengyu.com.myproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * created by yuanye
 * on 2020/5/26
 * describe:上下文
 */
public final class ContextUtils {
    private static Context context;

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull final Context context) {
        ContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }
}
