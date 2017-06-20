package mituo.wshoto.com.mituo;

/**
 * Created by Weshine on 2017/6/20.
 */

public class OrderMessage {
    public int type = 0;
    public boolean isOpen = false;

    public OrderMessage(int type, boolean isOpen) {
        this.type = type;
        this.isOpen = isOpen;
    }
}
