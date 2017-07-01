package mituo.wshoto.com.mituo.bean;

/**
 * Created by Weshine on 2017/6/22.
 */

public class EmsBean {
    /**
     * resultMsg : 请求成功
     * resultData : {"vcode":5354}
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
         * vcode : 5354
         */

        private int vcode;

        public int getVcode() {
            return vcode;
        }

        public void setVcode(int vcode) {
            this.vcode = vcode;
        }
    }
}
