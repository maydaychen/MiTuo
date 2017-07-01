package mituo.wshoto.com.mituo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Weshine on 2017/6/23.
 */

public class GatherBean {
    /**
     * code : 200
     * result : true
     * resultMsg : 请求成功
     * resultData : {"tcList":[{"tcName":"套餐名称","tcJmfy":"套餐减免费用","tcPrice":"套餐价格","tcxmList":[{"xmName":"维修项目名称","xmprice":"项目费用","pjlb":"配件类别","pjCode":"配件编码","pjName":"配件名称","pjpp":"配件品牌","pjPrice":"配件价格","pjNum":"配件数量","isZd":"是否自带"}]}],"xmList":[{"xmName":"维修项目名称","xmprice":"项目费用","pjlb":"配件类别","pjCode":"配件编码","pjName":"配件名称","pjpp":"配件品牌","pjPrice":"配件价格","pjNum":"配件数量","isZd":"是否自带"}],"gsf":"工时费","smfwf":"上门服务费","fzdjmf":"非自带减免费","hj":"合计"}
     */

    private String code;
    private boolean result;
    private String resultMsg;
    private ResultDataBean resultData;

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

    public static class ResultDataBean implements Serializable{
        /**
         * tcList : [{"tcName":"套餐名称","tcJmfy":"套餐减免费用","tcPrice":"套餐价格","tcxmList":[{"xmName":"维修项目名称","xmprice":"项目费用","pjlb":"配件类别","pjCode":"配件编码","pjName":"配件名称","pjpp":"配件品牌","pjPrice":"配件价格","pjNum":"配件数量","isZd":"是否自带"}]}]
         * xmList : [{"xmName":"维修项目名称","xmprice":"项目费用","pjlb":"配件类别","pjCode":"配件编码","pjName":"配件名称","pjpp":"配件品牌","pjPrice":"配件价格","pjNum":"配件数量","isZd":"是否自带"}]
         * gsf : 工时费
         * smfwf : 上门服务费
         * fzdjmf : 非自带减免费
         * hj : 合计
         */

        private String gsf;
        private String smfwf;
        private String fzdjmf;
        private String hj;
        private List<TcListBean> tcList;
        private List<XmListBean> xmList;

        public String getGsf() {
            return gsf;
        }

        public void setGsf(String gsf) {
            this.gsf = gsf;
        }

        public String getSmfwf() {
            return smfwf;
        }

        public void setSmfwf(String smfwf) {
            this.smfwf = smfwf;
        }

        public String getFzdjmf() {
            return fzdjmf;
        }

        public void setFzdjmf(String fzdjmf) {
            this.fzdjmf = fzdjmf;
        }

        public String getHj() {
            return hj;
        }

        public void setHj(String hj) {
            this.hj = hj;
        }

        public List<TcListBean> getTcList() {
            return tcList;
        }

        public void setTcList(List<TcListBean> tcList) {
            this.tcList = tcList;
        }

        public List<XmListBean> getXmList() {
            return xmList;
        }

        public void setXmList(List<XmListBean> xmList) {
            this.xmList = xmList;
        }

        public static class TcListBean implements Serializable{
            /**
             * tcName : 套餐名称
             * tcJmfy : 套餐减免费用
             * tcPrice : 套餐价格
             * tcxmList : [{"xmName":"维修项目名称","xmprice":"项目费用","pjlb":"配件类别","pjCode":"配件编码","pjName":"配件名称","pjpp":"配件品牌","pjPrice":"配件价格","pjNum":"配件数量","isZd":"是否自带"}]
             */

            private String tcName;
            private String tcJmfy;
            private String tcPrice;
            private List<TcxmListBean> tcxmList;

            public String getTcName() {
                return tcName;
            }

            public void setTcName(String tcName) {
                this.tcName = tcName;
            }

            public String getTcJmfy() {
                return tcJmfy;
            }

            public void setTcJmfy(String tcJmfy) {
                this.tcJmfy = tcJmfy;
            }

            public String getTcPrice() {
                return tcPrice;
            }

            public void setTcPrice(String tcPrice) {
                this.tcPrice = tcPrice;
            }

            public List<TcxmListBean> getTcxmList() {
                return tcxmList;
            }

            public void setTcxmList(List<TcxmListBean> tcxmList) {
                this.tcxmList = tcxmList;
            }

            public static class TcxmListBean implements Serializable{
                /**
                 * xmName : 维修项目名称
                 * xmprice : 项目费用
                 * pjlb : 配件类别
                 * pjCode : 配件编码
                 * pjName : 配件名称
                 * pjpp : 配件品牌
                 * pjPrice : 配件价格
                 * pjNum : 配件数量
                 * isZd : 是否自带
                 */

                private String xmName;
                private String xmprice;
                private String pjlb;
                private String pjCode;
                private String pjName;
                private String pjpp;
                private String pjPrice;
                private String pjNum;
                private String isZd;

                public String getXmName() {
                    return xmName;
                }

                public void setXmName(String xmName) {
                    this.xmName = xmName;
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

                public String getPjCode() {
                    return pjCode;
                }

                public void setPjCode(String pjCode) {
                    this.pjCode = pjCode;
                }

                public String getPjName() {
                    return pjName;
                }

                public void setPjName(String pjName) {
                    this.pjName = pjName;
                }

                public String getPjpp() {
                    return pjpp;
                }

                public void setPjpp(String pjpp) {
                    this.pjpp = pjpp;
                }

                public String getPjPrice() {
                    return pjPrice;
                }

                public void setPjPrice(String pjPrice) {
                    this.pjPrice = pjPrice;
                }

                public String getPjNum() {
                    return pjNum;
                }

                public void setPjNum(String pjNum) {
                    this.pjNum = pjNum;
                }

                public String getIsZd() {
                    return isZd;
                }

                public void setIsZd(String isZd) {
                    this.isZd = isZd;
                }
            }
        }

        public static class XmListBean implements Serializable{
            /**
             * xmName : 维修项目名称
             * xmprice : 项目费用
             * pjlb : 配件类别
             * pjCode : 配件编码
             * pjName : 配件名称
             * pjpp : 配件品牌
             * pjPrice : 配件价格
             * pjNum : 配件数量
             * isZd : 是否自带
             */

            private String xmName;
            private String xmprice;
            private String pjlb;
            private String pjCode;
            private String pjName;
            private String pjpp;
            private String pjPrice;
            private String pjNum;
            private String isZd;

            public String getXmName() {
                return xmName;
            }

            public void setXmName(String xmName) {
                this.xmName = xmName;
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

            public String getPjCode() {
                return pjCode;
            }

            public void setPjCode(String pjCode) {
                this.pjCode = pjCode;
            }

            public String getPjName() {
                return pjName;
            }

            public void setPjName(String pjName) {
                this.pjName = pjName;
            }

            public String getPjpp() {
                return pjpp;
            }

            public void setPjpp(String pjpp) {
                this.pjpp = pjpp;
            }

            public String getPjPrice() {
                return pjPrice;
            }

            public void setPjPrice(String pjPrice) {
                this.pjPrice = pjPrice;
            }

            public String getPjNum() {
                return pjNum;
            }

            public void setPjNum(String pjNum) {
                this.pjNum = pjNum;
            }

            public String getIsZd() {
                return isZd;
            }

            public void setIsZd(String isZd) {
                this.isZd = isZd;
            }
        }
    }
}
