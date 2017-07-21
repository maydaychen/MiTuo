package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.MainActivity;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.LoginBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Utils.isNetworkAvailable;

public class LoginActivity extends InitActivity {
    @BindView(R.id.cb_auto_log)
    CheckBox mCbAutoLog;
    private SubscriberOnNextListener<LoginBean> getLatestOnNext;

    @BindView(R.id.tv_password)
    AutoCompleteTextView mTvPassword;
    @BindView(R.id.phone)
    AutoCompleteTextView mPhone;
    @BindView(R.id.tv_phone_msg)
    TextView mTvPhoneMsg;
    @BindView(R.id.tv_passwor_msg)
    TextView mTvPassworMsg;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "无网络连接，请检查网络", Toast.LENGTH_SHORT).show();
        }

        getLatestOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token", resultBean.getResultData().getToken());
                editor.putString("name", resultBean.getResultData().getName());
                editor.putString("telephone", resultBean.getResultData().getPhone());
                editor.putBoolean("autoLog", mCbAutoLog.isChecked());
                editor.apply();
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        RxTextView.textChanges(mPhone).subscribe(charSequence -> {
            if (!charSequence.toString().equals("")) {
                mTvPhoneMsg.setVisibility(View.INVISIBLE);
            }
        });

    }

    @OnClick({R.id.bt_login, R.id.tv_login_changepass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (mPhone.getText().toString().equals("")) {
                    mTvPhoneMsg.setVisibility(View.VISIBLE);
                } else if (mTvPassword.getText().toString().equals("")) {
                    mTvPassworMsg.setVisibility(View.VISIBLE);
                } else {
                    HttpMethods.getInstance().login(
                            new ProgressSubscriber<>(getLatestOnNext, this), mPhone.getText().toString(),
                            mTvPassword.getText().toString());
                }

//                int i = 100;
//                for (int i1 = 0; i1 < i; i1++) {
//                    Intent intent = new Intent("auto.click");
//                    intent.putExtra("flag", 1);
//                    intent.putExtra("id", "bt_login");
//
//                    sendBroadcast(intent);
//                    mTvPassword.setText(i + "");
//                }
                break;
            case R.id.tv_login_changepass:
                startActivity(new Intent(LoginActivity.this, FindPassActivity.class));
                break;
        }
    }

}
