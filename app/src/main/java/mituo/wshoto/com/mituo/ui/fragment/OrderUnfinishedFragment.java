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
import mituo.wshoto.com.mituo.adapter.UnFinishedOrderAdapter;
import mituo.wshoto.com.mituo.bean.OrderBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

/**
 * Created by Weshine on 2017/6/6.
 */

public class OrderUnfinishedFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    private static final String EXTRA_CONTENT = "content";
    int currentItem = 5;
    @BindView(R.id.rv_order)
    PullLoadMoreRecyclerView mRvOrder;
    private SubscriberOnNextListener<OrderBean> getOrderListOnNext;
    private int page = 1;
    private UnFinishedOrderAdapter mUnFinishedOrderAdapter;
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_order_unfinished, container, false);
        ButterKnife.bind(this, root);

        getOrderListOnNext = resultBean -> {
            mRvOrder.setPullLoadMoreCompleted();
            mRvOrder.setVisibility(View.VISIBLE);
            if (resultBean.getCode().equals("200")) {
                mUnFinishedOrderAdapter.addAllData(resultBean.getResultData().getList());
            } else {
                Toast.makeText(getActivity(), resultBean.getResultMsg(), Toast.LENGTH_SHORT).show();
            }
        };

        RecyclerView recyclerView = mRvOrder.getRecyclerView();
        recyclerView.setVerticalScrollBarEnabled(true);
        mRvOrder.setRefreshing(false);
        mRvOrder.setPullRefreshEnable(true);
        mRvOrder.setPushRefreshEnable(true);
        mRvOrder.setFooterViewText("正在加载，请稍后");

        mRvOrder.setFooterViewTextColor(R.color.font_66);
        mRvOrder.setFooterViewBackgroundColor(R.color.order_e7);
        mRvOrder.setLinearLayout();

        mRvOrder.setOnPullLoadMoreListener(this);
        mRvOrder.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null));
        mUnFinishedOrderAdapter = new UnFinishedOrderAdapter(getActivity());
        mRvOrder.setAdapter(mUnFinishedOrderAdapter);
        initView();
        return root;
    }

    public static OrderUnfinishedFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        OrderUnfinishedFragment mainFragment = new OrderUnfinishedFragment();
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
        getData();
    }

    private void setRefresh() {
        mUnFinishedOrderAdapter.clearData();
        mUnFinishedOrderAdapter.notifyDataSetChanged();
        page = 1;
        currentItem = 10;
        mUnFinishedOrderAdapter.notifyDataSetChanged();
        mRvOrder.setVisibility(View.INVISIBLE);
    }


    private void initView() {
        getData();
    }

    private void getData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        HttpMethods.getInstance().check_order(
                new ProgressSubscriber<>(getOrderListOnNext, getActivity()), preferences.getString("token", ""), 0, 10,
                page);
    }


}