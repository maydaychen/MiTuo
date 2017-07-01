package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import mituo.wshoto.com.mituo.R;

/**
 * Created by user on 2017/6/29.
 */

public class GatherAdapter extends RecyclerView.Adapter<GatherAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<Map<String, String>> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public GatherAdapter(List<Map<String, String>> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gather, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.project.setText(mData.get(position).get("name"));
        viewHolder.peijian.setText(mData.get(position).get("peijian"));
        viewHolder.num.setText(mData.get(position).get("num"));
        viewHolder.price.setText(mData.get(position).get("price"));
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
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView project;
        TextView peijian;
        TextView num;
        TextView price;


        ViewHolder(View view) {
            super(view);
            project = (TextView) view.findViewById(R.id.tv_project);
            peijian = (TextView) view.findViewById(R.id.tv_peijian);
            num = (TextView) view.findViewById(R.id.tv_num);
            price = (TextView) view.findViewById(R.id.tv_price);
        }
    }
}