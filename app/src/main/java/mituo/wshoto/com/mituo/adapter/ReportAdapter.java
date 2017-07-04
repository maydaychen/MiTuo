package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mituo.wshoto.com.mituo.R;

/**
 * Created by user on 2017/7/3.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<String> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public ReportAdapter(List<String> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position));
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
        public TextView name;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_item_text);
        }
    }
}