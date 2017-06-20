package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.suru.myp.MonthYearPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;

public class CarInfoActivity extends InitActivity {

    @BindView(R.id.toolbar4)
    Toolbar mToolbar;
    @BindView(R.id.tv_care_next_time)
    TextView mTvCareNextTime;

    private MonthYearPicker myp;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_car_info);
        ButterKnife.bind(this);
        mToolbar.setTitle("车辆信息");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());

        myp = new MonthYearPicker(this);
        myp.build((dialog, which) -> mTvCareNextTime.setText(myp.getSelectedYear() + " - " + myp.getSelectedMonthName()), null);
    }

    @Override
    public void initData() {

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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
