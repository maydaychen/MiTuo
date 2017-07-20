package mituo.wshoto.com.mituo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.R;
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

    private boolean IS_INNER = true;
    private String path;//默认存储路径
    private boolean HAS_SD = false;//是否拥有外置SD

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storage);
        ButterKnife.bind(this);
        mToolbar.setTitle("存储设置");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mTest.setProgress((int) (getSystemAvailableSize() / (double) getSDTotalSize() * 100));
        DecimalFormat df = new DecimalFormat("######0.00");

        String result = String.format(getResources().getString(R.string.storage),
                df.format((float) getSystemAvailableSize() / 1024 / 1024 / 1024) + "",
                df.format((float) getSDTotalSize() / 1024 / 1024 / 1024) + "");//对应xml中定义的123顺序
        mTvStorage1.setText(result);
//        Toast.makeText(this, (int) (getSystemAvailableSize() / (double) getSDTotalSize() * 100) + "%", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rl_inner_sd)
    public void onViewClicked() {
        mCbStarageSd.setChecked(!IS_INNER);
        IS_INNER = !IS_INNER;
        if (IS_INNER) {
            path = Config.PATH_MOBILE;
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<String> getExtSDCardPath() {
        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard")) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }
}
