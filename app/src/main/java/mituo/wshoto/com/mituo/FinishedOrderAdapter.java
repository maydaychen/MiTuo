//package mituo.wshoto.com.mituo;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import mituo.wshoto.com.mituo.bean.FinishedOrderBean;
//
///**
// * Created by Weshine on 2017/6/7.
// */
//
//public class FinishedOrderAdapter extends RecyclerView.Adapter<FinishedOrderAdapter.ViewHolder> implements View.OnClickListener {
//    private FinishedOrderAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
//    private Context mContext;
//    private List<FinishedOrderBean.DataBean> mData = new ArrayList<>();
//
//
//    public void addAllData(List<FinishedOrderBean.DataBean> dataList) {
//        this.mData.addAll(dataList);
//        notifyDataSetChanged();
//    }
//
//    //define interface
//    public interface OnRecyclerViewItemClickListener {
//        void onItemClick(View view, int data);
//    }
//
//    public void clearData() {
//        this.mData.clear();
//    }
//
//    public FinishedOrderAdapter(Context context) {
//        mContext = context;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            //注意这里使用getTag方法获取数据
//            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
//        }
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        TextView number;
//        TextView data;
//        TextView price;
//        TextView username;
//        TextView pay_kind;
//        RelativeLayout item_order_list;
//
//        ViewHolder(View view) {
//            super(view);
//            number = (TextView) view.findViewById(R.id.tv_pay_num);
//            data = (TextView) view.findViewById(R.id.tv_pay_time);
//            price = (TextView) view.findViewById(R.id.tv_pay_price);
//            username = (TextView) view.findViewById(R.id.tv_payer_name);
//            pay_kind = (TextView) view.findViewById(R.id.tv_pay_kind);
//            item_order_list = (RelativeLayout) view.findViewById(R.id.item_pay_order);
//        }
//    }
//
//    @Override
//    public FinishedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finished_order, parent, false);
//        return new FinishedOrderAdapter.ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(FinishedOrderAdapter.ViewHolder viewHolder, final int position) {
//        if (mData != null) {
//
//            viewHolder.username.setText(String.format(mContext.getResources().getString(R.string.payer_name), mData.get(position).getPay_account()));
//            viewHolder.number.setText(String.format(mContext.getResources().getString(R.string.order_num), mData.get(position).getOrderid()));
//            viewHolder.data.setText(String.format(mContext.getResources().getString(R.string.pay_data), mData.get(position).getPaytime()));
//            viewHolder.price.setText(String.format(mContext.getResources().getString(R.string.money), mData.get(position).getMoney()));
//        }
//        viewHolder.itemView.setTag(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mData != null) {
//            return mData.size();
//        } else {
//            return 0;
//        }
//    }
//
//}