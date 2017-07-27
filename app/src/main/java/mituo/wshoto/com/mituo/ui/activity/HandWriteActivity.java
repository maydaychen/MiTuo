package mituo.wshoto.com.mituo.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.ui.widget.LinePathView;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class HandWriteActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @BindView(R.id.view)
    LinePathView mPathView;
    @BindView(R.id.clear1)
    Button mClear;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.save1)
    Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_write);
        ButterKnife.bind(this);
        setResult(50);
        //设置保存监听
        mSave.setOnClickListener(v -> {
            if (mPathView.getTouched()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(
                            WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE, RECORD_AUDIO},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        save();
                    }
                } else save();
            } else {
                Toast.makeText(HandWriteActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
            }
        });
        mClear.setOnClickListener(v -> mPathView.clear());
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void save() {
        try {
            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            String path = preferences.getString("path", Config.PATH_MOBILE);
            File destDir = new File(path);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
//            mPathView.save("/sdcard/qm.png", true, 10);
            mPathView.save(path + "/" + getIntent().getStringExtra("oid") + ".png", true, 10);
            setResult(100);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                save();
            } else {
                //用户不同意，自行处理即可
                finish();
            }
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
