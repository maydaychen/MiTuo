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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.RepairObjLeftAdapter;
import mituo.wshoto.com.mituo.adapter.RepairObjsDownAdapter;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.ui.activity.RepairObjectsActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_repair_objs extends RelativeLayout {

    @BindView(R.id.rv_taocan)
    RecyclerView mRvTaocan;
    @BindView(R.id.rv_repair_objs)
    RecyclerView mRvRepairObjs;
    @BindView(R.id.ll_taocan)
    LinearLayout mLlTaocan;
    private Context mContext;
    private RepairObjLeftAdapter mRepairObjLeftAdapter;
    private RelativeLayout titleRl;
    private Button edit;
    private LinearLayout infoRl;
    private RepairObjsBean.ResultDataBean mResultDataBean;

    private View baseV;
    private String orderNum;
    private boolean isOpened = false;

    private boolean animatorLock = false;

    public MyAffair_repair_objs(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_repair_objs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_repair_objs(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_repair_objs, this);
        ButterKnife.bind(this);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        edit = (Button) baseV.findViewById(R.id.repair_objs_edit);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(2, isOpened);
                    EventBus.getDefault().post(msg);
                }

            }
        });
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RepairObjectsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("objs", mResultDataBean);
            intent.putExtras(bundle);
            intent.putExtra("oid", orderNum);
            mContext.startActivity(intent);
            edit.setClickable(false);
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

    public void setInfo(RepairObjsBean.ResultDataBean bean, int status, String num) throws NullPointerException {
        edit.setClickable(true);
        orderNum = num;
        mResultDataBean = bean;
        if (status == 1) {
            edit.setVisibility(GONE);
        }
        if (bean.getTcList().size() == 0 && bean.getXmList().size() == 0) {
            mLlTaocan.setVisibility(GONE);
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mRvTaocan.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
            mRvTaocan.setLayoutManager(layoutManager);
            mRepairObjLeftAdapter = new RepairObjLeftAdapter(bean.getTcList(), mContext);
            mRvTaocan.setAdapter(mRepairObjLeftAdapter);

            RepairObjsDownAdapter repairObjsDownAdapter = new RepairObjsDownAdapter(bean.getXmList());
//            mRvRepairObjs.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
            mRvRepairObjs.setLayoutManager(new LinearLayoutManager(mContext));
            mRvRepairObjs.setAdapter(repairObjsDownAdapter);
        }
    }

    public void Payed() {
        edit.setVisibility(GONE);
    }

}