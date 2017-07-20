package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suru.myp.MonthYearPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static mituo.wshoto.com.mituo.Utils.logout;

public class CarInfoActivity extends InitActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.tv_care_next_time)
    TextView mTvCareNextTime;
    @BindView(R.id.owner_name)
    TextView ownerName;
    @BindView(R.id.owner_telephone)
    TextView ownerTelephone;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.et_car_model_num)
    EditText etCarModelNum;
    @BindView(R.id.et_mile)
    EditText etMile;
    @BindView(R.id.et_next_care_mile)
    EditText etNextCareMile;

    private MonthYearPicker myp;
    private CarInfoBean.ResultDataBean mResultDataBean;
    private SubscriberOnNextListener<ResultBean> gatherOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        mToolbar.setTitle("车辆信息");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        mResultDataBean = (CarInfoBean.ResultDataBean) getIntent().getSerializableExtra("objs");
        myp = new MonthYearPicker(this);
        myp.build((dialog, which) -> mTvCareNextTime.setText(myp.getSelectedYear() + " - " + myp.getSelectedMonthName()), null);
    }

    @Override
    public void initData() {
        ownerName.setText(String.format(getResources().getString(R.string.car_onwer_edit), mResultDataBean.getContactName()));
        ownerTelephone.setText(String.format(getResources().getString(R.string.telephone_edit), mResultDataBean.getContactPhone()));
        tvCarNum.setText(String.format(getResources().getString(R.string.card_num_edit), mResultDataBean.getCarNo()));
        tvCarType.setText(String.format(getResources().getString(R.string.car_type_edit), mResultDataBean.getCarXh()));
        etMile.setText(mResultDataBean.getCarXslc());
        mTvCareNextTime.setText(mResultDataBean.getXcbyDate());
        etNextCareMile.setText( mResultDataBean.getXcbylc());
        etCarModelNum.setText( mResultDataBean.getCarCjh());

        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else if (resultBean.getCode().equals("401")){
                logout(CarInfoActivity.this);
            } else{
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick(R.id.tv_care_next_time)
    public void onViewClicked() {
        myp.show();
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
            HttpMethods.getInstance().save_car_info(
                    new ProgressSubscriber<>(gatherOnNext, this), getIntent().getStringExtra("oid"),
                    preferences.getString("token", ""), etCarModelNum.getText().toString(), etMile.getText().toString(),
                    etNextCareMile.getText().toString(), mTvCareNextTime.getText().toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
