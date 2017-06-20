package mituo.wshoto.com.mituo.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：JTR on 2016/10/11 09:46
 * 邮箱：2091320109@qq.com
 */
public class CustomViewPager extends ViewPager {
//    少年你请说过反向isCanScroll么？
    private final boolean isCanScroll = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    @Override

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);

    }

    @Override

    public boolean onTouchEvent(MotionEvent arg0) {

        return !isCanScroll && super.onTouchEvent(arg0);

    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        return !isCanScroll && super.onInterceptTouchEvent(arg0);

    }

}