package mituo.wshoto.com.mituo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.AllCapTransformationMethod;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.SearchAdapter;
import mituo.wshoto.com.mituo.bean.SearchBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

import static mituo.wshoto.com.mituo.Utils.logout;

public class SearchActivity extends InitActivity {
    @BindView(R.id.sv_search)
    EditText mSvSearch;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    private SearchAdapter adapter;
    private String date;
    private SubscriberOnNextListener<SearchBean> gatherOnNext;
    private SharedPreferences preferences;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        gatherOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                mRvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                mRvSearch.addItemDecoration(new RecycleViewDivider(SearchActivity.this, LinearLayoutManager.VERTICAL));
                adapter = new SearchAdapter(SearchActivity.this, resultBean.getResultData().getList(), date);
                mRvSearch.setAdapter(adapter);
                adapter.setOnItemClickListener((view, data1) -> {
                    Intent intent = new Intent(SearchActivity.this, OrderDetailActivity.class);
                    intent.putExtra("oid", resultBean.getResultData().getList().get(data1).getOrderCode());
                    intent.putExtra("status", Integer.valueOf(resultBean.getResultData().getList().get(data1).getOrderStatus()));
                    startActivity(intent);
                });
            } else if (resultBean.getCode().equals("401")) {
                logout(SearchActivity.this);
            } else {
                Toast.makeText(this, resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        mSvSearch.setTransformationMethod(new AllCapTransformationMethod());
        RxTextView.textChanges(mSvSearch).subscribe(charSequence -> {
            date = charSequence.toString().toUpperCase();
            HttpMethods.getInstance().search(
                    new ProgressSubscriber<>(gatherOnNext, SearchActivity.this), preferences.getString("token", ""), date);
        });
    }

}
