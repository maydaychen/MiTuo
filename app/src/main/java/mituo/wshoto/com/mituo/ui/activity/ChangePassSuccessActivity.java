package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;

public class ChangePassSuccessActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_success);
        ButterKnife.bind(this);
        mToolbar2.setTitle("修改密码");
        setSupportActionBar(mToolbar2);
    }

    @OnClick(R.id.bt_success_back)
    public void onViewClicked() {
        startActivity(new Intent(ChangePassSuccessActivity.this,LoginActivity.class));
    }
}
