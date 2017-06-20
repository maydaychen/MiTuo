package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;

public class ChangePassActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar;
    @BindView(R.id.et_telephone)
    EditText mEtTelephone;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.editText4)
    EditText mEditText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        mToolbar.setTitle("修改密码");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @OnClick({R.id.bt_back, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_next:
                startActivity(new Intent(ChangePassActivity.this,ChangePassSuccessActivity.class));
                break;
        }
    }
}
