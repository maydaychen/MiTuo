package mituo.wshoto.com.mituo;

import mituo.wshoto.com.mituo.bean.RepairObjsBean;

/**
 * Created by user on 2017/7/24.
 */

public class RepairMessage {
    public RepairObjsBean.ResultDataBean mResultDataBean;

    public RepairMessage(RepairObjsBean.ResultDataBean resultDataBean) {
        mResultDataBean = resultDataBean;
    }
}
