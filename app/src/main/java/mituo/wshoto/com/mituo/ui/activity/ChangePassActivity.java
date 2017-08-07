package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Utils.logout;

public class ChangePassActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar;
    @BindView(R.id.et_old_pass)
    AutoCompleteTextView mOldPass;
    @BindView(R.id.et_new_pass)
    AutoCompleteTextView mNewPass;
    @BindView(R.id.et_new_pass_again)
    AutoCompleteTextView mNewPass2;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.textView3)
    TextView mTextView3;

    private SubscriberOnNextListener<ResultBean> ChangePassOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        ChangePassOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                startActivity(new Intent(ChangePassActivity.this, ChangePassSuccessActivity.class));
            } else if (resultBean.getCode().equals("401")) {
                logout(ChangePassActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
        RxTextView.textChanges(mOldPass).subscribe(charSequence -> mTextView.setText(""));
        RxTextView.textChanges(mNewPass).subscribe(charSequence -> mTextView2.setText(""));
        RxTextView.textChanges(mNewPass2).subscribe(charSequence -> mTextView3.setText(""));
    }

    @OnClick({R.id.bt_next})
    public void onViewClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        switch (view.getId()) {
            case R.id.bt_next:
                if (mOldPass.getText().toString().equals("")) {
                    Toast.makeText(this, "请输入旧密码！", Toast.LENGTH_SHORT).show();
                    mTextView.setText("请输入旧密码");
                }else {
                    if (mNewPass.getText().toString().equals("") || mNewPass2.getText().toString().equals("")) {
                        Toast.makeText(this, "请填写新密码！", Toast.LENGTH_SHORT).show();
                        mTextView2.setText("请输入新密码");
                    } else {
                        if (mNewPass.getText().toString().equals(mNewPass2.getText().toString())) {
                            HttpMethods.getInstance().change_pass(
                                    new ProgressSubscriber<>(ChangePassOnNext, this), preferences.getString("token", ""), mOldPass.getText().toString(), mNewPass.getText().toString());
                        } else {
                            mTextView3.setText("两次密码输入不一致");
                            Toast.makeText(this, "两次密码请输入一致！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }
}
