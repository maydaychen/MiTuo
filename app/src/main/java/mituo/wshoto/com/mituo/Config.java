package mituo.wshoto.com.mituo;

import android.os.Environment;

import java.util.List;

import mituo.wshoto.com.mituo.bean.ReportBean;

/**
 * Created by user on 2017/6/30.
 */

public class Config {
    public static final String PATH_MOBILE = Environment.getExternalStorageDirectory().getPath() + "/mituo";

    public static List<ReportBean.ResultDataBean.Step2Bean.ListBean> mBean;//有问题step2值
    public static final String PATH_SD = System.getenv("SECONDARY_STORAGE") + "/mituo";
}
