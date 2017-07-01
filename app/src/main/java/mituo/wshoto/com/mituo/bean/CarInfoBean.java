package mituo.wshoto.com.mituo.bean;

import java.io.Serializable;

/**
 * Created by Weshine on 2017/6/23.
 */

public class CarInfoBean {
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

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

    /**
     * code : 200
     * result : true
     * resultMsg : 请求成功
     * resultData : {"contactName":"XXXX","contactPhone":"18555555656","carNo":"苏B12345","carXh":"车辆型号","carCjh":"车架号码","carFdjbh":"发动机编号","carXslc":"行驶里程"," xcbylc":"下次保养里程"," xcbyDate":"下次保养时间"}
     */

    private String code;
    private boolean result;
    private String resultMsg;
    private ResultDataBean resultData;

    public static class ResultDataBean implements Serializable{
        /**
         * contactName : XXXX
         * contactPhone : 18555555656
         * carNo : 苏B12345
         * carXh : 车辆型号
         * carCjh : 车架号码
         * carFdjbh : 发动机编号
         * carXslc : 行驶里程
         *  xcbylc : 下次保养里程
         *  xcbyDate : 下次保养时间
         */

        private String contactName;
        private String contactPhone;
        private String carNo;
        private String carXh;
        private String carCjh;
        private String carFdjbh;
        private String carXslc;

        public String getXcbylc() {
            return xcbylc;
        }

        public void setXcbylc(String xcbylc) {
            this.xcbylc = xcbylc;
        }

        public String getXcbyDate() {
            return xcbyDate;
        }

        public void setXcbyDate(String xcbyDate) {
            this.xcbyDate = xcbyDate;
        }

        private String xcbylc;
        private String xcbyDate;

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCarXh() {
            return carXh;
        }

        public void setCarXh(String carXh) {
            this.carXh = carXh;
        }

        public String getCarCjh() {
            return carCjh;
        }

        public void setCarCjh(String carCjh) {
            this.carCjh = carCjh;
        }

        public String getCarFdjbh() {
            return carFdjbh;
        }

        public void setCarFdjbh(String carFdjbh) {
            this.carFdjbh = carFdjbh;
        }

        public String getCarXslc() {
            return carXslc;
        }

        public void setCarXslc(String carXslc) {
            this.carXslc = carXslc;
        }
    }
}
