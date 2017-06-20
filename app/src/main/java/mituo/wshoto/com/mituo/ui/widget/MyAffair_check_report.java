package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.ui.activity.CheckReportActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_check_report extends RelativeLayout {

    private Context mContext;

    private RelativeLayout titleRl;

    private LinearLayout infoRl;
    private Button edit;
    private ImageView directionIv;

    private View baseV;

    private boolean isOpened = false;

    private boolean animatorLock = false;

    public MyAffair_check_report(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_check_report(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_check_report(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_check_report, this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);

        edit = (Button) baseV.findViewById(R.id.check_report_edit);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(5, isOpened);
                    EventBus.getDefault().post(msg);
                }
            }
        });
        edit.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, CheckReportActivity.class)));
    }

    private ObjectAnimator getObjectAnimator() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
//                if (!isOpened) {
                    infoRl.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if (isOpened) {
//                    infoRl.setVisibility(View.GONE);
//                }
                isOpened = true;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
//        if (!isOpened) {
            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 0, 1f);
//        } else {
//            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
//        }
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

}