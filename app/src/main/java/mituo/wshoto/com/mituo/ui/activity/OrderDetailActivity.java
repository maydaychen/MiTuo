package mituo.wshoto.com.mituo.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_car_info;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_check_report;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_detail;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_gather;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repair_photos;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repair_video;
import mituo.wshoto.com.mituo.ui.widget.MyAffair_repaor_objs;

import static mituo.wshoto.com.mituo.R.id.detail;

public class OrderDetailActivity extends InitActivity {
    ArrayList<Object> list = new ArrayList<>();

    @BindView(R.id.tb_order)
    Toolbar mTbOrder;
    @BindView(detail)
    MyAffair_detail mDetail;
    @BindView(R.id.car_info)
    MyAffair_car_info mCarInfo;
    @BindView(R.id.repair_objs)
    MyAffair_repaor_objs mRepairObjs;
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
        mTbOrder.setTitle("订单明细（进行中）");
        setSupportActionBar(mTbOrder);

    }

    @Override
    public void initData() {
        mTbOrder.setNavigationIcon(R.drawable.nav_back);
        mTbOrder.setNavigationOnClickListener(v -> finish());
        mDetail.setInfo("hahah");
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
    }
}
