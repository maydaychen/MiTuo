package mituo.wshoto.com.mituo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mituo.wshoto.com.mituo.CheckMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.ReportBean;

/**
 * Created by user on 2017/7/11.
 */

public class ReportStep2Adapter extends RecyclerView.Adapter<ReportStep2Adapter.ViewHolder> {
    private List<ReportBean.ResultDataBean.Step2Bean.ListBean> mData;
    List<ReportBean.ResultDataBean.Step2Bean.ListBean> list = new CopyOnWriteArrayList<>();
    private ReportBean.ResultDataBean.Step2Bean.ListBean delList;
    private ReportBean.ResultDataBean.Step2Bean.ListBean addList;

    public ReportStep2Adapter(List<ReportBean.ResultDataBean.Step2Bean.ListBean> mData, List<ReportBean.ResultDataBean.Step2Bean.ListBean> listBeen) {
        this.mData = mData;
        this.list = listBeen;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report_step2, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        vh.setIsRecyclable(false);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mData.get(position).getBgxmName());
        Iterator<ReportBean.ResultDataBean.Step2Bean.ListBean> it = list.iterator();
        while (it.hasNext()) {
            ReportBean.ResultDataBean.Step2Bean.ListBean listBean = it.next();
            if (mData.get(position).getBgxmId() == listBean.getBgxmId()) {
                viewHolder.name.setChecked(true);
            }
        }
        viewHolder.name.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!isContain(list, mData.get(position).getBgxmId())) {
//                    list.add(mData.get(position));
                    addList = mData.get(position);
                }
                list.add(addList);
            } else {
                Iterator<ReportBean.ResultDataBean.Step2Bean.ListBean> it1 = list.iterator();
                while (it1.hasNext()) {
                    ReportBean.ResultDataBean.Step2Bean.ListBean listBean = it1.next();
                    if (mData.get(position).getBgxmId() == listBean.getBgxmId()) {
//                        list.remove(listBean);
                        delList = listBean;
                        break;
                    }
                }
                list.remove(delList);
//                for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : list2) {
//                    if (mData.get(position).getBgxmId() == listBean.getBgxmId()) {
//                        list.remove(listBean);
//                        CheckMessage msg = new CheckMessage(list);
//                        EventBus.getDefault().post(msg);
//                        return;
//                    }
//                }
            }
            CheckMessage msg = new CheckMessage(list);
            EventBus.getDefault().post(msg);
        });

        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox name;

        ViewHolder(View view) {
            super(view);
            name = (CheckBox) view.findViewById(R.id.cb_report);
        }
    }

    public List<ReportBean.ResultDataBean.Step2Bean.ListBean> getSelected() {
        return list;
    }

    public boolean isContain(List<ReportBean.ResultDataBean.Step2Bean.ListBean> list, int id) {
        for (ReportBean.ResultDataBean.Step2Bean.ListBean listBean : list) {
            if (listBean.getBgxmId() == id) {
                return true;
            }
        }
        return false;
    }
}