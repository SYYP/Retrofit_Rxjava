package www.yipengyu.com.myproject.interfaces;

/**
 *  加载框提示语
 */
public interface IbaseLoadView {

    void onShowLoading();

    void onHideLoading();

    /**
     * 网络错误
     */
    void onShowNetError();


    /***
     * 数据为空
     */
    void onShowDataEtmp();
}
