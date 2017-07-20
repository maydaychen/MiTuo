package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.ReportAdapter;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.ui.activity.CheckReportActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_check_report extends RelativeLayout {

    @BindView(R.id.lv_report)
    RecyclerView mLvReport;
    private Context mContext;

    private RelativeLayout titleRl;

    private LinearLayout infoRl;
    private Button edit;
    private ImageView directionIv;
    private String mOrderNum;
    private View baseV;
    private ReportBean mReportBean;
    private boolean isOpened = false;
    public boolean IS_OK = true;
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
        ButterKnife.bind(this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);

        edit = (Button) baseV.findViewById(R.id.check_report_edit);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    if (!isOpened) {
                        getObjectAnimator().start();
                        OrderMessage msg = new OrderMessage(5, isOpened);
                        EventBus.getDefault().post(msg);
                    }
                }
            }
        });
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CheckReportActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("objs", mReportBean);
            intent.putExtras(bundle);
            intent.putExtra("oid", mOrderNum);
            mContext.startActivity(intent);
        });
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

    public void setInfo(int status, ReportBean reportBean, String orderNum) {
        edit.setClickable(true);
        mOrderNum = orderNum;
        mReportBean = reportBean;
        if (status == 1) {
            edit.setVisibility(GONE);
        }
        List<String> list = new ArrayList<>();
        for (ReportBean.ResultDataBean.Step2Bean step2Bean : reportBean.getResultData().getStep2()) {
            for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : step2Bean.getList()) {
                switch (listBean.getBgxmValue()) {
                    case "1":
                        list.add(listBean.getBgxmName() + "建议进场检查");
                        break;
                    case "2":
                        list.add(listBean.getBgxmName() + "急需更换或维修");
                        break;
                }
            }
        }
        if (list.size() == 0) {
            IS_OK = false;
        }
        mLvReport.setLayoutManager(new LinearLayoutManager(mContext));
        ReportAdapter reportAdapter = new ReportAdapter(list);
        mLvReport.setAdapter(reportAdapter);

    }

}