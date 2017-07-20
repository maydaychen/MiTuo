package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.AllRepairBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

public class AddRepairObjsActivity extends InitActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar4;
    @BindView(R.id.rv_add_repair_obj)
    ListView mRvAddRepairObj;
    @BindView(R.id.rv_add_repair_obj_project)
    ListView mRvAddRepairObjProject;
    @BindView(R.id.textView14)
    TextView mTextView14;
    @BindView(R.id.textView15)
    TextView mTextView15;
    private SubscriberOnNextListener<AllRepairBean> getOrderListOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_repair_objs);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        getOrderListOnNext = resultBean -> {
            List<String> list = new ArrayList<>();
            for (AllRepairBean.ResultDataBean.TcListBean tcListBean : resultBean.getResultData().getTcList()) {
                list.add(tcListBean.getTcName());
            }
            mRvAddRepairObj.setAdapter(new ArrayAdapter<>(this, R.layout.item_taocan_text, list));
            mRvAddRepairObj.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if (resultBean.getResultData().getTcList().get(position).getTcName().contains("保养")) {
                    for (AllRepairBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : resultBean.getResultData().getTcList().get(position).getTcxmList()) {
                        for (AllRepairBean.ResultDataBean.PjListBean pjListBean : resultBean.getResultData().getPjList()) {
                            if (pjListBean.getPjlb().equals(tcxmListBean.getPjlb())) {
                                tcxmListBean.setPjName(pjListBean.getPjName());
                                tcxmListBean.setPjCode(pjListBean.getPjCode());
                                tcxmListBean.setPjpp(pjListBean.getPjpp());
                                tcxmListBean.setPjNum("1");
                                tcxmListBean.setPjPrice(pjListBean.getPjPrice());
                                break;
                            }
                        }
                    }
                }else {
                    List<List<AllRepairBean.ResultDataBean.PjListBean>> listBeen = new ArrayList<>();
                    for (AllRepairBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : resultBean.getResultData().getTcList().get(position).getTcxmList()) {
                        List<AllRepairBean.ResultDataBean.PjListBean> list2 = new ArrayList<>();
                        for (AllRepairBean.ResultDataBean.PjListBean pjListBean : resultBean.getResultData().getPjList()) {
                            if (pjListBean.getPjlb().equals(tcxmListBean.getPjlb())) {
                                list2.add(pjListBean);
                            }
                        }
                        listBeen.add(list2);
                    }
                    bundle.putSerializable("peijian", (Serializable) listBeen);
                }

                bundle.putSerializable("objs", resultBean.getResultData().getTcList().get(position));
                intent.putExtras(bundle);
                intent.putExtra("isTaocan", true);
                setResult(RESULT_OK, intent);
                finish();
            });

            List<String> list1 = new ArrayList<>();
            for (AllRepairBean.ResultDataBean.XmListBean xmListBean : resultBean.getResultData().getXmList()) {
                list1.add(xmListBean.getXmName());
            }
            mRvAddRepairObjProject.setAdapter(new ArrayAdapter<>(this, R.layout.item_taocan_text, list1));
            mRvAddRepairObjProject.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent();
                List<List<AllRepairBean.ResultDataBean.PjListBean>> listBeen = new ArrayList<>();
                List<AllRepairBean.ResultDataBean.PjListBean> list2 = new ArrayList<AllRepairBean.ResultDataBean.PjListBean>();
                for (AllRepairBean.ResultDataBean.PjListBean pjListBean : resultBean.getResultData().getPjList()) {
                    if (pjListBean.getPjlb().equals(resultBean.getResultData().getXmList().get(position).getPjlb())) {
                        list2.add(pjListBean);
                    }
                }
                listBeen.add(list2);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objs", resultBean.getResultData().getXmList().get(position));
                bundle.putSerializable("peijian", (Serializable) listBeen);
                intent.putExtras(bundle);
                intent.putExtra("isTaocan", false);
                setResult(RESULT_OK, intent);
                finish();
            });
        };

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().all_repair_objs(
                new ProgressSubscriber<>(getOrderListOnNext, this), preferences.getString("token", ""));
    }

}
