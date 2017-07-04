package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.RepairObjsDetailAdapter;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;

public class RepairObjectsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

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

        RepairObjsDetailAdapter repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.setAdapter(repairObjsDetailAdapter);

        Toast.makeText(this, mResultDataBean.getTcList().get(0).getTcName(), Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
    }
}
