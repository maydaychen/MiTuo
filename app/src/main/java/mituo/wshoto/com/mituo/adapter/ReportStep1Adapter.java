package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.ReportBean;

/**
 * Created by user on 2017/7/10.
 */

public class ReportStep1Adapter extends RecyclerView.Adapter<ReportStep1Adapter.ViewHolder> {
    private List<ReportBean.ResultDataBean.Step1Bean> mData;

    public ReportStep1Adapter(List<ReportBean.ResultDataBean.Step1Bean> mData) {
        this.mData = mData;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report_step1, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getTypeName());
        List<EditText> list = new ArrayList<>();
        list.add(viewHolder.et1);
        list.add(viewHolder.et2);
        list.add(viewHolder.et3);
        list.add(viewHolder.et4);
        list.add(viewHolder.et5);
        list.add(viewHolder.et6);
        list.add(viewHolder.et7);
        list.add(viewHolder.et8);
        list.add(viewHolder.et9);
        list.add(viewHolder.et10);
        list.add(viewHolder.et11);
        list.add(viewHolder.et12);
        list.add(viewHolder.et13);
        list.add(viewHolder.et14);
        list.add(viewHolder.et15);
        list.add(viewHolder.et16);
        list.add(viewHolder.et17);
        list.add(viewHolder.et18);
        list.add(viewHolder.et19);
        list.add(viewHolder.et20);
        list.add(viewHolder.et21);
        list.add(viewHolder.et22);
        list.add(viewHolder.et23);
        list.add(viewHolder.et24);
        for (int i = 0; i < mData.get(position).getList().size(); i++) {
            list.get(i).setVisibility(View.VISIBLE);
            list.get(i).setHint(mData.get(position).getList().get(i).getBgxmName());
            list.get(i).setText(mData.get(position).getList().get(i).getBgxmValue());
            ReportBean.ResultDataBean.Step1Bean.ListBeanX listBeanX = mData.get(position).getList().get(i);
            RxTextView.textChanges(list.get(i)).subscribe(charSequence -> listBeanX.setBgxmValue(charSequence.toString()));
        }
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        EditText et1;
        EditText et2;
        EditText et3;
        EditText et4;
        EditText et5;
        EditText et6;
        EditText et7;
        EditText et8;
        EditText et9;
        EditText et10;
        EditText et11;
        EditText et12;
        EditText et13;
        EditText et14;
        EditText et15;
        EditText et16;
        EditText et17;
        EditText et18;
        EditText et19;
        EditText et20;
        EditText et21;
        EditText et22;
        EditText et23;
        EditText et24;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_porject_name);
            et1 = (EditText) view.findViewById(R.id.et_1);
            et2 = (EditText) view.findViewById(R.id.et_2);
            et3 = (EditText) view.findViewById(R.id.et_3);
            et4 = (EditText) view.findViewById(R.id.et_4);
            et5 = (EditText) view.findViewById(R.id.et_5);
            et6 = (EditText) view.findViewById(R.id.et_6);
            et7 = (EditText) view.findViewById(R.id.et_7);
            et8 = (EditText) view.findViewById(R.id.et_8);
            et9 = (EditText) view.findViewById(R.id.et_9);
            et10 = (EditText) view.findViewById(R.id.et_10);
            et11 = (EditText) view.findViewById(R.id.et_11);
            et12 = (EditText) view.findViewById(R.id.et_12);
            et13 = (EditText) view.findViewById(R.id.et_13);
            et14 = (EditText) view.findViewById(R.id.et_14);
            et15 = (EditText) view.findViewById(R.id.et_15);
            et16 = (EditText) view.findViewById(R.id.et_16);
            et17 = (EditText) view.findViewById(R.id.et_17);
            et18 = (EditText) view.findViewById(R.id.et_18);
            et19 = (EditText) view.findViewById(R.id.et_19);
            et20 = (EditText) view.findViewById(R.id.et_20);
            et21 = (EditText) view.findViewById(R.id.et_21);
            et22 = (EditText) view.findViewById(R.id.et_22);
            et23 = (EditText) view.findViewById(R.id.et_23);
            et24 = (EditText) view.findViewById(R.id.et_24);
        }
    }

    public List<ReportBean.ResultDataBean.Step1Bean> getList() {
        return mData;
    }
}