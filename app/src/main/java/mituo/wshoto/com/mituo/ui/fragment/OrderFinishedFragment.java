package mituo.wshoto.com.mituo.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.adapter.FinishedOrderAdapter;
import mituo.wshoto.com.mituo.bean.OrderBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressErrorSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextAndErrorListener;

/**
 * Created by Weshine on 2017/6/6.
 */

public class OrderFinishedFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
//    private SubscriberOnNextAndErrorListener CheckOnNext;
//    private UnFinishedOrderAdapter mRecyclerViewAdapter;

    private static final String EXTRA_CONTENT = "content";
    int currentItem = 5;
    @BindView(R.id.rv_order_finished)
    PullLoadMoreRecyclerView mRvOrderFinished;
    private View root;
    private SubscriberOnNextAndErrorListener<OrderBean> getOrderListOnNext;
    private int page = 1;
    private FinishedOrderAdapter mFinishedOrderAdapter;
    private boolean IS_REFRESH = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_order_finished, container, false);
        ButterKnife.bind(this, root);

        getOrderListOnNext = new SubscriberOnNextAndErrorListener<OrderBean>() {
            @Override
            public void onNext(OrderBean resultBean) {
                try {
                    if (IS_REFRESH){
                        mFinishedOrderAdapter.clearData();
//                        mFinishedOrderAdapter.notifyDataSetChanged();
                    }
                    mRvOrderFinished.setPullLoadMoreCompleted();
                    mRvOrderFinished.setVisibility(View.VISIBLE);
                    if (resultBean.getCode().equals("200")) {
                        mFinishedOrderAdapter.addAllData(resultBean.getResultData().getList());
                        mFinishedOrderAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (NullPointerException e) {
                    if (page != 0) {
                        Toast.makeText(getActivity(), "已经加载完毕！", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mRvOrderFinished.setPullLoadMoreCompleted();
                mRvOrderFinished.setVisibility(View.VISIBLE);
            }
        };

        RecyclerView recyclerView = mRvOrderFinished.getRecyclerView();
        recyclerView.setVerticalScrollBarEnabled(true);
        mRvOrderFinished.setRefreshing(false);
        mRvOrderFinished.setPullRefreshEnable(true);
        mRvOrderFinished.setPushRefreshEnable(true);
        mRvOrderFinished.setFooterViewText("正在加载，请稍后");

        mRvOrderFinished.setFooterViewTextColor(R.color.font_66);
        mRvOrderFinished.setFooterViewBackgroundColor(R.color.order_e7);
        mRvOrderFinished.setLinearLayout();

        mRvOrderFinished.setOnPullLoadMoreListener(this);
        mRvOrderFinished.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null));
        mFinishedOrderAdapter = new FinishedOrderAdapter(getActivity());
        mRvOrderFinished.setAdapter(mFinishedOrderAdapter);
        initView();
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        IS_REFRESH = true;
        setRefresh();
        getData();
    }


    public static OrderFinishedFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        OrderFinishedFragment mainFragment = new OrderFinishedFragment();
        mainFragment.setArguments(arguments);
        return mainFragment;
    }

    @Override
    public void onRefresh() {
        setRefresh();
        getData();
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        IS_REFRESH = false;
        getData();
    }

    private void setRefresh() {
        mFinishedOrderAdapter.clearData();
        mFinishedOrderAdapter.notifyDataSetChanged();
        page = 1;
        currentItem = 10;
        mRvOrderFinished.setVisibility(View.INVISIBLE);
    }


    private void initView() {

    }

    private void getData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().check_order(
                new ProgressErrorSubscriber<>(getOrderListOnNext, getActivity()), preferences.getString("token", ""), 1, 10,
                page);
    }

}