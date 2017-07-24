package mituo.wshoto.com.mituo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.R;

public class ChangePassSuccessActivity extends AppCompatActivity {
    @BindView(R.id.toolbar2)
    Toolbar mToolbar2;
    private Handler handler;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_success);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar2);
        handler = new Handler();
        handler.postDelayed(() -> {
            /* Create an Intent that will start the Main WordPress Activity. */
            Intent mainIntent = new Intent(ChangePassSuccessActivity.this, LoginActivity.class);
            startActivity(mainIntent);
        }, 1500);

    }

    @OnClick(R.id.bt_success_back)
    public void onViewClicked() {
        startActivity(new Intent(ChangePassSuccessActivity.this, LoginActivity.class));
    }
}
