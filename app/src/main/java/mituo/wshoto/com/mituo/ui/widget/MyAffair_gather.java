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
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.GatherAdapter;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.ui.activity.GatherActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_gather extends RelativeLayout {

    @BindView(R.id.rv_gather)
    RecyclerView rvGather;
    @BindView(R.id.tv_gather_money)
    TextView tvGatherMoney;
    private Context mContext;
    private ImageView iv_remind;
    private TextView tv_remind;
    private RelativeLayout titleRl;
    private String orderNum;
    private LinearLayout infoRl;
    private Button edit;
    private View baseV;

    private boolean isOpened = true;
    private boolean animatorLock = false;
    private GatherBean.ResultDataBean mResultDataBean;

    public MyAffair_gather(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_gather(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_gather(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_gather, this);
        ButterKnife.bind(this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.affair_title_rl);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        iv_remind = (ImageView) baseV.findViewById(R.id.iv_remind);
        tv_remind = (TextView) baseV.findViewById(R.id.tv_remind);
        edit = (Button) baseV.findViewById(R.id.gather_edit);
//        directionIv.setBackgroundResource(R.drawable.p7_2_001);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(6, isOpened);
                    EventBus.getDefault().post(msg);
                }
            }
        });
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GatherActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("objs", mResultDataBean);
            intent.putExtras(bundle);
            intent.putExtra("oid", orderNum);
            mContext.startActivity(intent);
        });
    }

    private ObjectAnimator getObjectAnimator() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

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

    public void setRemind(int i) {
        switch (i) {
            case 0:
                iv_remind.setVisibility(GONE);
                tv_remind.setVisibility(GONE);
                break;
            case 1:
                iv_remind.setImageResource(R.drawable.order_details_icon_remind);
                tv_remind.setText("尚未完善");
                break;
        }
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

    public void setInfo(GatherBean.ResultDataBean mResultBean, int status, String oid) {
        orderNum = oid;
        List<Map<String, String>> list = new ArrayList<>();
        mResultDataBean = mResultBean;
        if (status == 1) {
            edit.setVisibility(GONE);
        }
        for (GatherBean.ResultDataBean.TcListBean tcListBean : mResultBean.getTcList()) {
            for (GatherBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : tcListBean.getTcxmList()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", tcxmListBean.getXmName());
                map.put("peijian", tcxmListBean.getPjName());
                map.put("num", tcxmListBean.getPjNum());
                map.put("price", tcxmListBean.getPjPrice());
                list.add(map);
            }
        }
        for (GatherBean.ResultDataBean.XmListBean xmListBean : mResultBean.getXmList()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", xmListBean.getXmName());
            map.put("peijian", xmListBean.getPjName());
            map.put("num", xmListBean.getPjNum());
            map.put("price", xmListBean.getPjPrice());
            list.add(map);
        }
        GatherAdapter gatherAdapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvGather.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        rvGather.setLayoutManager(layoutManager);
        gatherAdapter = new GatherAdapter(list);
        rvGather.setAdapter(gatherAdapter);
        tvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
    }
}