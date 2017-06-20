package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;

public class MyAffair_detail extends RelativeLayout {

    private Context mContext;

    private RelativeLayout titleRl;

    private LinearLayout infoRl;

    private ImageView directionIv;
    private TextView order_num;
    private TextView data_time;
    private TextView data_address;
    private View baseV;

    private boolean isOpened = false;

    private boolean animatorLock = false;

    public MyAffair_detail(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_detail(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_detail(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_detail, this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.affair_title_rl);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);
        order_num = (TextView) baseV.findViewById(R.id.textView2);
        data_time = (TextView) baseV.findViewById(R.id.textView3);
        data_address = (TextView) baseV.findViewById(R.id.textView4);
//        directionIv.setBackgroundResource(R.drawable.p7_2_001);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                getObjectAnimator().start();
                OrderMessage msg = new OrderMessage(0,isOpened);
                EventBus.getDefault().post(msg);
            }
        });
    }

    public ObjectAnimator getObjectAnimator() {
        AnimatorListener changeStatusListener = new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
                if (!isOpened) {
                    infoRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpened) {
                    infoRl.setVisibility(View.GONE);
                }
                isOpened = true;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        if (!isOpened) {
            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 0, 1f);
        } else {
            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
        }
        result.setDuration(500);
        result.addListener(changeStatusListener);
        return result;
    }

    public ObjectAnimator close() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpened) {
                    infoRl.setVisibility(View.GONE);
                }
                isOpened = false;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
        result.setDuration(500);
        result.addListener(changeStatusListener);
        return result;
    }

    public void setInfo(String a){
        order_num.setText(String.format(getResources().getString(R.string.order_num), a));
        data_time.setText(String.format(getResources().getString(R.string.data_time), a));
        data_address.setText(String.format(getResources().getString(R.string.data_address), a));
    }

}
