package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.AllRepairBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.MyListView;

public class AddRepairObjsActivity extends InitActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar4;
    @BindView(R.id.rv_add_repair_obj)
    MyListView mRvAddRepairObj;
    @BindView(R.id.rv_add_repair_obj_project)
    MyListView mRvAddRepairObjProject;
    @BindView(R.id.textView14)
    TextView mTextView14;
    @BindView(R.id.textView15)
    TextView mTextView15;
    private SubscriberOnNextListener<AllRepairBean> getOrderListOnNext;
    private RepairObjsBean.ResultDataBean mResultDataBean;
    private List<AllRepairBean.ResultDataBean.XmListBean> temp_xmList;
    private List<AllRepairBean.ResultDataBean.TcListBean> temp_tcList;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_repair_objs);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar4);
        mToolbar4.setNavigationIcon(R.drawable.nav_back);
        mToolbar4.setNavigationOnClickListener(v -> finish());
        mResultDataBean = (RepairObjsBean.ResultDataBean) getIntent().getSerializableExtra("objs");
    }

    @Override
    public void initData() {
        getOrderListOnNext = resultBean -> {
            List<String> list = new ArrayList<>();
            temp_tcList = new ArrayList<>();
            temp_xmList = new ArrayList<>();
            for (AllRepairBean.ResultDataBean.TcListBean tcListBean : resultBean.getResultData().getTcList()) {
                list.add(tcListBean.getTcName());
                temp_tcList.add(tcListBean);
            }
            for (AllRepairBean.ResultDataBean.TcListBean tcListBean : resultBean.getResultData().getTcList()) {
                for (RepairObjsBean.ResultDataBean.TcListBean listBean : mResultDataBean.getTcList()) {
                    if (listBean.getTcName().equals(tcListBean.getTcName())) {
                        list.remove(tcListBean.getTcName());
                        temp_tcList.remove(tcListBean);
                    }
                }
            }
            List<String> list1 = new ArrayList<>();
            for (AllRepairBean.ResultDataBean.XmListBean xmListBean : resultBean.getResultData().getXmList()) {
                list1.add(xmListBean.getXmName());
                temp_xmList.add(xmListBean);
            }

            for (AllRepairBean.ResultDataBean.XmListBean xmListBean : resultBean.getResultData().getXmList()) {
                for (RepairObjsBean.ResultDataBean.XmListBean listBean : mResultDataBean.getXmList()) {
                    if (listBean.getXmName().equals(xmListBean.getXmName())) {
                        list1.remove(xmListBean.getXmName());
                        temp_xmList.remove(xmListBean);
                    }
                }
            }
            mRvAddRepairObj.setAdapter(new ArrayAdapter<>(this, R.layout.item_taocan_text, list));
            mRvAddRepairObj.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("objs", temp_tcList.get(position));
                intent.putExtras(bundle);
                intent.putExtra("isTaocan", true);
                setResult(RESULT_OK, intent);
                finish();
            });


            mRvAddRepairObjProject.setAdapter(new ArrayAdapter<>(this, R.layout.item_taocan_text, list1));
            mRvAddRepairObjProject.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("objs", temp_xmList.get(position));
                intent.putExtras(bundle);
                intent.putExtra("isTaocan", false);
                setResult(RESULT_OK, intent);
                finish();
            });
        };

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().all_repair_objs(
                new ProgressSubscriber<>(getOrderListOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
    }

}
