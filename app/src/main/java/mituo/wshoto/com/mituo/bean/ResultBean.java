package mituo.wshoto.com.mituo.bean;

/**
 * Created by Weshine on 2017/6/21.
 */

public class ResultBean {
    /**
     * resultMsg : 请求成功
     * result : true
     * code : 200
     */

    private String resultMsg;
    private boolean result;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
