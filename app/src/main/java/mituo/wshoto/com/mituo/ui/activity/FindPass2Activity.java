package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
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
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

public class FindPass2Activity extends AppCompatActivity {
    private SubscriberOnNextListener<ResultBean> getLatestOnNext;
    @BindView(R.id.toolbar2)
    Toolbar mToolbar2;
    @BindView(R.id.et_telephone)
    EditText mEtTelephone;
    @BindView(R.id.et_num)
    EditText mEtNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass2);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar2);

        getLatestOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                startActivity(new Intent(FindPass2Activity.this, ChangePassSuccessActivity.class));
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick({R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                if (!mEtTelephone.getText().toString().equals(mEtNum.getText().toString())) {
                    Toast.makeText(this, "密码请输入一致！", Toast.LENGTH_SHORT).show();
                } else {
                    HttpMethods.getInstance().savePass(
                            new ProgressSubscriber<>(getLatestOnNext, this),
                            getIntent().getStringExtra("phone"), mEtNum.getText().toString());
                }
                break;
        }
    }
}
