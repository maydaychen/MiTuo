package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;

public class CheckReport2Activity extends AppCompatActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report2);
        ButterKnife.bind(this);
        mToolbar.setTitle("检测报告");
        setSupportActionBar(mToolbar);
    }
}
