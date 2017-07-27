package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.StorageUtils;
import mituo.wshoto.com.mituo.bean.StorageBean;
import mituo.wshoto.com.mituo.ui.widget.CircleProgressViewEasy;

import static mituo.wshoto.com.mituo.MemorySpaceCheck.getSDTotalSize;
import static mituo.wshoto.com.mituo.MemorySpaceCheck.getSystemAvailableSize;

public class StorageActivity extends InitActivity {

    @BindView(R.id.toolbar3)
    Toolbar mToolbar;
    @BindView(R.id.test)
    CircleProgressViewEasy mTest;
    @BindView(R.id.tv_storage_1)
    TextView mTvStorage1;
    @BindView(R.id.cb_starage_sd)
    CheckBox mCbStarageSd;
    @BindView(R.id.cb_starage_sd2)
    CheckBox mCbStarageSd2;
    @BindView(R.id.test2)
    CircleProgressViewEasy mTest2;
    @BindView(R.id.tv_storage_2)
    TextView mTvStorage2;
    @BindView(R.id.cv_sd2)
    CardView mCvSd2;
    @BindView(R.id.rl_inner_sd)
    RelativeLayout mRlInnerSd;
    @BindView(R.id.rl_inner_sd2)
    RelativeLayout mRlInnerSd2;

    private boolean IS_INNER = true;
    private String path;//默认存储路径
    private boolean HAS_SD = false;//是否拥有外置SD
    private ArrayList<StorageBean> storageData;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storage);
        ButterKnife.bind(this);
        mToolbar.setTitle("存储设置");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mTest.setProgress((int) ((getSDTotalSize() - getSystemAvailableSize()) / (double) getSDTotalSize() * 100));
        DecimalFormat df = new DecimalFormat("######0.00");

        String result = String.format(getResources().getString(R.string.storage),
                df.format((float) getSystemAvailableSize() / 1024 / 1024 / 1024) + "",
                df.format((float) getSDTotalSize() / 1024 / 1024 / 1024) + "");//对应xml中定义的123顺序
        mTvStorage1.setText(result);

        storageData = StorageUtils.getStorageData(this);
        if (storageData.size() == 2) {
            mCvSd2.setVisibility(View.VISIBLE);
            mTest2.setProgress((int) ((storageData.get(1).getTotalSize() - storageData.get(1).getAvailableSize()) / (double) storageData.get(1).getTotalSize() * 100));

            String result2 = String.format(getResources().getString(R.string.storage),
                    df.format((float) storageData.get(1).getAvailableSize() / 1024 / 1024 / 1024) + "",
                    df.format((float) storageData.get(1).getTotalSize() / 1024 / 1024 / 1024) + "");//对应xml中定义的123顺序
            mTvStorage2.setText(result2);
            mRlInnerSd.setOnClickListener(v -> mCbStarageSd.setChecked(true));
            mRlInnerSd2.setOnClickListener(v -> mCbStarageSd2.setChecked(true));

            RxCompoundButton.checkedChanges(mCbStarageSd).subscribe(aBoolean -> {
                if (aBoolean) {
                    IS_INNER = true;
                    mCbStarageSd2.setChecked(false);
                }
            });
            RxCompoundButton.checkedChanges(mCbStarageSd2).subscribe(aBoolean -> {
                if (aBoolean) {
                    IS_INNER = false;
                    mCbStarageSd.setChecked(false);
                }
            });
        } else {
            mRlInnerSd.setClickable(false);
        }


    }

    @Override
    public void initData() {

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
            SharedPreferences.Editor editor = preferences.edit();
            if (IS_INNER) {
                editor.putString("path", storageData.get(0).getPath() + "/mituo");
                editor.apply();
            } else {
                editor.putString("path", storageData.get(1).getPath() + "/mituo");
                editor.apply();
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @OnClick({R.id.rl_inner_sd, R.id.rl_inner_sd2})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.rl_inner_sd:
//                mCbStarageSd.setChecked(!IS_INNER);
//                IS_INNER = !IS_INNER;
//                if (IS_INNER) {
//                    path = Config.PATH_MOBILE;
//                }
//                break;
//            case R.id.rl_inner_sd2:
//                mCbStarageSd2.setChecked(!IS_INNER);
//                IS_INNER = !IS_INNER;
//                if (IS_INNER) {
//                    path = Config.PATH_MOBILE;
//                }
//                break;
//        }
//    }
}
