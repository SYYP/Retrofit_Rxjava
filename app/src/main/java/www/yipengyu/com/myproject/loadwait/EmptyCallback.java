package www.yipengyu.com.myproject.loadwait;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;


import www.yipengyu.com.myproject.R;

/**
 * created by yuanye
 * on 2020/6/12
 * describe:
 */
public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.callback_libview_empty;
    }


    @Override
    protected void onViewCreate(Context context, View view) {

    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return super.onReloadEvent(context, view);
    }

}

