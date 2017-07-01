package mituo.wshoto.com.mituo.bean;

import java.util.List;

/**
 * Created by Weshine on 2017/6/7.
 */

public class OrderBean {
    /**
     * resultMsg : 请求成功
     * resultData : {"count":1,"list":[{"orderCode":"BY20170622000","time":"2017-06-22 16:44:15.0","carNo":"苏AH1928"}]}
     * result : true
     * code : 200
     */

    private String resultMsg;
    private ResultDataBean resultData;
    private boolean result;
    private String code;

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultDataBean {
        /**
         * count : 1
         * list : [{"orderCode":"BY20170622000","time":"2017-06-22 16:44:15.0","carNo":"苏AH1928"}]
         */

        private int count;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * orderCode : BY20170622000
             * time : 2017-06-22 16:44:15.0
             * carNo : 苏AH1928
             */

            private String orderCode;
            private String time;
            private String carNo;

            public String getOrderCode() {
                return orderCode;
            }

            public void setOrderCode(String orderCode) {
                this.orderCode = orderCode;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCarNo() {
                return carNo;
            }

            public void setCarNo(String carNo) {
                this.carNo = carNo;
            }
        }
    }
}
