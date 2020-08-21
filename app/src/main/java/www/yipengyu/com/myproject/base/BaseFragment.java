package www.yipengyu.com.myproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.RxFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
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

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/21 0021
 * Time: 17:11
 */
public abstract class BaseFragment extends Fragment implements IbaseLoadView, IbaseInit {
    protected RxAppCompatActivity mActivity;
    public View mView;
    private Unbinder mUnbind;
    public LoadService mViewloadService;
    private boolean isViewCreated = false;
    private boolean isViewVisable = false;
    public LoadService mAppLoadService;
    private LoadSir mViewLoadSir;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RxAppCompatActivity) getActivity();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        AppManager.getAppmanager().addFragment(this);
        mView = bindView();
        mUnbind = ButterKnife.bind(this, mView);
        initLoadsirServer();
        initView();
        return mViewloadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        //如果启用了懒加载就进行懒加载，否则就进行预加载
        if (enableLazyData() && isViewVisable) {
            lazyLoad();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.v("MYTAG", "hide ...");
            loadData();
        }
    }

    private void initLoadsirServer() {
        mAppLoadService = LoadSir.getDefault().register(mView, new Callback.OnReloadListener() {
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

    public abstract void onAppReloadCall(View v);

    //默认不启用 在acitity回调OnRestart() 不刷新当前数据
    public boolean enableActivityOnRestartData() {
        return false;
    }

    public void loadActivityStartData() {
        if (enableActivityOnRestartData()) {
            Log.v("MYTAG", "loadActivityStartData  加载数据loadData");
            loadData();
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,必须确保onCreateView加载完毕且页面可见,才加载数据
        Log.v("MYTAG", "lazyLoad start...");
        Log.v("MYTAG", "isViewCreated:" + isViewCreated);
        Log.v("MYTAG", "isViewVisable" + isViewVisable);
        if (isViewCreated && isViewVisable) {
            Log.v("MYTAG", "lazyLoad  加载数据loadData");
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isViewVisable = false;
        }
    }

    //默认不启用懒加载
    public boolean enableLazyData() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisable = isVisibleToUser;
        //如果启用懒加载就进行懒加载
        if (enableLazyData() && isViewVisable) {
            lazyLoad();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public <T> void onEvent(BaseEvent<T> baseEvent){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbind.unbind();
        AppManager.getAppmanager().removeFragment(this);
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onShowLoading() {
        mAppLoadService.showCallback(LoadingCallback.class);
    }


    @Override
    public void onHideLoading() {
        mAppLoadService.showSuccess();
    }


    @Override
    public void onShowNetError() {
        Log.v("网络异常 onShowNetError","");
    }

    @Override
    public void onShowDataEtmp() {
        Log.v("数据为空 onShowDataEtmp","");
    }
    public void startIntent(Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        startActivity(intent);
    }

    public void startIntentWithExtras(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void startIntentWithSpecificFlag(Class<?> cls, int flag) {
        Intent intent = new Intent(mActivity, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }
    /**
     * 退出时移除该Fragment
     */
    public void removeFragment() {
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this);
        ft.commitAllowingStateLoss();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Objects.requireNonNull(getActivity()).finish();
//        }
    }
}
