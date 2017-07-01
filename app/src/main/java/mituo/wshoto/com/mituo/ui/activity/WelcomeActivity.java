package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.MainActivity;
import mituo.wshoto.com.mituo.R;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.start_iv)
    ImageView startIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Main WordPress Activity. */
            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            if (preferences.getBoolean("autoLog", false)) {
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            } else {
                Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        }, 1500);
    }
}
