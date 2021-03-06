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
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.NullStringToEmptyAdapterFactory;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.CheckReportAdapter;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressErrorSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextAndErrorListener;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

import static mituo.wshoto.com.mituo.Utils.logout;

public class CheckReport3Activity extends AppCompatActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.rv_check)
    RecyclerView mRvCheck;

    private SubscriberOnNextAndErrorListener<ResultBean> gatherOnNext;
    private String jsonElements;
    private CheckReportAdapter repairObjsDownAdapter;
    private ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX> step1List;
    private ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean> step2List;
    private List<ReportBean.ResultDataBean.Step2Bean.ListBean> listBeen;
    private boolean IS_CLICKABLE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report3);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean> listObj = (ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean>) getIntent().getSerializableExtra("objs");
        step1List = (ArrayList<ReportBean.ResultDataBean.Step1Bean.ListBeanX>) getIntent().getSerializableExtra("step1");
        step2List = (ArrayList<ReportBean.ResultDataBean.Step2Bean.ListBean>) getIntent().getSerializableExtra("step2");

        gatherOnNext = new SubscriberOnNextAndErrorListener<ResultBean>() {
            @Override
            public void onNext(ResultBean resultBean) {
                IS_CLICKABLE = true;
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(CheckReport3Activity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(CheckReport3Activity.this, OrderDetailActivity.class);
                mainIntent.putExtra("oid", getIntent().getStringExtra("oid"));
                mainIntent.putExtra("status", 0);
                startActivity(mainIntent);
            } else if (resultBean.getCode().equals("401")) {
                logout(CheckReport3Activity.this);
            } else {
                Toast.makeText(CheckReport3Activity.this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onError(Throwable e) {
                IS_CLICKABLE = true;
            }
        };
        repairObjsDownAdapter = new CheckReportAdapter(listObj);
        mRvCheck.addItemDecoration(new RecycleViewDivider(CheckReport3Activity.this, LinearLayoutManager.VERTICAL));
        mRvCheck.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
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
            if (IS_CLICKABLE) {
                IS_CLICKABLE = false;
                JsonObject jsonObject = new JsonObject();
//            try {
                listBeen = repairObjsDownAdapter.getList();
                for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : listBeen) {
                    if (!listBean.getBgxmValue().equals("1") && !listBean.getBgxmValue().equals("2")) {
                        Toast.makeText(this, "异常项未填写完整！", Toast.LENGTH_SHORT).show();
                        IS_CLICKABLE = true;
                        return false;
                    }
                }
                for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : step2List) {
                    listBean.setBgxmValue("0");
                }
                for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : step2List) {
                    for (ReportBean.ResultDataBean.Step2Bean.ListBean bean : listBeen) {
                        if (listBean.getBgxmId() == bean.getBgxmId()) {
                            listBean.setBgxmValue(bean.getBgxmValue());
                        }
                    }
                }
                listBeen = step2List;
                for (ReportBean.ResultDataBean.Step1Bean.ListBeanX listBeanX : step1List) {
                    ReportBean.ResultDataBean.Step2Bean.ListBean step2Bean = new ReportBean.ResultDataBean.Step2Bean.ListBean();
                    step2Bean.setBgxmValue(listBeanX.getBgxmValue());
                    step2Bean.setBgxmId(listBeanX.getBgxmId());
                    step2Bean.setBgxmName(listBeanX.getBgxmName());
                    step2Bean.setInputType(listBeanX.getInputType());
//                    Utils.copy(listBeanX, step2Bean);
                    listBeen.add(step2Bean);
                }
//            }
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }

                Gson gson = new GsonBuilder()
                        .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create();
                jsonObject.add("list", gson.toJsonTree(listBeen));
                jsonElements = gson.toJson(jsonObject);
                SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                HttpMethods.getInstance().save_report(
                        new ProgressErrorSubscriber<>(gatherOnNext, this), preferences.getString("token", ""),
                        getIntent().getStringExtra("oid"), jsonElements);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
