package mituo.wshoto.com.mituo.bean;

/**
 * Created by user on 2017/7/25.
 */

public class CouponDetailBean {
    public CouponDetailBean(int money, String num) {
        this.money = money;
        this.num = num;
    }

    public int money;
    public String num;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
