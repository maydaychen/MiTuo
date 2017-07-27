package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.EmsBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Utils.isChinaPhoneLegal;

public class FindPassActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar2;
    @BindView(R.id.et_telephone)
    EditText mEtTelephone;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.button3)
    Button mButton3;
    private boolean flag = true;
    private int recLen = 60;
    private SubscriberOnNextListener<EmsBean> getLatestOnNext;
    private String ems, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar2);
        mToolbar2.setNavigationIcon(R.drawable.nav_back);
        mToolbar2.setNavigationOnClickListener(v -> finish());

        getLatestOnNext = resultBean -> {
            ems = resultBean.getResultData().getVcode() + "";
        };
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen >= 1) {
                recLen--;
                mButton3.setBackgroundResource(R.drawable.boder_cc);
                mButton3.setTextColor(getResources().getColor(R.color.font_99));
                mButton3.setText("重新获取(" + recLen + "s)");
                handler.postDelayed(this, 1000);
            } else {
                flag = true;
                recLen = 60;
                mButton3.setBackgroundResource(R.drawable.boder_yellow);
                mButton3.setTextColor(getResources().getColor(R.color.font_33));
                mButton3.setText("获取验证码");
            }
        }
    };


    @OnClick({R.id.button3, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                phone = mEtTelephone.getText().toString();
                if (flag && !mEtTelephone.getText().toString().equals("") && isChinaPhoneLegal(mEtTelephone.getText().toString())) {
                    flag = false;
                    handler.post(runnable);
                    HttpMethods.getInstance().getEms(
                            new ProgressSubscriber<>(getLatestOnNext, this), mEtTelephone.getText().toString());
                } else {
                    Toast.makeText(this, "请填写正确手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_next:
                if (mEtNum.getText().toString().equals(ems)) {
                    Intent intent = new Intent(FindPassActivity.this, FindPass2Activity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "验证码输入有误！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
