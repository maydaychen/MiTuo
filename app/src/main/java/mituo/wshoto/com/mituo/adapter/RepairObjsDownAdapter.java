package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;

/**
 * Created by user on 2017/7/4.
 *
 */

public class RepairObjsDownAdapter extends RecyclerView.Adapter<RepairObjsDownAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<RepairObjsBean.ResultDataBean.XmListBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public RepairObjsDownAdapter(List<RepairObjsBean.ResultDataBean.XmListBean> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repair_objs, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.projects.setText(mData.get(position).getXmName());
        viewHolder.name.setText(mData.get(position).getXmName());
        viewHolder.objs.setText(mData.get(position).getPjpp() + mData.get(position).getPjName());
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
        public TextView projects;
        public TextView name;
        public TextView objs;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_projects);
            objs = (TextView) view.findViewById(R.id.tv_objs);
            projects = (TextView) view.findViewById(R.id.tv_repair_objs);

        }
    }
}