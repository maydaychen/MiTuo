package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.ui.activity.CarInfoActivity;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_car_info extends RelativeLayout {

    @BindView(R.id.owner_name)
    TextView mOwnerName;
    @BindView(R.id.owner_telephone)
    TextView mOwnerTelephone;
    @BindView(R.id.text2)
    TextView mText2;
    @BindView(R.id.car_type)
    TextView car_type;
    @BindView(R.id.engine_num)
    TextView mEngineNum;
    @BindView(R.id.total_mileage)
    TextView mTotalMileage;
    @BindView(R.id.next_care_time)
    TextView mNextCareTime;
    @BindView(R.id.next_care_mileage)
    TextView mNextCareMileage;
    @BindView(R.id.tv_car_model_num)
    TextView mTvCarModelNum;
    private Context mContext;

    private RelativeLayout title;
    private ImageView iv_remind;
    private TextView tv_remind;
    private LinearLayout infoRl;
    private CarInfoBean.ResultDataBean mResultDataBean;
    private ImageView directionIv;
    private Button edit;
    private View baseV;
    private String orderNum;

    private boolean isOpened = false;

    private boolean animatorLock = false;

    public MyAffair_car_info(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_car_info(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_car_info(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_car_info, this);
        ButterKnife.bind(this);
        title = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        directionIv = (ImageView) baseV.findViewById(R.id.affair_direction_iv);
        iv_remind = (ImageView) baseV.findViewById(R.id.iv_remind);
        tv_remind = (TextView) baseV.findViewById(R.id.tv_remind);
        edit = (Button) baseV.findViewById(R.id.car_info_edit);

//        directionIv.setBackgroundResource(R.drawable.p7_2_001);
        title.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(1, isOpened);
                    EventBus.getDefault().post(msg);
                }
            }
        });
        edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CarInfoActivity.class);
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

    public void setInfo(CarInfoBean.ResultDataBean resultBean, int status, String num) {
        orderNum = num;
        mResultDataBean = resultBean;
        mOwnerName.setText(String.format(getResources().getString(R.string.car_onwer_edit), resultBean.getContactName()));
        mOwnerTelephone.setText(String.format(getResources().getString(R.string.telephone_edit), resultBean.getContactPhone()));
        mText2.setText(String.format(getResources().getString(R.string.card_num_edit), resultBean.getCarNo()));
        car_type.setText(String.format(getResources().getString(R.string.car_type_edit), resultBean.getCarXh()));
        mEngineNum.setText(String.format(getResources().getString(R.string.engine_num_edit), resultBean.getCarFdjbh()));
        mTotalMileage.setText(String.format(getResources().getString(R.string.mileage_edit), resultBean.getCarXslc()));
        mNextCareTime.setText(String.format(getResources().getString(R.string.next_care_time_edit), resultBean.getXcbyDate()));
        mNextCareMileage.setText(String.format(getResources().getString(R.string.next_care_km_edit), resultBean.getXcbylc()));
        mTvCarModelNum.setText(String.format(getResources().getString(R.string.car_num_edit), resultBean.getCarCjh()));


        SpannableStringBuilder builder1 = new SpannableStringBuilder(mOwnerName.getText().toString());
        SpannableStringBuilder builder2 = new SpannableStringBuilder(mOwnerTelephone.getText().toString());
        SpannableStringBuilder builder3 = new SpannableStringBuilder(mText2.getText().toString());
        SpannableStringBuilder builder4 = new SpannableStringBuilder(car_type.getText().toString());
        SpannableStringBuilder builder5 = new SpannableStringBuilder(mEngineNum.getText().toString());
        SpannableStringBuilder builder6 = new SpannableStringBuilder(mTotalMileage.getText().toString());
        SpannableStringBuilder builder7 = new SpannableStringBuilder(mNextCareTime.getText().toString());
        SpannableStringBuilder builder8 = new SpannableStringBuilder(mNextCareMileage.getText().toString());
        SpannableStringBuilder builder9 = new SpannableStringBuilder(mTvCarModelNum.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.font_99));
        builder1.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder2.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder3.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder4.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder5.setSpan(redSpan, 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder6.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder7.setSpan(redSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder8.setSpan(redSpan, 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder9.setSpan(redSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mOwnerName.setText(builder1);
        mOwnerTelephone.setText(builder2);
        mText2.setText(builder3);
        car_type.setText(builder4);
        mEngineNum.setText(builder5);
        mTotalMileage.setText(builder6);
        mNextCareTime.setText(builder7);
        mNextCareMileage.setText(builder8);
        mTvCarModelNum.setText(builder9);
        if (status == 1) {
            edit.setVisibility(GONE);
        }

    }

}
