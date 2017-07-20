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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.NullStringToEmptyAdapterFactory;
import mituo.wshoto.com.mituo.R;
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
    private SubscriberOnNextListener<ResultBean> saveRepairOnNext;
    private ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>> peijian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_objects);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        mResultDataBean = (RepairObjsBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        peijian = new ArrayList<>();
        repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, peijian, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.setAdapter(repairObjsDetailAdapter);

        saveRepairOnNext = resultBean -> {
            if (resultBean.isResult()) {
                finish();
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }

        };
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        startActivityForResult(new Intent(RepairObjectsActivity.this, AddRepairObjsActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data.getBooleanExtra("isTaocan", true)) {
                AllRepairBean.ResultDataBean.TcListBean tcListBean1 = (AllRepairBean.ResultDataBean.TcListBean) data.getSerializableExtra("objs");
                peijian = (ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>) data.getSerializableExtra("peijian");
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
                repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, peijian, this);
                mRecyclerView.setAdapter(repairObjsDetailAdapter);
            } else {
                AllRepairBean.ResultDataBean.XmListBean xmListBean = (AllRepairBean.ResultDataBean.XmListBean) data.getSerializableExtra("objs");
                peijian = (ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>) data.getSerializableExtra("peijian");
                RepairObjsBean.ResultDataBean.XmListBean xmListBean1 = new RepairObjsBean.ResultDataBean.XmListBean();
                xmListBean1.setPjlb(xmListBean.getPjlb());
                xmListBean1.setIsZd(xmListBean.getIsZd());
                xmListBean1.setXmName(xmListBean.getXmName());
                xmListBean1.setXmprice(xmListBean.getXmprice());
                xmListBean1.setPjNum("1");

                mResultDataBean.getXmList().add(xmListBean1);
                repairObjsDetailAdapter = new RepairObjsDetailAdapter(mResultDataBean, peijian, this);
                mRecyclerView.setAdapter(repairObjsDetailAdapter);
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
}
