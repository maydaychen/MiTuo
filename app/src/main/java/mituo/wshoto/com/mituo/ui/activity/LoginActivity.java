package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.MainActivity;
import mituo.wshoto.com.mituo.R;

import static mituo.wshoto.com.mituo.Utils.isNetworkAvailable;

public class LoginActivity extends InitActivity {


    @BindView(R.id.tv_password)
    AutoCompleteTextView mTvPassword;
    @BindView(R.id.email)
    AutoCompleteTextView mEmail;
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
    }

    @Override
    public void initData() {

        RxTextView.textChanges(mEmail).subscribe(charSequence -> {
            if (!charSequence.toString().equals("")) {
                mTvPhoneMsg.setVisibility(View.INVISIBLE);
            }

        });

    }

    @OnClick({R.id.bt_login, R.id.tv_login_changepass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if (mTvPassword.getText().toString().equals("")){
                    mTvPhoneMsg.setVisibility(View.VISIBLE);
                } else if (mEmail.getText().toString().equals("")){
                    mTvPassworMsg.setVisibility(View.VISIBLE);
                }else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                break;
            case R.id.tv_login_changepass:
                startActivity(new Intent(LoginActivity.this, FindPassActivity.class));
                break;
        }
    }

}
