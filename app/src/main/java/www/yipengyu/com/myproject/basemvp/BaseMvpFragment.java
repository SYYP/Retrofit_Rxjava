package www.yipengyu.com.myproject.basemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import www.yipengyu.com.myproject.base.BaseFragment;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/27 0027
 * Time: 14:58
 */
public abstract class BaseMvpFragment<M extends BaseModel, V, P extends BasePresenter<M, V>> extends BaseFragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
            mPresenter.injectLifecycle(mActivity);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }

    public abstract P initPresenter();
}
