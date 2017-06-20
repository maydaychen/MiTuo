package mituo.wshoto.com.mituo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.ui.activity.InitActivity;
import mituo.wshoto.com.mituo.ui.activity.LoginActivity;
import mituo.wshoto.com.mituo.ui.activity.OrderDetailActivity;
import mituo.wshoto.com.mituo.ui.activity.StorageActivity;
import mituo.wshoto.com.mituo.ui.fragment.OrderFinishedFragment;
import mituo.wshoto.com.mituo.ui.fragment.OrderUnfinishedFragment;
import mituo.wshoto.com.mituo.ui.widget.CustomViewPager;

public class MainActivity extends InitActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.order_tab)
    TabLayout mOrderTab;
    @BindView(R.id.vp_content)
    CustomViewPager mVpContent;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的订单");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mVpContent.setOffscreenPageLimit(2);
        initContent();
        initTab();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, StorageActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, OrderDetailActivity.class));
        } else if (id == R.id.nav_slideshow) {
            show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("进行中");
        tabIndicators.add("已完成");
        tabFragments = new ArrayList<>();
        tabFragments.add(OrderUnfinishedFragment.newInstance(tabIndicators.get(0)));
        tabFragments.add(OrderFinishedFragment.newInstance(tabIndicators.get(1)));
        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mVpContent.setAdapter(contentAdapter);
    }

    private void initTab() {
//        mOrderTab.setTabMode(TabLayout.MODE_FIXED);
//        mOrderTab.setTabGravity(TabLayout.GRAVITY_FILL);
        mOrderTab.setupWithViewPager(mVpContent);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
//            SharedPreferences mySharedPreferences = getSharedPreferences("user",
//                    Activity.MODE_PRIVATE);
//            SharedPreferences.Editor editor = mySharedPreferences.edit();
//            editor.putBoolean("autoLog", false);
//            if (editor.commit()) {
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                dialog.dismiss();
//            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
