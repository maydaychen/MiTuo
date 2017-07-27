package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

/**
 * Created by Weshine on 2017/6/27.
 */

public class RepairObjLeftAdapter extends RecyclerView.Adapter<RepairObjLeftAdapter.ViewHolder> {
    private List<RepairObjsBean.ResultDataBean.TcListBean> mData;
    private Context mContext;

    public RepairObjLeftAdapter(List<RepairObjsBean.ResultDataBean.TcListBean> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repair_obj_up, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getTcName());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        viewHolder.objs.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        viewHolder.objs.setLayoutManager(layoutManager);
        RepairTaocanObjAdapter mRepairObjLeftAdapter = new RepairTaocanObjAdapter(mData.get(position).getTcxmList());
        viewHolder.objs.setAdapter(mRepairObjLeftAdapter);

        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RecyclerView objs;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_tc_name);
            objs = (RecyclerView) view.findViewById(R.id.rv_taocan);

        }
    }

    class RepairTaocanObjAdapter extends RecyclerView.Adapter<RepairTaocanObjAdapter.ViewHolder> {
        private List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData;

        public RepairTaocanObjAdapter(List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData) {
            this.mData = mData;
        }


        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_taocan, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.name.setText(mData.get(position).getXmName());
            if (mData.get(position).getIsZd().equals("0")) {
                viewHolder.objs.setText("用户自带");
            } else {
                if (null == mData.get(position).getPjName() || mData.get(position).getPjName().equals("")) {
                    viewHolder.objs.setText("--");
                } else {
                    viewHolder.objs.setText(mData.get(position).getPjName() + "*" + mData.get(position).getPjNum());
                }
            }

            viewHolder.itemView.setTag(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mData.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView objs;


            public ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tv_projects);
                objs = (TextView) view.findViewById(R.id.tv_objs);

            }
        }
    }
}