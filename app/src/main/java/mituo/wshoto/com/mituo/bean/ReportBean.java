package mituo.wshoto.com.mituo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/7/3.
 */

public class ReportBean implements Serializable{
    /**
     * code : 200
     * result : true
     * resultData : {"step2":[{"typeName":"易损","list":[{"bgxmName":"aaa","bgxmId":4,"bgxmValue":"0","inputType":"123"}]}],"step1":[{"typeName":"底盘","list":[{"bgxmName":"www","bgxmId":1,"bgxmValue":null,"inputType":"1"},{"bgxmName":"哈哈","bgxmId":3,"bgxmValue":"1","inputType":"1212"}]}]}
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

    public static class ResultDataBean implements Serializable{
        private List<Step2Bean> step2;
        private List<Step1Bean> step1;

        public List<Step2Bean> getStep2() {
            return step2;
        }

        public void setStep2(List<Step2Bean> step2) {
            this.step2 = step2;
        }

        public List<Step1Bean> getStep1() {
            return step1;
        }

        public void setStep1(List<Step1Bean> step1) {
            this.step1 = step1;
        }

        public static class Step2Bean implements Serializable{
            /**
             * typeName : 易损
             * list : [{"bgxmName":"aaa","bgxmId":4,"bgxmValue":"0","inputType":"123"}]
             */

            private String typeName;
            private List<ListBean> list;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean implements Serializable{
                /**
                 * bgxmName : aaa
                 * bgxmId : 4
                 * bgxmValue : 0
                 * inputType : 123
                 */

                private String bgxmName;
                private int bgxmId;
                private String bgxmValue;
                private String inputType;

                public String getBgxmName() {
                    return bgxmName;
                }

                public void setBgxmName(String bgxmName) {
                    this.bgxmName = bgxmName;
                }

                public int getBgxmId() {
                    return bgxmId;
                }

                public void setBgxmId(int bgxmId) {
                    this.bgxmId = bgxmId;
                }

                public String getBgxmValue() {
                    return bgxmValue;
                }

                public void setBgxmValue(String bgxmValue) {
                    this.bgxmValue = bgxmValue;
                }

                public String getInputType() {
                    return inputType;
                }

                public void setInputType(String inputType) {
                    this.inputType = inputType;
                }
            }
        }

        public static class Step1Bean implements Serializable{
            /**
             * typeName : 底盘
             * list : [{"bgxmName":"www","bgxmId":1,"bgxmValue":null,"inputType":"1"},{"bgxmName":"哈哈","bgxmId":3,"bgxmValue":"1","inputType":"1212"}]
             */

            private String typeName;
            private List<ListBeanX> list;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX implements Serializable{
                /**
                 * bgxmName : www
                 * bgxmId : 1
                 * bgxmValue : null
                 * inputType : 1
                 */

                private String bgxmName;
                private int bgxmId;
                private String bgxmValue;
                private String inputType;

                public String getBgxmName() {
                    return bgxmName;
                }

                public void setBgxmName(String bgxmName) {
                    this.bgxmName = bgxmName;
                }

                public int getBgxmId() {
                    return bgxmId;
                }

                public void setBgxmId(int bgxmId) {
                    this.bgxmId = bgxmId;
                }

                public String getBgxmValue() {
                    return bgxmValue;
                }

                public void setBgxmValue(String bgxmValue) {
                    this.bgxmValue = bgxmValue;
                }

                public String getInputType() {
                    return inputType;
                }

                public void setInputType(String inputType) {
                    this.inputType = inputType;
                }
            }
        }
    }
}
