package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.OrderBean;
import mituo.wshoto.com.mituo.ui.activity.OrderDetailActivity;

/**
 * Created by Weshine on 2017/6/23.
 */

public class FinishedOrderAdapter extends RecyclerView.Adapter<FinishedOrderAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private List<OrderBean.ResultDataBean.ListBean> mData = new ArrayList<>();


    public void addAllData(List<OrderBean.ResultDataBean.ListBean> dataList) {
        this.mData.addAll(dataList);
        notifyDataSetChanged();
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void clearData() {
        this.mData.clear();
    }

    public FinishedOrderAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView car_num;
        TextView time;
        LinearLayout item;

        ViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.tv_finished_num);
            car_num = (TextView) view.findViewById(R.id.tv_finished_car_num);
            time = (TextView) view.findViewById(R.id.tv_finished_data_time);
            item = (LinearLayout) view.findViewById(R.id.ll_unfinished);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finished_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (mData != null) {

            RxView.clicks(viewHolder.item)
                    .throttleFirst(2, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        Intent intent = new Intent(mContext, OrderDetailActivity.class);
                        intent.putExtra("oid", mData.get(position).getOrderCode());
                        intent.putExtra("status", 1);
                        mContext.startActivity(intent);
                    });


            viewHolder.number.setText(String.format(mContext.getResources().getString(R.string.order_num), mData.get(position).getOrderCode()));
            viewHolder.car_num.setText(String.format(mContext.getResources().getString(R.string.card_num_edit), mData.get(position).getCarNo()));
            viewHolder.time.setText(String.format(mContext.getResources().getString(R.string.finish_time), mData.get(position).getTime()));
        }
        viewHolder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }


}