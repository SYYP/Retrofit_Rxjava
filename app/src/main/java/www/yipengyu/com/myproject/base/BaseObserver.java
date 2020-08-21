package www.yipengyu.com.myproject.base;

import android.util.Log;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import www.yipengyu.com.myproject.errorhnadler.ExceptionHandler;
import www.yipengyu.com.myproject.interfaces.IAppServerCode;
import www.yipengyu.com.myproject.interfaces.IbaseLoadView;
import www.yipengyu.com.myproject.log.klog.KLog;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/22 0022
 * Time: 10:15
 */
public abstract class BaseObserver<T> implements Observer<BaseResult<T>> {

    private IbaseLoadView mLoadView;

    public BaseObserver(IbaseLoadView loadView) {
        this.mLoadView = loadView;
    }

    public BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        showLoading();
    }

    @Override
    public void onNext(BaseResult<T> tBaseResult) {
        hideLoading();
        onLoadSucc(tBaseResult.Data, tBaseResult.total);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();

        if (e instanceof ExceptionHandler.ResponeThrowable) {
            ExceptionHandler.ResponeThrowable error = (ExceptionHandler.ResponeThrowable) e;
            KLog.e(error.message + ":::::::" + error.code);
            if (error.code == IAppServerCode.http_error || error.code == IAppServerCode.unknown) {
                if (mLoadView != null) {
                    mLoadView.onShowNetError();
                } else {
                    onLoadError(e);
                }
            }
        } else {
            onLoadError(e);
        }
    }

    public void showLoading() {
        if (mLoadView != null) {
            mLoadView.onShowLoading();
        }
    }

    public void hideLoading() {
        if (mLoadView != null) {
            mLoadView.onHideLoading();
        }
    }

    /*
        加载成功
     */
    public abstract void onLoadSucc(T t, int counts);

    /**
     * 加载失败
     *
     * @param throwable
     */
    public abstract void onLoadError(Throwable throwable);
}
