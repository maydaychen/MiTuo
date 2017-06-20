package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;

public class RepairObjectsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_objects);
        ButterKnife.bind(this);
        mToolbar.setTitle("维修项目");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }
}
