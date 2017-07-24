package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.PayStatusBean;
import mituo.wshoto.com.mituo.bean.WeixinBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

public class SaoPayActivity extends AppCompatActivity {

    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.iv_erweima)
    ImageView mIvErweima;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.iv_paytype)
    ImageView mIvPaytype;
    @BindView(R.id.tv_pay_message)
    TextView mTvPayMessage;

    private SubscriberOnNextListener<WeixinBean> gatherOnNext;
    private SubscriberOnNextListener<PayStatusBean> checkPayOnNext;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sao_pay);
        ButterKnife.bind(this);
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                HttpMethods.getInstance().check_pay(
                        new ProgressSubscriber<>(checkPayOnNext, SaoPayActivity.this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"));
                handler.postDelayed(this, 2000);
            }
        };

        mTvPrice.setText(String.format(getResources().getString(R.string.sao_money), getIntent().getStringExtra("sum")));
        checkPayOnNext = resultBean -> {
            if (resultBean.getResultData().isPayStatus()) {
                mIvErweima.setImageResource(R.drawable.msg_);
                mTvPayMessage.setText("支付成功!");
                handler.removeCallbacks(runnable);
                mIvPaytype.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Main WordPress Activity. */
                    Intent mainIntent = new Intent(SaoPayActivity.this, OrderDetailActivity.class);
                    mainIntent.putExtra("oid", getIntent().getStringExtra("oid"));
                    mainIntent.putExtra("status", 0);
                    startActivity(mainIntent);
                }, 1500);
            }
        };

        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Bitmap qrcodeBitmap = generateBitmap(resultBean.getResultData().getUrlCode(), 500, 500);
                mIvErweima.setImageBitmap(qrcodeBitmap);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };

        if (getIntent().getStringExtra("PAY_TYPE").equals("2")) {
            mIvPaytype.setImageResource(R.drawable.wechat);
            mTvPayType.setText("微信支付");
            mTvPayMessage.setText("微信支付");
            HttpMethods.getInstance().wechat_sao(
                    new ProgressSubscriber<>(gatherOnNext, this), preferences.getString("token", ""),
                    getIntent().getStringExtra("oid"));
//                    "BY20170622000");
        } else {
            mTvPayType.setText("支付宝支付");
            mTvPayMessage.setText("支付宝支付");
            mIvPaytype.setImageResource(R.drawable.ali);
            HttpMethods.getInstance().ali_sao(
                    new ProgressSubscriber<>(gatherOnNext, this), preferences.getString("token", ""),
                    getIntent().getStringExtra("oid"));
//                    "BY20170622000");
        }


    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
