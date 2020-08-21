package www.yipengyu.com.myproject.interfaces;

/**
 * created by yuanye
 * on 2020/5/27
 * describe:我们自己跟服务器约定的协议。
 */
public interface IAppServerCode {
    //	处理成功，无错误
    int succ = 100000;
    //查询不到该设备信息 ,设备未绑定
    int device_not_find = 210001;

    //token过期
    int token_invalid = 100202;
    //错误码，被解绑
    int untie_device = 100103;
    //该学生已经提交作答了
    int already_commit = 202003;

    //设备没有连接任何网络 ,或者htp出现错误
    int http_error = 1003;
    //未知错误 ,设备连接WIFI 但是没有外网 报解析域名错误
    int unknown = 1000;

}
