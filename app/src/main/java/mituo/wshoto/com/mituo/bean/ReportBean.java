package mituo.wshoto.com.mituo.bean;

import java.util.List;

/**
 * Created by user on 2017/7/3.
 */

public class ReportBean {
    /**
     * code : 200
     * result : true
     * resultData : {"step2":{"typeName":"111","list":[{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":2,"bgxmValue":null,"inputType":"2","bgxm_type_id":2},{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":4,"bgxmValue":null,"inputType":"123","bgxm_type_id":2}]},"step1":{"typeName":"nihao","list":[{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"www","bgxmId":1,"bgxmValue":null,"inputType":"1","bgxm_type_id":1},{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"哈哈","bgxmId":3,"bgxmValue":null,"inputType":"1212","bgxm_type_id":1}]}}
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
         * step2 : {"typeName":"111","list":[{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":2,"bgxmValue":null,"inputType":"2","bgxm_type_id":2},{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":4,"bgxmValue":null,"inputType":"123","bgxm_type_id":2}]}
         * step1 : {"typeName":"nihao","list":[{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"www","bgxmId":1,"bgxmValue":null,"inputType":"1","bgxm_type_id":1},{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"哈哈","bgxmId":3,"bgxmValue":null,"inputType":"1212","bgxm_type_id":1}]}
         */

        private Step2Bean step2;
        private Step1Bean step1;

        public Step2Bean getStep2() {
            return step2;
        }

        public void setStep2(Step2Bean step2) {
            this.step2 = step2;
        }

        public Step1Bean getStep1() {
            return step1;
        }

        public void setStep1(Step1Bean step1) {
            this.step1 = step1;
        }

        public static class Step2Bean {
            /**
             * typeName : 111
             * list : [{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":2,"bgxmValue":null,"inputType":"2","bgxm_type_id":2},{"id":2,"typeName":"111","bgxm_id":null,"bgxmName":"aaa","bgxmId":4,"bgxmValue":null,"inputType":"123","bgxm_type_id":2}]
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

            public static class ListBean {
                /**
                 * id : 2
                 * typeName : 111
                 * bgxm_id : null
                 * bgxmName : aaa
                 * bgxmId : 2
                 * bgxmValue : null
                 * inputType : 2
                 * bgxm_type_id : 2
                 */

                private int id;
                private String typeName;
                private String bgxm_id;
                private String bgxmName;
                private int bgxmId;
                private String bgxmValue;
                private String inputType;
                private int bgxm_type_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getBgxm_id() {
                    return bgxm_id;
                }

                public void setBgxm_id(String bgxm_id) {
                    this.bgxm_id = bgxm_id;
                }

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

                public int getBgxm_type_id() {
                    return bgxm_type_id;
                }

                public void setBgxm_type_id(int bgxm_type_id) {
                    this.bgxm_type_id = bgxm_type_id;
                }
            }
        }

        public static class Step1Bean {
            /**
             * typeName : nihao
             * list : [{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"www","bgxmId":1,"bgxmValue":null,"inputType":"1","bgxm_type_id":1},{"id":1,"typeName":"nihao","bgxm_id":null,"bgxmName":"哈哈","bgxmId":3,"bgxmValue":null,"inputType":"1212","bgxm_type_id":1}]
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

            public static class ListBeanX {
                /**
                 * id : 1
                 * typeName : nihao
                 * bgxm_id : null
                 * bgxmName : www
                 * bgxmId : 1
                 * bgxmValue : null
                 * inputType : 1
                 * bgxm_type_id : 1
                 */

                private int id;
                private String typeName;
                private String bgxm_id;
                private String bgxmName;
                private int bgxmId;
                private String bgxmValue;
                private String inputType;
                private int bgxm_type_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getBgxm_id() {
                    return bgxm_id;
                }

                public void setBgxm_id(String bgxm_id) {
                    this.bgxm_id = bgxm_id;
                }

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

                public int getBgxm_type_id() {
                    return bgxm_type_id;
                }

                public void setBgxm_type_id(int bgxm_type_id) {
                    this.bgxm_type_id = bgxm_type_id;
                }
            }
        }
    }
}
