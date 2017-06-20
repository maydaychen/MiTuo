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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);
        ButterKnife.bind(this);
        mToolbar2.setTitle("找回密码");
        setSupportActionBar(mToolbar2);
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
                if (flag && !mEtTelephone.getText().toString().equals("")) {
                    flag = false;
                    handler.post(runnable);
                } else {
                    Toast.makeText(this, "请填写手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_next:
                startActivity(new Intent(FindPassActivity.this, FindPass2Activity.class));
                break;
        }
    }
}
