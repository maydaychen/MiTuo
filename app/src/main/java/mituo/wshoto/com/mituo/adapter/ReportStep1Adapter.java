package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context context;

    public ReportStep1Adapter(List<ReportBean.ResultDataBean.Step1Bean> mData, Context mContext) {
        this.mData = mData;
        this.context = mContext;
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
        List<AutoCompleteTextView> list = new ArrayList<>();
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
        List<TextInputLayout> head_list = new ArrayList<>();
        head_list.add(viewHolder.head1);
        head_list.add(viewHolder.head2);
        head_list.add(viewHolder.head3);
        head_list.add(viewHolder.head4);
        head_list.add(viewHolder.head5);
        head_list.add(viewHolder.head6);
        head_list.add(viewHolder.head7);
        head_list.add(viewHolder.head8);
        head_list.add(viewHolder.head9);
        head_list.add(viewHolder.head10);
        head_list.add(viewHolder.head11);
        head_list.add(viewHolder.head12);
        head_list.add(viewHolder.head13);
        head_list.add(viewHolder.head14);
        head_list.add(viewHolder.head15);
        head_list.add(viewHolder.head16);
        head_list.add(viewHolder.head17);
        head_list.add(viewHolder.head18);
        head_list.add(viewHolder.head19);
        head_list.add(viewHolder.head20);
        head_list.add(viewHolder.head21);
        head_list.add(viewHolder.head22);
        head_list.add(viewHolder.head23);
        head_list.add(viewHolder.head24);
        for (int i = 0; i < mData.get(position).getList().size(); i++) {
            head_list.get(i).setVisibility(View.VISIBLE);
            head_list.get(i).setHint(mData.get(position).getList().get(i).getBgxmName());
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
        AutoCompleteTextView et1;
        AutoCompleteTextView et2;
        AutoCompleteTextView et3;
        AutoCompleteTextView et4;
        AutoCompleteTextView et5;
        AutoCompleteTextView et6;
        AutoCompleteTextView et7;
        AutoCompleteTextView et8;
        AutoCompleteTextView et9;
        AutoCompleteTextView et10;
        AutoCompleteTextView et11;
        AutoCompleteTextView et12;
        AutoCompleteTextView et13;
        AutoCompleteTextView et14;
        AutoCompleteTextView et15;
        AutoCompleteTextView et16;
        AutoCompleteTextView et17;
        AutoCompleteTextView et18;
        AutoCompleteTextView et19;
        AutoCompleteTextView et20;
        AutoCompleteTextView et21;
        AutoCompleteTextView et22;
        AutoCompleteTextView et23;
        AutoCompleteTextView et24;
        TextInputLayout head1;
        TextInputLayout head2;
        TextInputLayout head3;
        TextInputLayout head4;
        TextInputLayout head5;
        TextInputLayout head6;
        TextInputLayout head7;
        TextInputLayout head8;
        TextInputLayout head9;
        TextInputLayout head10;
        TextInputLayout head11;
        TextInputLayout head12;
        TextInputLayout head13;
        TextInputLayout head14;
        TextInputLayout head15;
        TextInputLayout head16;
        TextInputLayout head17;
        TextInputLayout head18;
        TextInputLayout head19;
        TextInputLayout head20;
        TextInputLayout head21;
        TextInputLayout head22;
        TextInputLayout head23;
        TextInputLayout head24;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_porject_name);
            et1 = (AutoCompleteTextView) view.findViewById(R.id.et_1);
            et2 = (AutoCompleteTextView) view.findViewById(R.id.et_2);
            et3 = (AutoCompleteTextView) view.findViewById(R.id.et_3);
            et4 = (AutoCompleteTextView) view.findViewById(R.id.et_4);
            et5 = (AutoCompleteTextView) view.findViewById(R.id.et_5);
            et6 = (AutoCompleteTextView) view.findViewById(R.id.et_6);
            et7 = (AutoCompleteTextView) view.findViewById(R.id.et_7);
            et8 = (AutoCompleteTextView) view.findViewById(R.id.et_8);
            et9 = (AutoCompleteTextView) view.findViewById(R.id.et_9);
            et10 = (AutoCompleteTextView) view.findViewById(R.id.et_10);
            et11 = (AutoCompleteTextView) view.findViewById(R.id.et_11);
            et12 = (AutoCompleteTextView) view.findViewById(R.id.et_12);
            et13 = (AutoCompleteTextView) view.findViewById(R.id.et_13);
            et14 = (AutoCompleteTextView) view.findViewById(R.id.et_14);
            et15 = (AutoCompleteTextView) view.findViewById(R.id.et_15);
            et16 = (AutoCompleteTextView) view.findViewById(R.id.et_16);
            et17 = (AutoCompleteTextView) view.findViewById(R.id.et_17);
            et18 = (AutoCompleteTextView) view.findViewById(R.id.et_18);
            et19 = (AutoCompleteTextView) view.findViewById(R.id.et_19);
            et20 = (AutoCompleteTextView) view.findViewById(R.id.et_20);
            et21 = (AutoCompleteTextView) view.findViewById(R.id.et_21);
            et22 = (AutoCompleteTextView) view.findViewById(R.id.et_22);
            et23 = (AutoCompleteTextView) view.findViewById(R.id.et_23);
            et24 = (AutoCompleteTextView) view.findViewById(R.id.et_24);
            head1 = (TextInputLayout) view.findViewById(R.id.head_1);
            head2 = (TextInputLayout) view.findViewById(R.id.head_2);
            head3 = (TextInputLayout) view.findViewById(R.id.head_3);
            head4 = (TextInputLayout) view.findViewById(R.id.head_4);
            head5 = (TextInputLayout) view.findViewById(R.id.head_5);
            head6 = (TextInputLayout) view.findViewById(R.id.head_6);
            head7 = (TextInputLayout) view.findViewById(R.id.head_7);
            head8 = (TextInputLayout) view.findViewById(R.id.head_8);
            head9 = (TextInputLayout) view.findViewById(R.id.head_9);
            head10 = (TextInputLayout) view.findViewById(R.id.head_10);
            head11 = (TextInputLayout) view.findViewById(R.id.head_11);
            head12 = (TextInputLayout) view.findViewById(R.id.head_12);
            head13 = (TextInputLayout) view.findViewById(R.id.head_13);
            head14 = (TextInputLayout) view.findViewById(R.id.head_14);
            head15 = (TextInputLayout) view.findViewById(R.id.head_15);
            head16 = (TextInputLayout) view.findViewById(R.id.head_16);
            head17 = (TextInputLayout) view.findViewById(R.id.head_17);
            head18 = (TextInputLayout) view.findViewById(R.id.head_18);
            head19 = (TextInputLayout) view.findViewById(R.id.head_19);
            head20 = (TextInputLayout) view.findViewById(R.id.head_20);
            head21 = (TextInputLayout) view.findViewById(R.id.head_21);
            head22 = (TextInputLayout) view.findViewById(R.id.head_22);
            head23 = (TextInputLayout) view.findViewById(R.id.head_23);
            head24 = (TextInputLayout) view.findViewById(R.id.head_24);
        }
    }

    public List<ReportBean.ResultDataBean.Step1Bean> getList() {
        for (ReportBean.ResultDataBean.Step1Bean step1Bean : mData) {
            for (ReportBean.ResultDataBean.Step1Bean.ListBeanX listBeanX : step1Bean.getList()) {
                if (null == listBeanX.getBgxmValue() || listBeanX.getBgxmValue().equals("")) {
                    Toast.makeText(context, "请填写完整", Toast.LENGTH_SHORT).show();
                    List<ReportBean.ResultDataBean.Step1Bean> err = new ArrayList<>();
                    return err;
                }
            }
        }
        return mData;
    }
}