package mituo.wshoto.com.mituo.bean;

import java.util.List;

/**
 * Created by user on 2017/6/28.
 */

public class PicBean {
    /**
     * code : 200
     * result : true
     * resultData : [{"base64":"","picName":"111"}]
     * resultMsg : 请求成功
     */

    private String code;
    private boolean result;
    private String resultMsg;
    private List<ResultDataBean> resultData;

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

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        /**
         * base64 :
         * picName : 111
         */

        private String base64;
        private String picName;

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }

        public String getPicName() {
            return picName;
        }

        public void setPicName(String picName) {
            this.picName = picName;
        }
    }
}
