package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

public class ChangePassActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar;
    @BindView(R.id.et_old_pass)
    EditText mOldPass;
    @BindView(R.id.et_new_pass)
    EditText mNewPass;
    @BindView(R.id.et_new_pass_again)
    EditText mNewPass2;

    private SubscriberOnNextListener<RepairObjsBean> ChangePassOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        mToolbar.setTitle("修改密码");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        ChangePassOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                startActivity(new Intent(ChangePassActivity.this, ChangePassSuccessActivity.class));
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick({R.id.bt_next})
    public void onViewClicked(View view) {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        switch (view.getId()) {
            case R.id.bt_next:
                if (mNewPass.getText().toString().equals(mNewPass2.getText().toString())) {
                    HttpMethods.getInstance().change_pass(
                            new ProgressSubscriber<>(ChangePassOnNext, this), preferences.getString("token", ""), mOldPass.getText().toString(), mNewPass.getText().toString());
                }else {
                    Toast.makeText(this, "两次密码请输入一致！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
