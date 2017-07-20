package mituo.wshoto.com.mituo.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.adapter.GatherAdapter;
import mituo.wshoto.com.mituo.bean.CouponBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.bean.WeixinBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

import static mituo.wshoto.com.mituo.Utils.logout;

public class GatherActivity extends InitActivity {
    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.ll_mobile_pay)
    LinearLayout mLlMobilePay;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.rv_gather)
    RecyclerView mRvGather;
    @BindView(R.id.tv_gather_money)
    TextView mTvGatherMoney;

    private GatherBean.ResultDataBean mResultBean;
    private static final int SHOW_SUBACTIVITY = 1;
    private SubscriberOnNextListener<CouponBean> CheckCounOnNext;
    private SubscriberOnNextListener<WeixinBean> GatherOnNext;
    private SubscriberOnNextListener<ResultBean> SavePayOnNext;
    private SubscriberOnNextListener<ResultBean> SavePay2OnNext;
    private SharedPreferences preferences;
    private Bitmap mBitmap;
    private String coupon = "";
    private List<String> couponList = new ArrayList<>();
    //pay_type:0,现金；1，支付宝，2，微信
    private String payType = "2";
    private CouponBean mCouponBean;
    List<Map<String, String>> list = new ArrayList<>();
    GatherAdapter gatherAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gather);
        ButterKnife.bind(this);
        mToolbar.setTitle("结算清单");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        payType = "2";
                        break;
                    case 1:
                        payType = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        CheckCounOnNext = resultBean -> {
            mCouponBean = resultBean;
            if (resultBean.getCode().equals("200")) {
                show(resultBean.getResultData().getCouponPrice());
            } else if (resultBean.getCode().equals("401")) {
                logout(GatherActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        SavePayOnNext = resultBean -> {
            if (resultBean.isResult()) {
                Intent intent = new Intent(GatherActivity.this, SaoPayActivity.class);
                intent.putExtra("PAY_TYPE", payType);
                intent.putExtra("sum", mResultBean.getHj());
                intent.putExtra("oid", getIntent().getStringExtra("oid"));
                startActivity(intent);
            } else if (resultBean.getCode().equals("401")) {
                logout(GatherActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        SavePay2OnNext = resultBean -> {
            if (resultBean.isResult()) {
                Intent payIntent = new Intent(GatherActivity.this, CaptureActivity.class);
                startActivityForResult(payIntent, 2);
            } else if (resultBean.getCode().equals("401")) {
                logout(GatherActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        GatherOnNext = resultBean -> {
            if (resultBean.isResult()) {
                Toast.makeText(this, "支付成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };


    }

    @Override
    public void initData() {
        mResultBean = (GatherBean.ResultDataBean) getIntent().getSerializableExtra("objs");

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvGather.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvGather.setLayoutManager(layoutManager);
        gatherAdapter = new GatherAdapter(list);
        mRvGather.setAdapter(gatherAdapter);
        mTvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            try {
                String code = data.getStringExtra("code");
                if (payType.equals("2")) {
                    HttpMethods.getInstance().wechat_pay(
                            new ProgressSubscriber<>(GatherOnNext, this), preferences.getString("token", ""),
                            getIntent().getStringExtra("oid"), code);
//                        "BY20170622000", code);
                } else {
                    HttpMethods.getInstance().ali_pay(
                            new ProgressSubscriber<>(GatherOnNext, this), preferences.getString("token", ""),
                            getIntent().getStringExtra("oid"), code);
//                        "BY20170622000", code);
                }
                return;
            } catch (NullPointerException ignored) {

            }
        }
        switch (resultCode) {
            case RESULT_OK:
                mEtNum.setText(data.getStringExtra("code"));
                break;
            case 100:
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qm.png";
                String path = Config.PATH_MOBILE + "/" + getIntent().getStringExtra("oid") + ".png";
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(path, options);
                mBitmap = bm;
                mImageView2.setImageBitmap(bm);
                mLlMobilePay.setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick({R.id.imageView2, R.id.button4, R.id.iv_gather_saoyisao, R.id.bt_mobile_pay_1, R.id.bt_mobile_pay_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                Intent handIntent = new Intent(GatherActivity.this, HandWriteActivity.class);
                handIntent.putExtra("oid", getIntent().getStringExtra("oid"));
                startActivityForResult(handIntent, 1);
                break;
            case R.id.button4:
                if (mEtNum.getText().length() == 0) {
                    Toast.makeText(this, "请填写代金券券码！", Toast.LENGTH_SHORT).show();
                } else {
                    HttpMethods.getInstance().check_coupon(
                            new ProgressSubscriber<>(CheckCounOnNext, this), preferences.getString("token", ""),
                            getIntent().getStringExtra("oid"), mEtNum.getText().toString());
                }
                break;
            case R.id.iv_gather_saoyisao:
                Intent intent10 = new Intent(GatherActivity.this, CaptureActivity.class);
                startActivityForResult(intent10, SHOW_SUBACTIVITY);
                break;
            case R.id.bt_mobile_pay_1:
                String test = Utils.bitmaptoString(mBitmap);
                HttpMethods.getInstance().save_pay(
                        new ProgressSubscriber<>(SavePayOnNext, this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"), mResultBean.getHj(), coupon, payType, Utils.bitmaptoString(mBitmap));
                break;
            case R.id.bt_mobile_pay_2:
                HttpMethods.getInstance().save_pay(
                        new ProgressSubscriber<>(SavePay2OnNext, this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"), mResultBean.getHj(), coupon, payType, Utils.bitmaptoString(mBitmap));
                break;
        }
    }

    public void show(String context) {
        String a = "该代金券价值" + context + "元，仅限一次使用，是否确认使用？";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(a);
        builder.setTitle("提示");

        builder.setPositiveButton("确认", (dialog, which) -> {
            if (Double.valueOf(mResultBean.getHj()) - Double.valueOf(context) < 0.00) {
                Toast.makeText(this, "优惠金额不能大于支付金额！", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }
            mResultBean.setHj(Double.valueOf(mResultBean.getHj()) - Double.valueOf(context) + "");
            couponList.add(mEtNum.getText().toString());
            mTvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "优惠券减免");
            map.put("peijian", mEtNum.getText().toString());
            map.put("num", "1");
            map.put("price", "-" + context);
            list.add(map);
            gatherAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

}
