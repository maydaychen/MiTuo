package mituo.wshoto.com.mituo.bean;

import java.util.List;

/**
 * Created by user on 2017/7/19.
 */

public class SearchBean {
    /**
     * code : 200
     * result : true
     * resultData : {"list":[{"carNo":"苏b12345","createTime":null,"orderStatus":"1","orderCode":"BY20170719009"},{"carNo":"苏b12345","createTime":null,"orderStatus":"0","orderCode":"BY20170719026"},{"carNo":"苏B12345","createTime":null,"orderStatus":"1","orderCode":"BY20170721017"},{"carNo":"苏Q12345","createTime":null,"orderStatus":"0","orderCode":"BY20170725024"}]}
     * resultMsg : 请求成功
     */

    private String code;
    private boolean result;
    private ResultDataBean resultData;
    private String resultMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public static class ResultDataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * carNo : 苏b12345
             * createTime : null
             * orderStatus : 1
             * orderCode : BY20170719009
             */

            private String carNo;
            private String createTime;
            private String orderStatus;
            private String orderCode;

            public String getCarNo() {
                return carNo;
            }

            public void setCarNo(String carNo) {
                this.carNo = carNo;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }
        }
    }
}
