package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.CheckMessage;
import mituo.wshoto.com.mituo.NullStringToEmptyAdapterFactory;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.ReportStep2Adapter;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Config.mBean;
import static mituo.wshoto.com.mituo.Utils.logout;

public class CheckReport2Activity extends AppCompatActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.order_tab)
    TabLayout mOrderTab;
    @BindView(R.id.rv_report)
    RecyclerView mRvReport;
    @BindView(R.id.button2)
    Button mButton2;

    private ReportBean mReportBean;

    private ReportStep2Adapter reportAdapter;
    private ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX> step1List;//所有step1值
    private SubscriberOnNextListener<ResultBean> gatherOnNext;
    private List<ReportBean.ResultDataBean.Step2Bean.ListBean> listX;//所有step2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report2);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mReportBean = (ReportBean) getIntent().getSerializableExtra("objs");
        step1List = (ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX>) getIntent().getSerializableExtra("step1");
        listX = new ArrayList<>();
        for (ReportBean.ResultDataBean.Step2Bean step2Bean : mReportBean.getResultData().getStep2()) {
            mOrderTab.addTab(mOrderTab.newTab().setText(step2Bean.getTypeName()));
            for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : step2Bean.getList()) {
                try{
                    if (listBean.getBgxmValue().equals("1")||listBean.getBgxmValue().equals("2")) {
                        mBean.add(listBean);
                    }else {
                        listBean.setBgxmValue("0");
                    }
                }catch (NullPointerException e){
                    listBean.setBgxmValue("0");
                }
                listX.add(listBean);
            }
        }
        mRvReport.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter = new ReportStep2Adapter(mReportBean.getResultData().getStep2().get(0).getList(), mBean);
        mRvReport.setAdapter(reportAdapter);

        mOrderTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBean = reportAdapter.getSelected();
                for (int i = 0; i < mReportBean.getResultData().getStep2().size(); i++) {
                    if (mReportBean.getResultData().getStep2().get(i).getTypeName().equals(tab.getText())) {
                        mRvReport.setLayoutManager(new LinearLayoutManager(CheckReport2Activity.this));
                        reportAdapter = new ReportStep2Adapter(mReportBean.getResultData().getStep2().get(i).getList(), mBean);
                        mRvReport.setAdapter(reportAdapter);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        if (mBean.size()==0) {
            mButton2.setOnClickListener(v -> finishCheck());
        }else {
            mButton2.setText("下一步");
            mButton2.setOnClickListener(v -> {
                mBean = reportAdapter.getSelected();
                ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX> listObj = (ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX>) getIntent().getSerializableExtra("step1");
                Intent intent = new Intent(CheckReport2Activity.this, CheckReport3Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objs", (Serializable) mBean);
                intent.putExtras(bundle);
                intent.putExtra("oid", getIntent().getStringExtra("oid"));
                intent.putExtra("step1", listObj);
                intent.putExtra("step2", (Serializable)listX);
                startActivity(intent);
            });
        }
        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(CheckReport2Activity.this, OrderDetailActivity.class);
                mainIntent.putExtra("oid", getIntent().getStringExtra("oid"));
                mainIntent.putExtra("status", 0);
                startActivity(mainIntent);
            } else if (resultBean.getCode().equals("401")) {
                logout(CheckReport2Activity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(CheckMessage loginInfoMessage) {
        if (loginInfoMessage.mListBeen.size() == 0) {
            mButton2.setText("完成");
            mButton2.setOnClickListener(v -> {
                finishCheck();
            });
        } else {
            mButton2.setText("下一步");
            mButton2.setOnClickListener(v -> {
                mBean = reportAdapter.getSelected();
                ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX> listObj = (ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX>) getIntent().getSerializableExtra("step1");
                Intent intent = new Intent(CheckReport2Activity.this, CheckReport3Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objs", (Serializable) mBean);
                intent.putExtras(bundle);
                intent.putExtra("oid", getIntent().getStringExtra("oid"));
                intent.putExtra("step1", listObj);
                intent.putExtra("step2", (Serializable)listX);
                startActivity(intent);
            });
        }
    }

    public void finishCheck() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("list", gson.toJsonTree(step1List));
        String jsonElements = gson.toJson(jsonObject);
        String TAG = "111111";
        Log.d(TAG, "onCreate: " + jsonElements);

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().save_report(
                new ProgressSubscriber<>(gatherOnNext, this), preferences.getString("token", ""),
                getIntent().getStringExtra("oid"), jsonElements);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
