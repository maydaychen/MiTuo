package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;

/**
 * Created by user on 2017/7/4.
 */

public class RepairObjsDetailAdapter extends RecyclerView.Adapter<RepairObjsDetailAdapter.ViewHolder> {
    private RepairObjsBean.ResultDataBean mData;
    private Context mContext;
    public int num;

    public RepairObjsDetailAdapter(RepairObjsBean.ResultDataBean mData, Context context) {
        this.mData = mData;
        this.mContext = context;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repair_objs_setting, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if ((position + 1) <= mData.getTcList().size()) {
            viewHolder.name.setText(mData.getTcList().get(position).getTcName());
            viewHolder.ll_xm.setVisibility(View.GONE);
            viewHolder.itemView.setTag(position);
        } else {
            int mPositon = position - mData.getTcList().size();
            num = mData.getXmList().get(mPositon).getPjNum();
            viewHolder.name.setText(mData.getXmList().get(mPositon).getXmName());
            viewHolder.xm_name.setText(mData.getXmList().get(mPositon).getXmName());
            viewHolder.pj_num.setText(mData.getXmList().get(mPositon).getPjNum() + "");
            List<String> list = new ArrayList<>();
            list.add(mData.getXmList().get(mPositon).getPjName());
            list.add("客户自带");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item_text, R.id.tv_item_text, list);
            viewHolder.mSpinner.setAdapter(adapter);
            viewHolder.mSpinner.setPrompt("ceshi");
            viewHolder.itemView.setTag(position);
            if (num == 1) {
                viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                viewHolder.num_del.setClickable(false);
            }
            viewHolder.num_add.setOnClickListener(v -> {
                num++;
                if (num>1){
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus_normal);
                    viewHolder.num_del.setClickable(true);
                }
                mData.getXmList().get(mPositon).setPjNum(num);
                viewHolder.pj_num.setText(num + "");
            });
            viewHolder.num_del.setOnClickListener(v -> {
                num--;
                if (num == 1) {
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                    viewHolder.num_del.setClickable(false);
                }
                mData.getXmList().get(mPositon).setPjNum(num);
                viewHolder.pj_num.setText(num + "");
            });
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.getXmList().size() + mData.getTcList().size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView xm_name;
        TextView name;
        TextView pj_num;
        ImageView delete;
        ImageView num_del;
        ImageView num_add;
        RecyclerView taocan;
        LinearLayout ll_xm;
        Spinner mSpinner;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_objs_name);
            taocan = (RecyclerView) view.findViewById(R.id.rv_taocan);
            mSpinner = (Spinner) view.findViewById(R.id.sp_xm);
            xm_name = (TextView) view.findViewById(R.id.tv_xm);
            pj_num = (TextView) view.findViewById(R.id.tv_xm_num);
            delete = (ImageView) view.findViewById(R.id.iv_delete);
            num_del = (ImageView) view.findViewById(R.id.tv_xm_add);
            num_add = (ImageView) view.findViewById(R.id.tv_xm_minus);
            ll_xm = (LinearLayout) view.findViewById(R.id.ll_xm);
        }
    }
}