package www.yipengyu.com.myproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.yipengyu.com.myproject.base.BaseEvent;
import www.yipengyu.com.myproject.interfaces.IbaseInit;
import www.yipengyu.com.myproject.interfaces.IbaseLoadView;
import www.yipengyu.com.myproject.loadwait.Callback;
import www.yipengyu.com.myproject.loadwait.EmptyCallback;
import www.yipengyu.com.myproject.loadwait.LoadService;
import www.yipengyu.com.myproject.loadwait.LoadSir;
import www.yipengyu.com.myproject.loadwait.LoadingCallback;
import www.yipengyu.com.myproject.loadwait.NetCallback;
import www.yipengyu.com.myproject.loadwait.SuccessCallback;
import www.yipengyu.com.myproject.utils.AppManager;

public abstract class BaseActivity extends RxAppCompatActivity implements IbaseInit, IbaseLoadView {
    protected View mRootView;
    private Unbinder mUnbind;
    public LoadService mAppLoadService;
    private LoadSir mViewLoadSir;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = bindView();
        setContentView(mRootView);
        mUnbind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        AppManager.getAppmanager().addActivity(this);
        initLoadsirServer();
        initView();
        // initBar();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AppManager.getAppmanager().removeActivity(this);
        mUnbind.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void OnEvent(BaseEvent<T> event) {
        if (event == null) {
            return;
        }

    }

    @Override
    public void onShowLoading() {

    }

    public abstract void onAppReloadCall(View v);

    public void initLoadsirServer() {
        mAppLoadService = LoadSir.getDefault().register(this, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onAppReloadCall(v);
            }
        });

        mViewLoadSir = new LoadSir.Builder()
                .addCallback(new NetCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(SuccessCallback.class)
                .build();

    }

    /**
     * 是否支持 退出当前页面
     *
     * @return
     */
    public boolean enableBack() {
        return false;
    }

    public View inflate(int resId) {
        return LayoutInflater.from(this).inflate(resId, null);
    }

    public void startIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startIntentWithExtras(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void startIntentWithSpecificFlag(Class<?> cls, int flag) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (enableBack()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
