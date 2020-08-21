package www.yipengyu.com.myproject.loadwait;

import android.content.Context;
import android.view.View;


import www.yipengyu.com.myproject.R;

/**
 * created by yuanye
 * on 2020/6/1
 * describe:
 * 使用地方 1.网络
 */
public class NetCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.callback_libview_net;
    }


    @Override
    protected void onViewCreate(Context context, View view) {
//        view.findViewById(R.id.btn_callback_gotoWifi).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NetworkUtils.gotoWifiSettings();
//            }
//        });
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return  super.onReloadEvent(context ,view) ;
    }

}
