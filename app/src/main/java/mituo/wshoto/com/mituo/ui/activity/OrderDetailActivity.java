package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.OrderInfoBean;
import mituo.wshoto.com.mituo.bean.PayStatusBean;
import mituo.wshoto.com.mituo.bean.PicBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.bean.TimeBean;
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

import static mituo.wshoto.com.mituo.Utils.logout;

public class OrderDetailActivity extends InitActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private SubscriberOnNextListener<OrderInfoBean> getOrderInfoOnNext;
    private SubscriberOnNextListener<CarInfoBean> getCarInfoOnNext;
    private SubscriberOnNextListener<RepairObjsBean> getObjsOnNext;
    private SubscriberOnNextListener<ReportBean> getChecksOnNext;
    private SubscriberOnNextListener<GatherBean> gatherOnNext;
    private SubscriberOnNextListener<PicBean> picOnNext;
    private SubscriberOnNextListener<ResultBean> finishOnNext;
    private SubscriberOnNextListener<ResultBean> UploadPicOnNext;
    private SubscriberOnNextListener<PayStatusBean> checkPayOnNext;
    private SubscriberOnNextListener<TimeBean> timeOnNext;
    private SubscriberOnNextListener<ResultBean> startOnNext;
    private SubscriberOnNextListener<ResultBean> endOnNext;

    private int num = 1;
    private int status;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Bitmap mBitmap;
    private String path;

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
    @BindView(R.id.tv_finish_order)
    TextView mTvFinishOrder;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        status = getIntent().getIntExtra("status", 0);
        if (status == 0) {
            mTvTitle.setText("订单明细（进行中）");
        } else {
            mTvTitle.setText("订单明细");
            mTvFinishOrder.setVisibility(View.GONE);
        }
        setSupportActionBar(mTbOrder);

    }

    @Override
    public void initData() {
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        path = preferences.getString("path", Config.PATH_MOBILE);
        mTbOrder.setNavigationIcon(R.drawable.nav_back);
        mTbOrder.setNavigationOnClickListener(v -> finish());
        getOrderInfoOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mDetail.setInfo(resultBean.getResultData().getOrderCode(), resultBean.getResultData().getYyTime(),
                        resultBean.getResultData().getYyAddress());
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getCarInfoOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mCarInfo.setInfo(resultBean.getResultData(), status, getIntent().getStringExtra("oid"));
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getObjsOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mRepairObjs.setInfo(resultBean.getResultData(), status, getIntent().getStringExtra("oid"));
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mGather.setInfo(resultBean.getResultData(), status, getIntent().getStringExtra("oid"));
                HttpMethods.getInstance().check_pay(
                        new ProgressSubscriber<>(checkPayOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        picOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mRepairPhotos.setInfo(status, resultBean);
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        finishOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                finish();
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        getChecksOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mCheckReport.setInfo(status, resultBean, getIntent().getStringExtra("oid"));
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        checkPayOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mGather.setRemind(resultBean);
                if (resultBean.getResultData().isPayStatus()) {
                    mRepairObjs.Payed();
                }
            }
        };
        timeOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                long time = System.currentTimeMillis();
                SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("sys_time", resultBean.getResultData().getTimeStamp() - time);
                editor.apply();
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        UploadPicOnNext = resultBean -> {
            if (resultBean.isResult()) {
                mRepairPhotos.addPic(Utils.bitmaptoString(mBitmap), path + "/" + getIntent().getStringExtra("oid") + num++ + ".png");
            } else if (resultBean.getCode().equals("401")) {
                logout(OrderDetailActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        startOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                editor.putBoolean(getIntent().getStringExtra("oid") + "start", true);
                if (editor.commit()) {
                    HttpMethods.getInstance().end_record(
                            new ProgressSubscriber<>(endOnNext, this), preferences.getString("token", ""),
                            getIntent().getStringExtra("oid"), preferences.getString(getIntent().getStringExtra("oid") + "endTime", ""));
                }
            } else {
                editor.putBoolean(getIntent().getStringExtra("oid") + "start", false);
                editor.apply();
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        endOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                editor.putBoolean(getIntent().getStringExtra("oid") + "end", true);
                if (editor.commit()) {
                    HttpMethods.getInstance().finish_order(
                            new ProgressSubscriber<>(finishOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
                }
            } else {
                editor.putBoolean(getIntent().getStringExtra("oid") + "end", false);
                editor.apply();
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        HttpMethods.getInstance().get_pic(
                new ProgressSubscriber<>(picOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理结果
        if (requestCode == 10) {
            try {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    mBitmap = extras.getParcelable("data");
                    HttpMethods.getInstance().upload_img(
                            new ProgressSubscriber<>(UploadPicOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"),
                            Utils.bitmaptoString(mBitmap), path + "/" + getIntent().getStringExtra("oid") + num++ + ".png");
                }
            } catch (NullPointerException ignored) {

            }
        }
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
                case 10:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 10);

                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        mGather.INFO_OK = false;
        mGather.CHECK_OK = false;
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        Config.mBean = new ArrayList<>();
        mRepairVideo.setInfo(status, getIntent().getStringExtra("oid"));
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
//                "BY20170622000");
        HttpMethods.getInstance().get_time(
                new ProgressSubscriber<>(timeOnNext, this), preferences.getString("token", ""));

    }


    @OnClick(R.id.tv_finish_order)
    public void onViewClicked() {
        if (mCarInfo.IS_OK) {
            if (mRepairVideo.IS_OK) {
                if (mCheckReport.IS_OK) {
                    if (mGather.IS_OK) {
                        if (!preferences.getBoolean(getIntent().getStringExtra("oid") + "start", false)) {
                            HttpMethods.getInstance().start_record(
                                    new ProgressSubscriber<>(startOnNext, this), preferences.getString("token", ""),
                                    getIntent().getStringExtra("oid"), preferences.getString(getIntent().getStringExtra("oid") + "startTime", ""));
                        } else {
                            if (!preferences.getBoolean(getIntent().getStringExtra("oid") + "end", false)) {
                                HttpMethods.getInstance().end_record(
                                        new ProgressSubscriber<>(endOnNext, this), preferences.getString("token", ""),
                                        getIntent().getStringExtra("oid"), preferences.getString(getIntent().getStringExtra("oid") + "endTime", ""));
                            } else {
                                HttpMethods.getInstance().finish_order(
                                        new ProgressSubscriber<>(finishOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
                            }
                        }
                    } else {
                        Toast.makeText(this, "尚未付款", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(this, "检测报告尚未填写", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(this, "尚未录制维修录像", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "请补完车辆信息", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
