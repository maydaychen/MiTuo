package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.OrderInfoBean;
import mituo.wshoto.com.mituo.bean.PicBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_car_info;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_check_report;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_detail;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_gather;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repair_objs;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repair_photos;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repair_video;

public class OrderDetailActivity extends InitActivity {
    private SubscriberOnNextListener<OrderInfoBean> getOrderInfoOnNext;
    private SubscriberOnNextListener<CarInfoBean> getCarInfoOnNext;
    private SubscriberOnNextListener<RepairObjsBean> getObjsOnNext;
    private SubscriberOnNextListener<ReportBean> getChecksOnNext;
    private SubscriberOnNextListener<GatherBean> gatherOnNext;
    private SubscriberOnNextListener<PicBean> picOnNext;
    private SubscriberOnNextListener<ResultBean> finishOnNext;

    private int status;
    private SharedPreferences preferences;

    @BindView(R.id.tb_order)
    Toolbar mTbOrder;
    @BindView(R.id.detail)
    MyAffair_detail mDetail;
    @BindView(R.id.car_info)
    MyAffair_car_info mCarInfo;
    @BindView(R.id.repair_objs)
    MyAffair_repair_objs mRepairObjs;
    @BindView(R.id.repair_video)
    MyAffair_repair_video mRepairVideo;
    @BindView(R.id.repair_photos)
    MyAffair_repair_photos mRepairPhotos;
    @BindView(R.id.check_report)
    MyAffair_check_report mCheckReport;
    @BindView(R.id.gather)
    MyAffair_gather mGather;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        status = getIntent().getIntExtra("status", 0);
        if (status == 0) {
            mTbOrder.setTitle("订单明细（进行中）");
        } else {
            mTbOrder.setTitle("订单明细");
        }
        setSupportActionBar(mTbOrder);

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mTbOrder.setNavigationIcon(R.drawable.nav_back);
        mTbOrder.setNavigationOnClickListener(v -> finish());
        getOrderInfoOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mDetail.setInfo(resultBean.getResultData().getOrderCode(), resultBean.getResultData().getYyTime(),
                        resultBean.getResultData().getYyAddress());
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getCarInfoOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mCarInfo.setInfo(resultBean.getResultData(), status, getIntent().getStringExtra("oid"));
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getObjsOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mRepairObjs.setInfo(resultBean.getResultData(), status);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mGather.setInfo(resultBean.getResultData(), status, getIntent().getStringExtra("oid"));
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        picOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mRepairPhotos.setInfo(status,resultBean);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        finishOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                finish();
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getChecksOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                List<String> list = new ArrayList<>();
                for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : resultBean.getResultData().getStep2().getList()) {
                    list.add(listBean.getTypeName());
                }
                mCheckReport.setInfo(status, list);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };

        mRepairVideo.setInfo(status, getIntent().getStringExtra("oid"));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(OrderMessage loginInfoMessage) {
        if (!loginInfoMessage.isOpen) {
            switch (loginInfoMessage.type) {
                case 0:
                    mCarInfo.close().start();
                    mRepairObjs.close().start();
                    mRepairVideo.close().start();
                    mRepairPhotos.close().start();
                    mCheckReport.close().start();
                    mGather.close().start();
                    break;
                case 1:
                    mDetail.close().start();
                    mRepairObjs.close().start();
                    mRepairVideo.close().start();
                    mRepairPhotos.close().start();
                    mCheckReport.close().start();
                    mGather.close().start();
                    break;
                case 2:
                    mDetail.close().start();
                    mCarInfo.close().start();
                    mRepairVideo.close().start();
                    mRepairPhotos.close().start();
                    mCheckReport.close().start();
                    mGather.close().start();
                    break;
                case 3:
                    mDetail.close().start();
                    mCarInfo.close().start();
                    mRepairObjs.close().start();
                    mRepairPhotos.close().start();
                    mCheckReport.close().start();
                    mGather.close().start();
                    break;
                case 4:
                    mDetail.close().start();
                    mCarInfo.close().start();
                    mRepairVideo.close().start();
                    mRepairObjs.close().start();
                    mCheckReport.close().start();
                    mGather.close().start();
                    break;
                case 5:
                    mDetail.close().start();
                    mCarInfo.close().start();
                    mRepairVideo.close().start();
                    mRepairPhotos.close().start();
                    mRepairObjs.close().start();
                    mGather.close().start();
                    break;
                case 6:
                    mDetail.close().start();
                    mCarInfo.close().start();
                    mRepairVideo.close().start();
                    mRepairPhotos.close().start();
                    mCheckReport.close().start();
                    mRepairObjs.close().start();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        HttpMethods.getInstance().order_detail(
                new ProgressSubscriber<>(getOrderInfoOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
        HttpMethods.getInstance().car_info(
                new ProgressSubscriber<>(getCarInfoOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
        HttpMethods.getInstance().all_check(
                new ProgressSubscriber<>(getChecksOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
        HttpMethods.getInstance().repair_objs(
                new ProgressSubscriber<>(getObjsOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
        HttpMethods.getInstance().gather(
                new ProgressSubscriber<>(gatherOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
        HttpMethods.getInstance().get_pic(
                new ProgressSubscriber<>(picOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));

    }


    @OnClick(R.id.tv_finish_order)
    public void onViewClicked() {
        HttpMethods.getInstance().finish_order(
                new ProgressSubscriber<>(finishOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
    }
}
