package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.ReportStep1Adapter;
import mituo.wshoto.com.mituo.bean.ReportBean;

public class CheckReportActivity extends InitActivity {
    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView2)
    RecyclerView mRecyclerView2;

    private ReportBean mReportBean;
    private ReportStep1Adapter reportAdapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_check_report);
        ButterKnife.bind(this);
        mToolbar.setTitle("检测报告");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mReportBean = (ReportBean) getIntent().getSerializableExtra("objs");

        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter = new ReportStep1Adapter(mReportBean.getResultData().getStep1());
        mRecyclerView2.setAdapter(reportAdapter);
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.button2)
    public void onViewClicked() {
        List<ReportBean.ResultDataBean.Step1Bean> list = reportAdapter.getList();
        List<ReportBean.ResultDataBean.Step1Bean.ListBeanX> listX = new ArrayList<>();

        for (ReportBean.ResultDataBean.Step1Bean step1Bean : list) {
            for (ReportBean.ResultDataBean.Step1Bean.ListBeanX listBeanX : step1Bean.getList()) {
                listX.add(listBeanX);
            }
        }
        Intent intent = new Intent(CheckReportActivity.this, CheckReport2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objs", mReportBean);
        bundle.putSerializable("step1", (Serializable) listX);
        intent.putExtras(bundle);
        intent.putExtra("oid", getIntent().getStringExtra("oid"));
        startActivity(intent);
    }
}
