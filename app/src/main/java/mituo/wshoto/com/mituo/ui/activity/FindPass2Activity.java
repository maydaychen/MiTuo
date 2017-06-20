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

public class FindPass2Activity extends AppCompatActivity {

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
        mToolbar2.setTitle("修改密码");
        setSupportActionBar(mToolbar2);
    }

    @OnClick({R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                startActivity(new Intent(FindPass2Activity.this,ChangePassSuccessActivity.class));
                break;
        }
    }
}
