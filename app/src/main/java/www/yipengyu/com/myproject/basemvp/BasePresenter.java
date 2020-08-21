package www.yipengyu.com.myproject.basemvp;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by Android Studio.
 * User: ${ypu}
 * Date: 2020/7/27 0027
 * Time: 14:29
 */
public abstract class BasePresenter<M extends BaseModel, V> {
    protected Context mContext;
    protected V mView;
    protected M mModel;

    public BasePresenter(Context context) {
        mContext = context;
    }

    public void attach(V v) {
        attchView(v);
        attachModel();
    }

    public void attchView(V view) {
        mView = view;
    }

    public void detach() {
        detachView();
        detachModel();
    }

    public void detachView() {
        mView = null;
    }

    public void detachModel() {
        mModel.destory();
        mModel = null;
    }

    public void attachModel() {
        mModel = initModel();

    }

    public abstract M initModel();

    public void injectLifecycle(LifecycleProvider lifecycle) {
        if (mModel != null) {
            mModel.injectLifecycle(lifecycle);
        }
    }
}
