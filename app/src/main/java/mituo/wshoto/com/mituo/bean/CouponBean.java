package mituo.wshoto.com.mituo.bean;

/**
 * Created by user on 2017/6/30.
 */

public class CouponBean {
    /**
     * code : 200
     * resultMsg : 请求成功
     * result : true
     * resultData : {"couponPrice":"优惠券面值"}
     */

    private String code;
    private String resultMsg;
    private boolean result;
    private ResultDataBean resultData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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

    public static class ResultDataBean {
        /**
         * couponPrice : 优惠券面值
         */

        private String couponPrice;

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }
    }
}
