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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.ui.activity.RecordActivity;

import static mituo.wshoto.com.mituo.MemorySpaceCheck.fileIsExists;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_repair_video extends RelativeLayout {
    private Context mContext;

    private RelativeLayout titleRl;
    private ImageView iv_remind;
    private TextView tv_remind;
    private LinearLayout infoRl;

    private ImageView directionIv;
    private Button edit;
    private View baseV;
    private String orderNum;
    private boolean isOpened = false;
    private boolean isFileExist = false;
    private boolean animatorLock = false;

    public MyAffair_repair_video(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_repair_video(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_repair_video(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_repair_video, this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);
        iv_remind = (ImageView) baseV.findViewById(R.id.iv_remind);
        tv_remind = (TextView) baseV.findViewById(R.id.tv_remind);
        edit = (Button) baseV.findViewById(R.id.video_edit);
//        directionIv.setBackgroundResource(R.drawable.p7_2_001);
        isFileExist = fileIsExists("");
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(3, isOpened);
                    EventBus.getDefault().post(msg);
                }
            }
        });
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RecordActivity.class);
            intent.putExtra("oid", orderNum);
            mContext.startActivity(intent);
        });
    }

    private ObjectAnimator getObjectAnimator() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
                if (isFileExist) {
                    if (!isOpened) {
                        infoRl.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!isOpened) {
                        edit.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFileExist) {
                    if (isOpened) {
                        infoRl.setVisibility(View.GONE);
                    }
                } else {
                    if (isOpened) {
                        edit.setVisibility(View.GONE);
                    }
                }
                isOpened = true;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        if (isFileExist) {
            if (!isOpened) {
                result = ObjectAnimator.ofFloat(infoRl, "scaleY", 0, 1f);
            } else {
                result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
            }
        } else {
            if (!isOpened) {
                result = ObjectAnimator.ofFloat(edit, "scaleY", 0, 1f);
            } else {
                result = ObjectAnimator.ofFloat(edit, "scaleY", 1f, 0);
            }
        }

        result.setDuration(300);
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
                if (isFileExist) {
                    if (isOpened) {
                        infoRl.setVisibility(View.GONE);
                    }
                } else {
                    if (isOpened) {
                        edit.setVisibility(View.GONE);
                    }
                }
                isOpened = false;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        if (isFileExist) {
            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
        } else {
            result = ObjectAnimator.ofFloat(edit, "scaleY", 1f, 0);
        }

        result.setDuration(300);
        result.addListener(changeStatusListener);
        return result;
    }

    public void setRemind(int i) {
        switch (i) {
            case 0:
                iv_remind.setVisibility(GONE);
                tv_remind.setVisibility(GONE);
                break;
            case 1:
                iv_remind.setImageResource(R.drawable.order_details_icon_remind);
                tv_remind.setText("上传失败");
                break;
            case 2:
                iv_remind.setImageResource(R.drawable.icon_yellow);
                tv_remind.setText("尚未上传");
                tv_remind.setTextColor(getResources().getColor(R.color.yellow));
                break;
        }
    }

    public void setInfo(int status, String oid) {
        orderNum = oid;
        if (status == 1) {
            edit.setVisibility(GONE);
        }

    }

}