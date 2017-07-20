package mituo.wshoto.com.mituo;

import java.util.ArrayList;
import java.util.List;

import mituo.wshoto.com.mituo.bean.ReportBean;

/**
 * Created by user on 2017/7/20.
 */

public class CheckMessage {
    public List<ReportBean.ResultDataBean.Step2Bean.ListBean> mListBeen = new ArrayList<>();

    public CheckMessage(List<ReportBean.ResultDataBean.Step2Bean.ListBean> listBeen) {
        mListBeen = listBeen;
    }
}
