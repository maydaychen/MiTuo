package mituo.wshoto.com.mituo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/7/5.
 */

public class AllRepairBean {
    /**
     * code : 200
     * result : true
     * resultData : {"tcList":[{"tcxmList":[{"xmName":"更换汽机油","isZd":0,"xmprice":null,"pjlb":"汽机油"},{"xmName":"更换机油滤芯","isZd":0,"xmprice":null,"pjlb":"机油滤芯"},{"xmName":"39项检测","isZd":null,"xmprice":null,"pjlb":null}],"tcPrice":null,"tcJmfy":50,"tcName":"小保养","isBzdjm":0}],"pjList":[{"pjName":"汽机油","pjCode":"SN 5W-40 4L","pjPrice":400,"pjpp":"壳英能硼磁全合成","pjlb":"汽机油"},{"pjName":"空气滤芯","pjCode":"HA/DZ008U","pjPrice":90,"pjpp":"昊牌","pjlb":"空气滤芯"},{"pjName":"汽机油111","pjCode":"SN 5W-40 1L","pjPrice":110,"pjpp":"嘉实多极护全合成","pjlb":"汽机油"},{"pjName":"空调滤芯","pjCode":"HC/TY016C","pjPrice":120,"pjpp":"昊牌","pjlb":"空调滤芯"},{"pjName":"机油滤芯","pjCode":"HO/BC010Z","pjPrice":120,"pjpp":"昊牌","pjlb":"机油滤芯"}],"xmList":[{"xmName":"更换空调滤芯","isZd":1,"xmprice":0,"pjlb":"空调滤芯"},{"xmName":"更换空气滤芯1","isZd":0,"xmprice":0,"pjlb":"机油滤芯"},{"xmName":"更换空调滤芯1","isZd":0,"xmprice":0,"pjlb":"空气滤芯"}]}
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
        private List<TcListBean> tcList;
        private List<PjListBean> pjList;
        private List<XmListBean> xmList;

        public List<TcListBean> getTcList() {
            return tcList;
        }

        public void setTcList(List<TcListBean> tcList) {
            this.tcList = tcList;
        }

        public List<PjListBean> getPjList() {
            return pjList;
        }

        public void setPjList(List<PjListBean> pjList) {
            this.pjList = pjList;
        }

        public List<XmListBean> getXmList() {
            return xmList;
        }

        public void setXmList(List<XmListBean> xmList) {
            this.xmList = xmList;
        }

        public static class TcListBean implements Serializable{
            /**
             * tcxmList : [{"xmName":"更换汽机油","isZd":0,"xmprice":null,"pjlb":"汽机油"},{"xmName":"更换机油滤芯","isZd":0,"xmprice":null,"pjlb":"机油滤芯"},{"xmName":"39项检测","isZd":null,"xmprice":null,"pjlb":null}]
             * tcPrice : null
             * tcJmfy : 50
             * tcName : 小保养
             * isBzdjm : 0
             */

            private String tcPrice;
            private int tcJmfy;
            private String tcName;
            private String isBzdjm;
            private List<TcxmListBean> tcxmList;

            public String getTcPrice() {
                return tcPrice;
            }

            public void setTcPrice(String tcPrice) {
                this.tcPrice = tcPrice;
            }

            public int getTcJmfy() {
                return tcJmfy;
            }

            public void setTcJmfy(int tcJmfy) {
                this.tcJmfy = tcJmfy;
            }

            public String getTcName() {
                return tcName;
            }

            public void setTcName(String tcName) {
                this.tcName = tcName;
            }

            public String getIsBzdjm() {
                return isBzdjm;
            }

            public void setIsBzdjm(String isBzdjm) {
                this.isBzdjm = isBzdjm;
            }

            public List<TcxmListBean> getTcxmList() {
                return tcxmList;
            }

            public void setTcxmList(List<TcxmListBean> tcxmList) {
                this.tcxmList = tcxmList;
            }

            public static class TcxmListBean implements Serializable{
                /**
                 * xmName : 更换汽机油
                 * isZd : 0
                 * xmprice : null
                 * pjlb : 汽机油
                 */

                private String xmName;
                private String isZd;
                private String xmprice;
                private String pjlb;


                public String getPjName() {
                    return pjName;
                }

                public void setPjName(String pjName) {
                    this.pjName = pjName;
                }

                public String getPjNum() {
                    return pjNum;
                }

                public void setPjNum(String pjNum) {
                    this.pjNum = pjNum;
                }

                public String getPjCode() {
                    return pjCode;
                }

                public void setPjCode(String pjCode) {
                    this.pjCode = pjCode;
                }

                public String getPjPrice() {
                    return pjPrice;
                }

                public void setPjPrice(String pjPrice) {
                    this.pjPrice = pjPrice;
                }

                public String getPjpp() {
                    return pjpp;
                }

                public void setPjpp(String pjpp) {
                    this.pjpp = pjpp;
                }

                private String pjName;
                private String pjNum;
                private String pjCode;
                private String pjPrice;
                private String pjpp;

                public String getXmName() {
                    return xmName;
                }

                public void setXmName(String xmName) {
                    this.xmName = xmName;
                }

                public String getIsZd() {
                    return isZd;
                }

                public void setIsZd(String isZd) {
                    this.isZd = isZd;
                }

                public String getXmprice() {
                    return xmprice;
                }

                public void setXmprice(String xmprice) {
                    this.xmprice = xmprice;
                }

                public String getPjlb() {
                    return pjlb;
                }

                public void setPjlb(String pjlb) {
                    this.pjlb = pjlb;
                }
            }
        }

        public static class PjListBean implements Serializable{
            /**
             * pjName : 汽机油
             * pjCode : SN 5W-40 4L
             * pjPrice : 400
             * pjpp : 壳英能硼磁全合成
             * pjlb : 汽机油
             */

            private String pjName;
            private String pjCode;
            private String pjPrice;
            private String pjpp;
            private String pjlb;

            public String getPjName() {
                return pjName;
            }

            public void setPjName(String pjName) {
                this.pjName = pjName;
            }

            public String getPjCode() {
                return pjCode;
            }

            public void setPjCode(String pjCode) {
                this.pjCode = pjCode;
            }

            public String getPjPrice() {
                return pjPrice;
            }

            public void setPjPrice(String pjPrice) {
                this.pjPrice = pjPrice;
            }

            public String getPjpp() {
                return pjpp;
            }

            public void setPjpp(String pjpp) {
                this.pjpp = pjpp;
            }

            public String getPjlb() {
                return pjlb;
            }

            public void setPjlb(String pjlb) {
                this.pjlb = pjlb;
            }
        }

        public static class XmListBean implements Serializable{
            /**
             * xmName : 更换空调滤芯
             * isZd : 1
             * xmprice : 0
             * pjlb : 空调滤芯
             */

            private String xmName;
            private String isZd;
            private String xmprice;
            private String pjlb;

            public String getXmName() {
                return xmName;
            }

            public void setXmName(String xmName) {
                this.xmName = xmName;
            }

            public String getIsZd() {
                return isZd;
            }

            public void setIsZd(String isZd) {
                this.isZd = isZd;
            }

            public String getXmprice() {
                return xmprice;
            }

            public void setXmprice(String xmprice) {
                this.xmprice = xmprice;
            }

            public String getPjlb() {
                return pjlb;
            }

            public void setPjlb(String pjlb) {
                this.pjlb = pjlb;
            }
        }
    }
}
