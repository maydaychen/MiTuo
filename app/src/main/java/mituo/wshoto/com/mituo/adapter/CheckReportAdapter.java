package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxCompoundButton;

import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.ReportBean;

/**
 * Created by user on 2017/7/17.
 */

public class CheckReportAdapter extends RecyclerView.Adapter<CheckReportAdapter.ViewHolder> {
    private List<ReportBean.ResultDataBean.Step2Bean.ListBean> mData;

    public CheckReportAdapter(List<ReportBean.ResultDataBean.Step2Bean.ListBean> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report_step3, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        vh.setIsRecyclable(false);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getBgxmName());
        if (mData.get(position).getBgxmValue().equals("2")) {
            viewHolder.repair.setChecked(true);
        }
        if (mData.get(position).getBgxmValue().equals("1")) {
            viewHolder.check.setChecked(true);
        }
        RxCompoundButton.checkedChanges(viewHolder.check)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mData.get(position).setBgxmValue("1");
                        viewHolder.repair.setChecked(false);
                    }
                });

        RxCompoundButton.checkedChanges(viewHolder.repair)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        mData.get(position).setBgxmValue("2");
                        viewHolder.check.setChecked(false);
                    }
                });

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
        public CheckBox check;
        public CheckBox repair;


        public ViewHolder(View view) {
            super(view);
            check = (CheckBox) view.findViewById(R.id.cb_check);
            repair = (CheckBox) view.findViewById(R.id.cb_repair);
            name = (TextView) view.findViewById(R.id.textView5);

        }
    }

    public List<ReportBean.ResultDataBean.Step2Bean.ListBean> getList() {
        return mData;
    }
}