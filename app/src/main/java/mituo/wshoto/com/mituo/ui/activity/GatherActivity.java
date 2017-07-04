package mituo.wshoto.com.mituo.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.GatherAdapter;
import mituo.wshoto.com.mituo.bean.CouponBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

import static mituo.wshoto.com.mituo.Utils.bitmaptoString;

public class GatherActivity extends InitActivity {
    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.cash_pay)
    Button mCashPay;
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
    private SubscriberOnNextListener<ResultBean> GatherOnNext;
    private SharedPreferences preferences;
    private Bitmap mBitmap;
    private String coupon = "";
    private String payType = "0";

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gather);
        ButterKnife.bind(this);
        mToolbar.setTitle("结算清单");
        setSupportActionBar(mToolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        payType = "0";
                        mCashPay.setVisibility(View.VISIBLE);
                        mLlMobilePay.setVisibility(View.GONE);
                        break;
                    case 1:
                        payType = "2";
                        mCashPay.setVisibility(View.GONE);
                        mLlMobilePay.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        payType = "1";
                        mCashPay.setVisibility(View.GONE);
                        mLlMobilePay.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        CheckCounOnNext = resultBean -> {
            if (resultBean.isResult()) {
                String a = "该代金券价值" + resultBean.getResultData().getCouponPrice() + "元，仅限一次使用，是否确认使用？";
                show(a);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        GatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(this, "支付成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };


    }

    @Override
    public void initData() {
        mResultBean = (GatherBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        List<Map<String, String>> list = new ArrayList<>();
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvGather.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvGather.setLayoutManager(layoutManager);
        gatherAdapter = new GatherAdapter(list);
        mRvGather.setAdapter(gatherAdapter);
        mTvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qm.png";
        switch (resultCode) {
            case RESULT_OK:
                mEtNum.setText(data.getStringExtra("code"));
                break;
            case 100:
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bm = BitmapFactory.decodeFile(path, options);
                mBitmap = bm;
                mImageView2.setImageBitmap(bm);
                break;
        }

    }

    @OnClick({R.id.imageView2, R.id.button4, R.id.iv_gather_saoyisao, R.id.cash_pay, R.id.bt_mobile_pay_1, R.id.bt_mobile_pay_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
                startActivityForResult(new Intent(GatherActivity.this, HandWriteActivity.class), 1);
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
            case R.id.cash_pay:
                HttpMethods.getInstance().save_pay(
                        new ProgressSubscriber<>(GatherOnNext, this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"), mResultBean.getHj(),coupon ,payType , bitmaptoString(mBitmap));
                break;
            case R.id.bt_mobile_pay_1:
                break;
            case R.id.bt_mobile_pay_2:
                break;
        }
    }

    public void show(String context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(context);
        builder.setTitle("提示");

        builder.setPositiveButton("确认", (dialog, which) -> {

        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

}
