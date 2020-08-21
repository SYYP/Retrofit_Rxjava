package www.yipengyu.com.myproject.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import www.yipengyu.com.myproject.utils.ContextUtils;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/23 0023
 * Time: 14:36
 */
public class BaseApplication extends Application {
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        if (application == null) {
            if (inMainProcess(this)) {
                intApplication(this);
            }
        }
    }

    private void intApplication(Application baseApplication) {
        application = (BaseApplication) baseApplication;
        ContextUtils.init(baseApplication); // Context
    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    public static final String getProcessName(Context context) {
        String processName = null;
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

        }
    }
}
