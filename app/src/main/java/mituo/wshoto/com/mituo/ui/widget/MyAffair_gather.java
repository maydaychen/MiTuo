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
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.adapter.GatherAdapter;
import mituo.wshoto.com.mituo.bean.CouponDetailBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.PayStatusBean;
import mituo.wshoto.com.mituo.ui.activity.GatherActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_gather extends RelativeLayout {

    @BindView(R.id.rv_gather)
    RecyclerView rvGather;
    @BindView(R.id.tv_gather_money)
    TextView tvGatherMoney;
    @BindView(R.id.tv_gather_coupon)
    TextView mTvGatherCoupon;
    @BindView(R.id.ll_coupon)
    LinearLayout mLlCoupon;
    @BindView(R.id.tv_gather_real)
    TextView mTvGatherReal;
    @BindView(R.id.ll_real_pay)
    LinearLayout mLlRealPay;
    @BindView(R.id.tv_pay_kind)
    TextView mTvPayKind;
    @BindView(R.id.ll_pay_kind)
    LinearLayout mLlPayKind;
    @BindView(R.id.tv_pay_order)
    TextView mTvPayOrder;
    @BindView(R.id.ll_pay_order)
    LinearLayout mLlPayOrder;
    @BindView(R.id.iv_khqm)
    ImageView mIvKhqm;
    @BindView(R.id.ll_khqm)
    LinearLayout mLlKhqm;
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
    public boolean IS_OK = true;
    private List<CouponDetailBean> couponList = new ArrayList<>();

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
        mResultDataBean = mResultBean;
        if (status == 1) {
            edit.setVisibility(GONE);
        }
        tvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
        setDate();
    }

    public void setRemind(PayStatusBean payStatusBean) {
        if (payStatusBean.getResultData().getCouponCodeList().size() != 0) {
            couponList = new ArrayList<>();
            for (PayStatusBean.ResultDataBean.CouponCodeListBean couponCodeListBean : payStatusBean.getResultData().getCouponCodeList()) {
                CouponDetailBean couponDetailBean = new CouponDetailBean(couponCodeListBean.getCouponPrice(), couponCodeListBean.getCouponCode());
                couponList.add(couponDetailBean);
            }
            tvGatherMoney.setText(String.format(getResources().getString(R.string.money), payStatusBean.getResultData().getPaySum() + ""));
        }
        setDate();
        if (payStatusBean.getResultData().isPayStatus()) {
            setPayInfo(payStatusBean.getResultData());
            iv_remind.setVisibility(GONE);
            tv_remind.setVisibility(GONE);
            IS_OK = true;
        } else {
            iv_remind.setVisibility(VISIBLE);
            tv_remind.setVisibility(VISIBLE);
            IS_OK = false;
        }
    }

    public void setPayInfo(PayStatusBean.ResultDataBean mResultBean) {
        mTvGatherReal.setText(String.format(getResources().getString(R.string.money), mResultBean.getPaySum() + ""));
        mTvGatherCoupon.setText(mResultBean.getCouponPrice() + "");
        mTvPayKind.setText(mResultBean.getPayType().equals("1") ? "支付宝支付" : "微信支付");
        mTvPayOrder.setText(mResultBean.getPayCode());
        if (mResultBean.isPayStatus()) {
            edit.setVisibility(GONE);
        }
        mIvKhqm.setImageBitmap(Utils.stringtoBitmap(mResultBean.getKhqm()));
        mLlCoupon.setVisibility(VISIBLE);
        mLlRealPay.setVisibility(VISIBLE);
        mLlPayKind.setVisibility(VISIBLE);
        mLlPayOrder.setVisibility(VISIBLE);
        mLlKhqm.setVisibility(VISIBLE);
    }

    private void setDate() {
        List<Map<String, String>> list = new ArrayList<>();
        for (GatherBean.ResultDataBean.TcListBean tcListBean : mResultDataBean.getTcList()) {
            for (GatherBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : tcListBean.getTcxmList()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", tcxmListBean.getXmName());
                map.put("peijian", tcxmListBean.getPjpp() == null ? "--" : tcxmListBean.getPjName());
                map.put("num", tcxmListBean.getPjpp() == null ? "--" : tcxmListBean.getPjNum());
                map.put("price", tcxmListBean.getPjPrice());
                list.add(map);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "套餐减免费用");
            map.put("peijian", "--");
            map.put("num", "--");
            map.put("price", "-" + tcListBean.getTcJmfy());
            list.add(map);
        }
        for (GatherBean.ResultDataBean.XmListBean xmListBean : mResultDataBean.getXmList()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", xmListBean.getXmName());
            map.put("peijian", xmListBean.getPjName());
            map.put("num", xmListBean.getPjNum());
            map.put("price", xmListBean.getPjPrice());
            list.add(map);
        }
        if (null != mResultDataBean.getSmfwf() && !mResultDataBean.getSmfwf().equals("")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "上门服务费");
            map.put("peijian", "--");
            map.put("num", "--");
            map.put("price", mResultDataBean.getSmfwf());
            list.add(map);
        }
        if (null != mResultDataBean.getGsf() && !mResultDataBean.getGsf().equals("")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "工时费");
            map.put("peijian", "--");
            map.put("num", "--");
            map.put("price", mResultDataBean.getGsf());
            list.add(map);
        }
        if (couponList.size() != 0) {
            for (int i = 0; i < couponList.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "优惠券减免");
                map.put("peijian", couponList.get(i).getNum());
                map.put("num", "--");
                map.put("price", "-" + couponList.get(i).getMoney() + "");
                list.add(map);
            }
        }
        GatherAdapter gatherAdapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGather.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        rvGather.setLayoutManager(layoutManager);
        gatherAdapter = new GatherAdapter(list);
        rvGather.setAdapter(gatherAdapter);
    }

}