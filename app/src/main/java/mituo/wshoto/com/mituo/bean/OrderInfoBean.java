package mituo.wshoto.com.mituo.bean;

/**
 * Created by Weshine on 2017/6/23.
 */

public class OrderInfoBean {
    /**
     * resultMsg : 请求成功
     * resultData : {"orderCode":"BY20170622000","yyAddress":"12314444","yyTime":"2017-06-22 16:44:15.0"}
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
         * orderCode : BY20170622000
         * yyAddress : 12314444
         * yyTime : 2017-06-22 16:44:15.0
         */

        private String orderCode;
        private String yyAddress;
        private String yyTime;

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getYyAddress() {
            return yyAddress;
        }

        public void setYyAddress(String yyAddress) {
            this.yyAddress = yyAddress;
        }

        public String getYyTime() {
            return yyTime;
        }

        public void setYyTime(String yyTime) {
            this.yyTime = yyTime;
        }
    }
}
