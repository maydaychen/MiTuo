package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.NullStringToEmptyAdapterFactory;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.adapter.CheckReportAdapter;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Utils.logout;

public class CheckReport3Activity extends AppCompatActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.rv_check)
    RecyclerView mRvCheck;

    private SubscriberOnNextListener<ResultBean> gatherOnNext;
    private String jsonElements;
    private CheckReportAdapter repairObjsDownAdapter;
    private ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX> step1List;
    private List<ReportBean.ResultDataBean.Step2Bean.ListBean> listBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report3);
        ButterKnife.bind(this);
        mToolbar.setTitle("检测报告");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean> listObj = (ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean>) getIntent().getSerializableExtra("objs");
        step1List = (ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX>) getIntent().getSerializableExtra("step1");
        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(CheckReport3Activity.this, OrderDetailActivity.class);
                mainIntent.putExtra("oid", getIntent().getStringExtra("oid"));
                mainIntent.putExtra("status", 0);
                startActivity(mainIntent);
            } else if (resultBean.getCode().equals("401")){
                logout(CheckReport3Activity.this);
            } else{
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };

        repairObjsDownAdapter = new CheckReportAdapter(listObj);
//            mRvRepairObjs.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        mRvCheck.setLayoutManager(new LinearLayoutManager(this));
        mRvCheck.setAdapter(repairObjsDownAdapter);

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
            JsonObject jsonObject = new JsonObject();
            try {
                listBeen = repairObjsDownAdapter.getList();
                for (ReportBean.ResultDataBean.Step1Bean.ListBeanX listBeanX : step1List) {
                    ReportBean.ResultDataBean.Step2Bean.ListBean step2Bean = new ReportBean.ResultDataBean.Step2Bean.ListBean();
                    Utils.copy(listBeanX, step2Bean);
                    listBeen.add(step2Bean);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            jsonObject.add("list", gson.toJsonTree(listBeen));
            jsonElements = gson.toJson(jsonObject);
            String TAG = "111111";
            Log.d(TAG, "onCreate: " + jsonElements);

            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            HttpMethods.getInstance().save_report(
                    new ProgressSubscriber<>(gatherOnNext, this), preferences.getString("token", ""),
                    getIntent().getStringExtra("oid"), jsonElements);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
