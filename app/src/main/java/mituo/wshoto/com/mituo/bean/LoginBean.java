package mituo.wshoto.com.mituo.bean;

/**
 * Created by Weshine on 2017/6/22.
 */

public class LoginBean {
    /**
     * resultMsg : 请求成功
     * resultData : {"token":"be65614e199416dbdcad1543b2b2d84d"}
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
         * token : be65614e199416dbdcad1543b2b2d84d
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
