package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.NullStringToEmptyAdapterFactory;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.RepairMessage;
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.adapter.RepairObjsDetailAdapter;
import mituo.wshoto.com.mituo.bean.AllRepairBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

public class RepairObjectsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private RepairObjsBean.ResultDataBean mResultDataBean;
    private RepairObjsDetailAdapter repairObjsDetailAdapter;
    Map<String, ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>> map = new HashMap<>();
    private List<AllRepairBean.ResultDataBean.TcListBean> mTcListBeen = new ArrayList<>();
    private List<AllRepairBean.ResultDataBean.XmListBean> mXmListBeen = new ArrayList<>();
    private List<AllRepairBean.ResultDataBean.PjListBean> mPjListBeen = new ArrayList<>();

    private SubscriberOnNextListener<ResultBean> saveRepairOnNext;
    private SubscriberOnNextListener<AllRepairBean> getOrderListOnNext;
    private Map<String, ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>> peijian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_objects);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mResultDataBean = (RepairObjsBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        peijian = new HashMap<>();

        saveRepairOnNext = resultBean -> {
            if (resultBean.isResult()) {
                finish();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        };

        getOrderListOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mPjListBeen = resultBean.getResultData().getPjList();
                mTcListBeen = resultBean.getResultData().getTcList();
                mXmListBeen = resultBean.getResultData().getXmList();
                refresh();
            }
        };
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().all_repair_objs(
                new ProgressSubscriber<>(getOrderListOnNext, this), preferences.getString("token", ""), getIntent().getStringExtra("oid"));
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        Intent intent = new Intent(RepairObjectsActivity.this, AddRepairObjsActivity.class);
        intent.putExtra("oid", getIntent().getStringExtra("oid"));
        Bundle bundle = new Bundle();
        bundle.putSerializable("objs", mResultDataBean);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data.getBooleanExtra("isTaocan", true)) {
                AllRepairBean.ResultDataBean.TcListBean tcListBean1 = (AllRepairBean.ResultDataBean.TcListBean) data.getSerializableExtra("objs");
                List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> list = new ArrayList<>();
                for (AllRepairBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : tcListBean1.getTcxmList()) {
                    RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean1 = new RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean();
                    Utils.copy(tcxmListBean, tcxmListBean1);
                    list.add(tcxmListBean1);
                }
                RepairObjsBean.ResultDataBean.TcListBean tcListBean = new RepairObjsBean.ResultDataBean.TcListBean();
                Utils.copy(data.getSerializableExtra("objs"), tcListBean);
                tcListBean.setTcxmList(list);
                mResultDataBean.getTcList().add(tcListBean);
                refresh();
//                repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, map, this);
//                mRecyclerView.setAdapter(repairObjsDetailAdapter);
            } else {
                AllRepairBean.ResultDataBean.XmListBean xmListBean = (AllRepairBean.ResultDataBean.XmListBean) data.getSerializableExtra("objs");
                peijian.put(xmListBean.getXmName(), (ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>) data.getSerializableExtra("peijian"));
                RepairObjsBean.ResultDataBean.XmListBean xmListBean1 = new RepairObjsBean.ResultDataBean.XmListBean();
                xmListBean1.setPjlb(xmListBean.getPjlb());
                xmListBean1.setIsZd("1");
                xmListBean1.setIsCanZidai(xmListBean.getIsZd());
                xmListBean1.setXmName(xmListBean.getXmName());
                xmListBean1.setXmprice(xmListBean.getXmprice());
                xmListBean1.setPjNum("1");

                mResultDataBean.getXmList().add(xmListBean1);
                refresh();
//                repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, peijian, this);
//                mRecyclerView.setAdapter(repairObjsDetailAdapter);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mecu_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            String toekn = preferences.getString("token", "");
            String test = gson.toJson(repairObjsDetailAdapter.getData());
            String test1 = getIntent().getStringExtra("oid");
            HttpMethods.getInstance().save_repair(
                    new ProgressSubscriber<>(saveRepairOnNext, this), toekn,
                    test1, test);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
            for (RepairObjsBean.ResultDataBean.TcListBean tcListBean : mResultDataBean.getTcList()) {
                ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>> allList = new ArrayList<>();
                for (RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean tcxmListBean : tcListBean.getTcxmList()) {
                    //项目是否可自带
                    for (AllRepairBean.ResultDataBean.TcListBean listBean : mTcListBeen) {
                        if (listBean.getTcName().equals(tcListBean.getTcName())) {
                            for (int i = 0; i < tcListBean.getTcxmList().size(); i++) {
                                tcListBean.getTcxmList().get(i).setIsCanZidai(listBean.getTcxmList().get(i).getIsZd());
                            }
                        }
                    }

                    //获取关联配件
                    ArrayList<AllRepairBean.ResultDataBean.PjListBean> pjList = new ArrayList<>();
                    for (AllRepairBean.ResultDataBean.PjListBean pjListBean : mPjListBeen) {
                        if (null!= tcxmListBean.getPjlb()){
                            if (tcxmListBean.getPjlb().equals(pjListBean.getPjlb())) {
                                pjList.add(pjListBean);
                            }
                        }
                    }
                    allList.add(pjList);
                }
                map.put(tcListBean.getTcName(), allList);
            }

            for (RepairObjsBean.ResultDataBean.XmListBean xmListBean : mResultDataBean.getXmList()) {
                ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>> allList = new ArrayList<>();
                for (AllRepairBean.ResultDataBean.XmListBean listBean : mXmListBeen) {
                    if (listBean.getXmName().equals(xmListBean.getXmName())) {
                        xmListBean.setIsCanZidai(listBean.getIsZd());
                    }
                }

                ArrayList<AllRepairBean.ResultDataBean.PjListBean> pjList = new ArrayList<>();
                for (AllRepairBean.ResultDataBean.PjListBean pjListBean : mPjListBeen) {
                    if (null!= xmListBean.getPjlb()){
                        if (xmListBean.getPjlb().equals(pjListBean.getPjlb())) {
                            pjList.add(pjListBean);
                        }
                    }
                }
                allList.add(pjList);
                map.put(xmListBean.getXmName(), allList);
            }
        repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, map, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.setAdapter(repairObjsDetailAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(RepairMessage loginInfoMessage) {
        mResultDataBean = loginInfoMessage.mResultDataBean;
        refresh();
    }
}
