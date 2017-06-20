package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;

public class GatherActivity extends AppCompatActivity {
    private static final int SHOW_SUBACTIVITY = 1;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gather);
        ButterKnife.bind(this);
        mToolbar.setTitle("结算清单");
        setSupportActionBar(mToolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos) {
                    case 0:
                        mCashPay.setVisibility(View.VISIBLE);
                        mLlMobilePay.setVisibility(View.GONE);
                        break;
                    case 1:
                        mCashPay.setVisibility(View.GONE);
                        mLlMobilePay.setVisibility(View.VISIBLE);
                        break;
                    case 2:
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

                }
                break;
            case R.id.iv_gather_saoyisao:
                Intent intent10 = new Intent(GatherActivity.this, CaptureActivity.class);
                startActivityForResult(intent10, SHOW_SUBACTIVITY);
                break;
            case R.id.cash_pay:
                break;
            case R.id.bt_mobile_pay_1:
                break;
            case R.id.bt_mobile_pay_2:
                break;
        }
    }

}
