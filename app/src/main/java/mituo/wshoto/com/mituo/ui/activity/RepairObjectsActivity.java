package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;

public class RepairObjectsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RepairObjsBean.ResultDataBean mResultDataBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_objects);
        ButterKnife.bind(this);
        mToolbar.setTitle("维修项目");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        mResultDataBean = (RepairObjsBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        Toast.makeText(this, mResultDataBean.getTcList().get(0).getTcName(), Toast.LENGTH_SHORT).show();

    }
}
