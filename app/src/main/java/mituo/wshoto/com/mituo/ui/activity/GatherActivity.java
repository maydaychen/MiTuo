package mituo.wshoto.com.mituo.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
import mituo.wshoto.com.mituo.bean.CouponDetailBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.PayStatusBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.bean.WeixinBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.LinePathView;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
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
    @BindView(R.id.ll_sum)
    LinearLayout mLlSum;

    private GatherBean.ResultDataBean mResultBean;
    private static final int SHOW_SUBACTIVITY = 1;
    private SubscriberOnNextListener<CouponBean> CheckCounOnNext;
    private SubscriberOnNextListener<WeixinBean> GatherOnNext;
    private SubscriberOnNextListener<ResultBean> SavePayOnNext;
    private SubscriberOnNextListener<ResultBean> SavePay2OnNext;
    private SharedPreferences preferences;
    private Bitmap mBitmap;
    private String coupon = "";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private List<CouponDetailBean> couponList = new ArrayList<>();
    //pay_type:0,现金；1，支付宝，2，微信
    private String payType = "2";
    List<Map<String, String>> list = new ArrayList<>();
    GatherAdapter gatherAdapter;
    private LinePathView mPathView;
    private PopupWindow popupWindow;
    private SubscriberOnNextListener<PayStatusBean> checkPayOnNext;
    private List<PayStatusBean.ResultDataBean.CouponCodeListBean> mCodeListBeen;
    private DecimalFormat df = new DecimalFormat("######0.00");

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gather);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        checkPayOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mCodeListBeen = resultBean.getResultData().getCouponCodeList();
                if (resultBean.getResultData().getKhqm() != null && !resultBean.getResultData().getKhqm().equals("")) {
                    mImageView2.setImageBitmap(Utils.stringtoBitmap(resultBean.getResultData().getKhqm()));
                    mBitmap = Utils.stringtoBitmap(resultBean.getResultData().getKhqm());
                    mLlMobilePay.setVisibility(View.VISIBLE);
                    couponList = new ArrayList<>();
                    mResultBean.setHj(resultBean.getResultData().getPaySum() + "");
                    coupon = "";
                    if (resultBean.getResultData().getCouponCodeList().size() != 0) {
                        for (PayStatusBean.ResultDataBean.CouponCodeListBean couponCodeListBean : resultBean.getResultData().getCouponCodeList()) {
                            CouponDetailBean couponDetailBean = new CouponDetailBean(couponCodeListBean.getCouponPrice(), couponCodeListBean.getCouponCode());
                            couponList.add(couponDetailBean);

                        }
                        mTvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
                    }
                    mLlMobilePay.setVisibility(View.VISIBLE);
                    setDate();
                }
            }
        };

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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
    protected void onResume() {
        super.onResume();
        HttpMethods.getInstance().check_pay(
                new ProgressSubscriber<>(checkPayOnNext, GatherActivity.this), preferences.getString("token", ""),
                getIntent().getStringExtra("oid"));
    }

    @Override
    public void initData() {
        mResultBean = (GatherBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        setDate();
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
//            case 100:
////                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qm.png";
//                String path = Config.PATH_MOBILE + "/" + getIntent().getStringExtra("oid") + ".png";
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inSampleSize = 2;
//                Bitmap bm = BitmapFactory.decodeFile(path, options);
//                mBitmap = bm;
//                mImageView2.setImageBitmap(bm);
//                mLlMobilePay.setVisibility(View.VISIBLE);
//                break;
        }

    }

    @OnClick({R.id.imageView2, R.id.button4, R.id.iv_gather_saoyisao, R.id.bt_mobile_pay_1, R.id.bt_mobile_pay_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView2:
//                Intent handIntent = new Intent(GatherActivity.this, HandWriteActivity.class);
//                handIntent.putExtra("oid", getIntent().getStringExtra("oid"));
//                startActivityForResult(handIntent, 1);
                showPopupWindow();
                break;
            case R.id.button4:
                if (mEtNum.getText().length() == 0) {
                    Toast.makeText(this, "请填写代金券券码！", Toast.LENGTH_SHORT).show();
                } else {
                    mEtNum.setText("");
                    if (couponList.contains(mEtNum.getText().toString())) {
                        Toast.makeText(this, "该券已使用", Toast.LENGTH_SHORT).show();
                    } else {
                        HttpMethods.getInstance().check_coupon(
                                new ProgressSubscriber<>(CheckCounOnNext, this), preferences.getString("token", ""),
                                getIntent().getStringExtra("oid"), mEtNum.getText().toString());
                    }
                }
                break;
            case R.id.iv_gather_saoyisao:
                Intent intent10 = new Intent(GatherActivity.this, CaptureActivity.class);
                startActivityForResult(intent10, SHOW_SUBACTIVITY);
                break;
            case R.id.bt_mobile_pay_1:
                if (couponList.size() != 0) {
                    for (CouponDetailBean couponDetailBean : couponList) {
                        coupon = coupon + couponDetailBean.getNum() + ",";
                    }
                    coupon = coupon.substring(0, coupon.length() - 1);
                }

                HttpMethods.getInstance().save_pay(
                        new ProgressSubscriber<>(SavePayOnNext, this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"), mResultBean.getHj(), coupon, payType, Utils.bitmaptoString(mBitmap));
                break;
            case R.id.bt_mobile_pay_2:
                if (couponList.size() != 0) {
                    for (CouponDetailBean couponDetailBean : couponList) {
                        coupon = coupon + couponDetailBean.getNum() + ",";
                    }
                    coupon = coupon.substring(0, coupon.length() - 1);
                }
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


            String sum = df.format(Float.valueOf(mResultBean.getHj()) - Float.valueOf(context));
            mResultBean.setHj(sum);

            CouponDetailBean couponDetailBean = new CouponDetailBean(Integer.valueOf(context), mEtNum.getText().toString());
            couponList.add(couponDetailBean);
            mTvGatherMoney.setText(String.format(getResources().getString(R.string.money), mResultBean.getHj()));
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "优惠券减免");
            map.put("peijian", mEtNum.getText().toString());
            map.put("num", "1");
            map.put("price", "-" + context);
            list.add(map);
            gatherAdapter.notifyDataSetChanged();
            mEtNum.setText("");
        });

        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
            mEtNum.setText("");
        });

        builder.create().show();
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_sign, null);

        mPathView = (LinePathView) contentView.findViewById(R.id.view);
        final TextView mClear = (TextView) contentView.findViewById(R.id.clear1);
        final TextView mSave = (TextView) contentView.findViewById(R.id.save1);
        final TextView back = (TextView) contentView.findViewById(R.id.back);
        popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
                true);
        mSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(
                            WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE, RECORD_AUDIO},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        save();
                    }
                } else {
                    save();
                }
            } else {
                Toast.makeText(GatherActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
            }
        });
        mClear.setOnClickListener(v -> mPathView.clear());
        back.setOnClickListener(view -> popupWindow.dismiss());

        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });


        popupWindow.showAsDropDown(GatherActivity.this.findViewById(R.id.ll_sum));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                save();
            } else {
                //用户不同意，自行处理即可
                finish();
            }
        }
    }

    private void save() {
        try {
            String path = preferences.getString("path", Config.PATH_MOBILE);
            popupWindow.dismiss();
            File destDir = new File(path);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
//            mPathView.save("/sdcard/qm.png", true, 10);
            mPathView.save(path + "/" + getIntent().getStringExtra("oid") + ".png", true, 10);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(path + "/" + getIntent().getStringExtra("oid") + ".png", options);
            mBitmap = bm;
            mImageView2.setImageBitmap(bm);
            mLlMobilePay.setVisibility(View.VISIBLE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDate() {
        list = new ArrayList<>();
        for (GatherBean.ResultDataBean.TcListBean tcListBean : mResultBean.getTcList()) {
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
        for (GatherBean.ResultDataBean.XmListBean xmListBean : mResultBean.getXmList()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", xmListBean.getXmName());
            map.put("peijian", xmListBean.getPjName());
            map.put("num", xmListBean.getPjNum());
            map.put("price", xmListBean.getPjPrice());
            list.add(map);
        }
        if (null != mResultBean.getSmfwf() && !mResultBean.getSmfwf().equals("")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "上门服务费");
            map.put("peijian", "--");
            map.put("num", "--");
            map.put("price", mResultBean.getSmfwf());
            list.add(map);
        }
        if (null != mResultBean.getGsf() && !mResultBean.getGsf().equals("")) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", "工时费");
            map.put("peijian", "--");
            map.put("num", "--");
            map.put("price", mResultBean.getGsf());
            list.add(map);
        }
        if (couponList.size() != 0) {
            for (int i = 0; i < mCodeListBeen.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", "优惠券减免");
                map.put("peijian", couponList.get(i).getNum());
                map.put("num", "--");
                map.put("price", "-" + couponList.get(i).getMoney() + "");
                list.add(map);
            }
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
    }
}
