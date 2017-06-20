package mituo.wshoto.com.mituo.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.R;

/**
 * Created by Weshine on 2017/6/6.
 */

public class OrderUnfinishedFragment extends Fragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {
//    private SubscriberOnNextAndErrorListener CheckOnNext;
//    private PayOrderAdapter mRecyclerViewAdapter;

    private static final String EXTRA_CONTENT = "content";
    private List<String> left_list;
    int currentItem = 5;

    private int page = 0;
    private static final int SHOW_SUBACTIVITY = 1;
    private static final int END_DATE = 2;
    //    private FenleiLeftAdapter fenleiLeftAdapter;
    private String source;
    private ProgressDialog dialog = null;
    private String temp_start = "", temp_end = "";
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_order_unfinished, container, false);
        ButterKnife.bind(this, root);

//        source = Config.yfy;
//        CheckOnNext = new SubscriberOnNextAndErrorListener<PayOrderBean>() {
//            @Override
//            public void onNext(PayOrderBean payOrderBean) {
//                mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
//                try {
//                    mTvPayOrderPrice.setText("");
//                    if (!payOrderBean.getSumoney().isEmpty()) {
//                        mTvPayOrderPrice.setText(String.format(getActivity().getResources().getString(R.string.money), payOrderBean.getSumoney()));
//                    }
//                    mRecyclerViewAdapter.addAllData(payOrderBean.getData());
//                } catch (NullPointerException e) {
//                    if (page != 0) {
//                        Toast.makeText(getActivity(), "已经加载完毕！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//                mTvPayOrderPrice.setText(String.format(getActivity().getResources().getString(R.string.money), ""));
//                mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
//                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
//            }
//        };

//        RecyclerView recyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
//        recyclerView.setVerticalScrollBarEnabled(true);
//        mPullLoadMoreRecyclerView.setRefreshing(false);
//        mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
//        mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
//        mPullLoadMoreRecyclerView.setFooterViewText("正在加载，请稍后");
//
//        mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.font_666);
//        mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.order_e7);
//        mPullLoadMoreRecyclerView.setLinearLayout();
//
//        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
//        mPullLoadMoreRecyclerView.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, null));
//        mRecyclerViewAdapter = new PayOrderAdapter(getActivity());
//        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
//
//        getData(start_string, end_string, "", "", "", "1", page + "");
//        initData();
        initView();

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        currentItem = 5;
//        fenleiLeftAdapter.notifyDataSetChanged();
    }

    public static OrderUnfinishedFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        OrderUnfinishedFragment mainFragment = new OrderUnfinishedFragment();
        mainFragment.setArguments(arguments);
        return mainFragment;
    }

//    private void getData(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7) {
//        if (parm1.equals("") && parm2.equals("")) {
//            mTvPayOrderTime.setText("所有付款单");
//        } else {
//            mTvPayOrderTime.setText(parm1 + " 至 " + (parm2.isEmpty() ? today : parm2));
//        }
//        mPullLoadMoreRecyclerView.setVisibility(View.INVISIBLE);
//        dialog = ProgressDialog.show(getActivity(), "提示", "正在查找数据，请稍等...", false, true);//创建ProgressDialog
//        HttpMethods.getInstance().pay_order(
//                new ProgressErrorSubscriber(CheckOnNext, getActivity()),
//                parm1, parm2, parm3, parm4, parm5, parm6, parm7, "2", source);
//    }

//    //load more 专属方法
//    private void getData1(String parm1, String parm2, String parm3, String parm4, String parm5, String parm6, String parm7) {
//        if (parm1.equals("") && parm2.equals("")) {
//            mTvPayOrderTime.setText("所有付款单");
//        } else {
//            mTvPayOrderTime.setText(parm1 + " 至 " + (parm2.isEmpty() ? today : parm2));
//        }
//        HttpMethods.getInstance().pay_order(
//                new ProgressErrorSubscriber(CheckOnNext, getActivity()),
//                parm1, parm2, parm3, parm4, parm5, parm6, parm7, "2", source);
//    }


    @Override
    public void onRefresh() {
        setRefresh();
//        getData(start_string, end_string, "", "", "", "1", page + "");
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
//        getData1(start_string, end_string, order_number, name, teltphone, "1", page + "");
    }

    private void setRefresh() {
//        mRecyclerViewAdapter.clearData();
//        mRecyclerViewAdapter.notifyDataSetChanged();
//        mTvPayOrderPrice.setText("");
//        page = 0;
//        currentItem = 5;
//        fenleiLeftAdapter.notifyDataSetChanged();
//        order_number = name = teltphone = start_string = end_string = "";
//        mPullLoadMoreRecyclerView.setVisibility(View.INVISIBLE);
    }


    private void initView() {
//        fenleiLeftAdapter = new FenleiLeftAdapter(getActivity(), R.layout.item_day, left_list);
//        mFenleiList.setAdapter(fenleiLeftAdapter);
//        mFenleiList.setOnItemClickListener((adapterView, view, i, l) -> {
//            currentItem = i;
//            fenleiLeftAdapter.notifyDataSetChanged();
//            end_string = today;
//            switch (i) {
//                case 0:
//                    start_string = today;
//                    break;
//                case 1:
//                    start_string = sdf.format(new Date(nowdate.getTime() - 2 * 24 * 60 * 60 * 1000));
//                    break;
//                case 2:
//                    start_string = sdf.format(new Date(nowdate.getTime() - 6 * 24 * 60 * 60 * 1000));
//                    break;
//                case 3:
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(nowdate);
//                    cal.add(Calendar.MONTH, -1);
//                    cal.add(Calendar.DATE, 1);
//                    start_string = sdf.format(cal.getTime());
//                    break;
//            }
//            page = 0;
//            end_string = today;
//            mRecyclerViewAdapter.clearData();
//            mRecyclerViewAdapter.notifyDataSetChanged();
//            getData(start_string, end_string, order_number, name, teltphone, "1", page + "");
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    class FenleiLeftAdapter extends BaseAdapter {
//        private LayoutInflater flater;
//        private int resource;
//        private TextView text;
//        private List mList;
//
//        FenleiLeftAdapter(Context context, int resource, List objects) {
//            flater = LayoutInflater.from(context);
//            this.mList = objects;
//            this.resource = resource;
//        }
//
//        @Override
//        public int getCount() {
//            return mList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView = flater.inflate(resource, null);
//            text = (TextView) convertView.findViewById(R.id.fenlei_text);
//            text.setText((String) mList.get(position));
//            if (position == currentItem) {
//                text.setBackgroundResource(R.drawable.boder_bt_blue);
//                text.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
//            } else {
//                text.setBackgroundResource(R.color.background);
//                text.setTextColor(ContextCompat.getColor(getActivity(), R.color.font_666));
//            }
//            return convertView;
//        }
//    }

//    private void refesh() {
//        currentItem = 5;
//        name = teltphone = start_string = end_string = order_number = "";
//        page = 0;
//        fenleiLeftAdapter.notifyDataSetChanged();
//        mRecyclerViewAdapter.clearData();
//        mRecyclerViewAdapter.notifyDataSetChanged();
//        getData("", "", "", "", "", "1", "0");
//    }
}