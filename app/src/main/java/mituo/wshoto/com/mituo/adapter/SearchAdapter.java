package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.SearchBean;

/**
 * Created by user on 2017/7/19.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;
    private List<SearchBean.ResultDataBean.ListBean> mData;
    private String mKey;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public SearchAdapter(Context context, List<SearchBean.ResultDataBean.ListBean> mdata, String key) {
        this.mContext = context;
        mData = mdata;
        this.mKey = key;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.enterprise_programe_num.setText(mData.get(position).getCreateTime());
        if (mData.get(position).getOrderCode().contains(mKey)) {
            viewHolder.enterprise_name.setText(mData.get(position).getOrderCode());
            ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.font_red));
            SpannableStringBuilder builder1 = new SpannableStringBuilder(viewHolder.enterprise_name.getText().toString());
            builder1.setSpan(redSpan, mData.get(position).getOrderCode().indexOf(mKey), mData.get(position).getOrderCode().indexOf(mKey) + mKey.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.enterprise_name.setText(builder1);
            viewHolder.type.setText("订单编号");
        } else {
            viewHolder.enterprise_name.setText(mData.get(position).getCarNo());
            ForegroundColorSpan redSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.font_red));
            SpannableStringBuilder builder1 = new SpannableStringBuilder(viewHolder.enterprise_name.getText().toString());
            builder1.setSpan(redSpan, mData.get(position).getCarNo().indexOf(mKey), mData.get(position).getCarNo().indexOf(mKey) + mKey.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.enterprise_name.setText(builder1);
            viewHolder.type.setText("车牌号码");
        }
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView enterprise_name;
        TextView enterprise_programe_num;
        TextView type;

        public ViewHolder(View view) {
            super(view);
            enterprise_name = (TextView) view.findViewById(R.id.tv_name);
            enterprise_programe_num = (TextView) view.findViewById(R.id.tv_date);
            type = (TextView) view.findViewById(R.id.tv_type);
        }
    }

}
