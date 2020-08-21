package www.yipengyu.com.myproject.basemvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import www.yipengyu.com.myproject.base.BaseActivity;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/27 0027
 * Time: 14:47
 */
public abstract class BaseMvpActivity<M extends BaseModel, V, P extends BasePresenter<M, V>> extends BaseActivity {
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
            mPresenter.injectLifecycle(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    public abstract P initPresenter();
}
