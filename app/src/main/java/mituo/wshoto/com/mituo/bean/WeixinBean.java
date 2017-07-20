package mituo.wshoto.com.mituo.bean;

/**
 * Created by user on 2017/7/5.
 */

public class WeixinBean {
    /**
     * code : 200
     * result : true
     * resultData : {"urlCode":"weixin://wxpay/bizpayurl?pr=jleMNbs"}
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
        /**
         * urlCode : weixin://wxpay/bizpayurl?pr=jleMNbs
         */

        private String urlCode;

        public String getUrlCode() {
            return urlCode;
        }

        public void setUrlCode(String urlCode) {
            this.urlCode = urlCode;
        }
    }
}
