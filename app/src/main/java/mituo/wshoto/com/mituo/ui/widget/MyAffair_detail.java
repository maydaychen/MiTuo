package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;

public class MyAffair_detail extends RelativeLayout {

    @BindView(R.id.textView2)
    TextView order_num;
    @BindView(R.id.textView3)
    TextView data_time;
    @BindView(R.id.textView4)
    TextView data_address;
    private Context mContext;

    private RelativeLayout titleRl;

    private LinearLayout infoRl;

    private ImageView directionIv;
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
        ButterKnife.bind(this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.affair_title_rl);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);

        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(0, isOpened);
                    EventBus.getDefault().post(msg);
                }
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
        result.setDuration(300);
        result.addListener(changeStatusListener);
        return result;
    }

    public ObjectAnimator close() {
        AnimatorListener changeStatusListener = new AnimatorListener() {

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
        result.setDuration(300);
        result.addListener(changeStatusListener);
        return result;
    }

    public void setInfo(String a, String b, String c) {
        order_num.setText(String.format(getResources().getString(R.string.order_num), a));
        data_time.setText(String.format(getResources().getString(R.string.data_time), b));
        data_address.setText(String.format(getResources().getString(R.string.data_address), c));
        SpannableStringBuilder builder1 = new SpannableStringBuilder(order_num.getText().toString());
        SpannableStringBuilder builder2 = new SpannableStringBuilder(data_time.getText().toString());
        SpannableStringBuilder builder3 = new SpannableStringBuilder(data_address.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.font_99));
        builder1.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        order_num.setText(builder1);
        data_time.setText(builder2);
        data_address.setText(builder3);
    }

}
