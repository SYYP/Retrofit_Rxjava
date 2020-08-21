package www.yipengyu.com.myproject.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import www.yipengyu.com.myproject.R;

public class TitleBar extends RelativeLayout {
    private View mView;
    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        mView = inflate(getContext(), R.layout.view_baseview_title_bar, this);
    }
}
