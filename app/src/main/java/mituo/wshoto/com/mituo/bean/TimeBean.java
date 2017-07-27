package mituo.wshoto.com.mituo.bean;

/**
 * Created by user on 2017/7/1.
 */

public class TimeBean {
    /**
     * code : 200
     * result : true
     * resultData : {"timeStamp":1501126556068,"time":"2017-07-27 11:35:56"}
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
         * timeStamp : 1501126556068
         * time : 2017-07-27 11:35:56
         */

        private long timeStamp;
        private String time;

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
