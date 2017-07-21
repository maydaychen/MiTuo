package mituo.wshoto.com.mituo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.AllRepairBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.ui.widget.RecycleViewDivider;

/**
 * Created by user on 2017/7/4.
 */

public class RepairObjsDetailAdapter extends RecyclerView.Adapter<RepairObjsDetailAdapter.ViewHolder> {
    private RepairObjsBean.ResultDataBean mData;
    private Map<String, ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>> peijianList;
    private Context mContext;

    public RepairObjsDetailAdapter(RepairObjsBean.ResultDataBean mData, Map<String, ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>>> mPeijianList, Context context) {
        this.mData = mData;
        this.mContext = context;
        this.peijianList = mPeijianList;
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
        viewHolder.delete.setOnClickListener(v -> show(position));
        if ((position + 1) <= mData.getTcList().size()) {
            viewHolder.name.setText(mData.getTcList().get(position).getTcName());
            viewHolder.ll_xm.setVisibility(View.GONE);
            viewHolder.taocan.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
            viewHolder.taocan.setLayoutManager(new LinearLayoutManager(mContext));
            if (mData.getTcList().get(position).getTcName().contains("保养")) {
                viewHolder.taocan.setAdapter(new RepairCareTaocanObjAdapter(mData.getTcList().get(position).getTcxmList()));
            } else {
                viewHolder.taocan.setAdapter(new RepairTaocanObjAdapter(mData.getTcList().get(position).getTcxmList(), peijianList.get(mData.getTcList().get(position).getTcName())));
            }
            viewHolder.itemView.setTag(position);
        } else {
            int mPositon = position - mData.getTcList().size();
            viewHolder.name.setText(mData.getXmList().get(mPositon).getXmName());
            viewHolder.xm_name.setText(mData.getXmList().get(mPositon).getXmName());
            if (null == mData.getXmList().get(mPositon).getPjNum()) {
                viewHolder.pj_num.setText("1");
                mData.getXmList().get(mPositon).setPjNum("1");
            } else {
                viewHolder.pj_num.setText(mData.getXmList().get(mPositon).getPjNum() + "");
            }
            List<String> list = new ArrayList<>();
            try {
                for (AllRepairBean.ResultDataBean.PjListBean pjListBean : peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0)) {
                    list.add(pjListBean.getPjpp() + pjListBean.getPjName());
                }
            } catch (IndexOutOfBoundsException ignored) {
            } catch (NullPointerException ignored) {
            }
            if (mData.getXmList().get(mPositon).getIsCanZidai().equals("0")) {
                list.add("客户自带");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item_text, R.id.tv_item_text, list);
            viewHolder.mSpinner.setAdapter(adapter);
            for (int i = 0; i < adapter.getCount(); i++) {
                if ((mData.getXmList().get(mPositon).getPjpp() + mData.getXmList().get(mPositon).getPjName()).equals(adapter.getItem(i).toString())) {
                    viewHolder.mSpinner.setSelection(i, true);
                    break;
                }
            }

            viewHolder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (mData.getXmList().get(mPositon).getIsCanZidai().equals("0")) {
                        if (position == adapter.getCount() - 1) {
                            viewHolder.num_del.setVisibility(View.GONE);
                            viewHolder.num_add.setVisibility(View.GONE);
                            mData.getXmList().get(mPositon).setIsZd("0");
                            mData.getXmList().get(mPositon).setPjName("");
                            mData.getXmList().get(mPositon).setPjNum("1");
                            viewHolder.pj_num.setText("1");
                            mData.getXmList().get(mPositon).setPjpp("");
                            mData.getXmList().get(mPositon).setPjPrice("");
                            mData.getXmList().get(mPositon).setPjCode("");
                        } else {
                            mData.getXmList().get(mPositon).setPjName(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjName());
                            mData.getXmList().get(mPositon).setPjNum(mData.getXmList().get(mPositon).getPjNum());
                            if (viewHolder.pj_num.getText().equals("1")) {
                                viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                                viewHolder.num_del.setClickable(false);
                            }
                            mData.getXmList().get(mPositon).setPjpp(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjpp());
                            mData.getXmList().get(mPositon).setPjPrice(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjPrice());
                            mData.getXmList().get(mPositon).setPjCode(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjCode());
                        }
                    } else {
                        mData.getXmList().get(mPositon).setPjName(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjName());
                        mData.getXmList().get(mPositon).setPjNum(mData.getXmList().get(mPositon).getPjNum());
                        mData.getXmList().get(mPositon).setPjpp(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjpp());
                        mData.getXmList().get(mPositon).setPjPrice(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjPrice());
                        mData.getXmList().get(mPositon).setPjCode(peijianList.get(mData.getXmList().get(mPositon).getXmName()).get(0).get(position).getPjCode());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            viewHolder.mSpinner.setPrompt("ceshi");
            viewHolder.itemView.setTag(position);
            if (Integer.valueOf(mData.getXmList().get(mPositon).getPjNum()) == 1) {
                viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                viewHolder.num_del.setClickable(false);
            }
            viewHolder.num_add.setOnClickListener(v -> {
                int a = Integer.valueOf(mData.getXmList().get(mPositon).getPjNum());
                a++;
                if (a > 1) {
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus_normal);
                    viewHolder.num_del.setClickable(true);
                }
                mData.getXmList().get(mPositon).setPjNum(a + "");
                viewHolder.pj_num.setText(a + "");
            });
            viewHolder.num_del.setOnClickListener(v -> {
                int a = Integer.valueOf(mData.getXmList().get(mPositon).getPjNum());
                if (a > 1) {
                    a--;
                }
                if (a == 1) {
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                    viewHolder.num_del.setClickable(false);
                }
                mData.getXmList().get(mPositon).setPjNum(a + "");
                viewHolder.pj_num.setText(a + "");
            });

        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (null == mData) {
            return 0;
        }
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

    public void show(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("确认删除该保养么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
            if ((position + 1) <= mData.getTcList().size()) {
                mData.getTcList().remove(position);
            } else {
                mData.getXmList().remove(position - mData.getTcList().size());
            }
            notifyDataSetChanged();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    class RepairCareTaocanObjAdapter extends RecyclerView.Adapter<RepairCareTaocanObjAdapter.ViewHolder> {
        private List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData;

        public RepairCareTaocanObjAdapter(List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData) {
            this.mData = mData;
        }


        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_care_taocan_objs, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.name.setText(mData.get(position).getXmName());
            viewHolder.pj_num.setText(mData.get(position).getPjNum() + "");
            viewHolder.mSpinner.setText(mData.get(position).getPjpp() + mData.get(position).getPjName());
            viewHolder.itemView.setTag(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mData.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView mSpinner;
            TextView pj_num;

            ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tv_xm);
                mSpinner = (TextView) view.findViewById(R.id.sp_xm);
                pj_num = (TextView) view.findViewById(R.id.tv_xm_num);

            }
        }
    }

    class RepairTaocanObjAdapter extends RecyclerView.Adapter<RepairTaocanObjAdapter.ViewHolder> {
        private List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData;
        private ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>> mlistBeen = new ArrayList<>();

        public RepairTaocanObjAdapter(List<RepairObjsBean.ResultDataBean.TcListBean.TcxmListBean> mData, ArrayList<ArrayList<AllRepairBean.ResultDataBean.PjListBean>> listBeen) {
            this.mData = mData;
            this.mlistBeen = listBeen;
        }


        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_taocan_objs, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }


        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.name.setText(mData.get(position).getXmName());
            viewHolder.pj_num.setText(mData.get(position).getPjNum() + "");
            int mPositon = position;
            if (null == mData.get(position).getPjNum()) {
                viewHolder.pj_num.setText("1");
                mData.get(position).setPjNum("1");
            } else {
                viewHolder.pj_num.setText(mData.get(position).getPjNum() + "");
            }
            if (Integer.valueOf(mData.get(position).getPjNum()) <= 1) {
                viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                viewHolder.num_del.setClickable(false);
            }
            List<String> list = new ArrayList<>();
            try {
                if (mlistBeen.size() != 0) {
                    for (AllRepairBean.ResultDataBean.PjListBean pjListBean : mlistBeen.get(position)) {
                        list.add(pjListBean.getPjpp() + pjListBean.getPjName());
                    }
                }
            } catch (NullPointerException ignored) {
            }
            if (mData.get(position).getIsCanZidai().equals("0")) {
                list.add("客户自带");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item_text, R.id.tv_item_text, list);
            viewHolder.mSpinner.setAdapter(adapter);
            for (int i = 0; i < adapter.getCount(); i++) {
                if ((mData.get(position).getPjpp() + mData.get(position).getPjName()).equals(adapter.getItem(i).toString())) {
                    viewHolder.mSpinner.setSelection(i, true);
                    break;
                }
            }
            viewHolder.pj_num.setText(mData.get(position).getPjNum());
            viewHolder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (mData.get(mPositon).getIsCanZidai().equals("0")) {
                        if (position == adapter.getCount() - 1) {
                            viewHolder.num_del.setVisibility(View.GONE);
                            viewHolder.num_add.setVisibility(View.GONE);
                            mData.get(mPositon).setIsZd("0");
                            mData.get(mPositon).setPjName("");
                            mData.get(mPositon).setPjNum("1");
                            viewHolder.pj_num.setText("1");
                            mData.get(mPositon).setPjpp("");
                            mData.get(mPositon).setPjPrice("");
                            mData.get(mPositon).setPjCode("");
                        } else {
                            viewHolder.num_del.setVisibility(View.VISIBLE);
                            viewHolder.num_add.setVisibility(View.VISIBLE);
                            if (viewHolder.pj_num.getText().equals("1")) {
                                viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                                viewHolder.num_del.setClickable(false);
                            }
                            mData.get(mPositon).setPjName(mlistBeen.get(mPositon).get(position).getPjName());
                            mData.get(mPositon).setPjNum(mData.get(mPositon).getPjNum());
                            mData.get(mPositon).setPjpp(mlistBeen.get(mPositon).get(position).getPjpp());
                            mData.get(mPositon).setPjPrice(mlistBeen.get(mPositon).get(position).getPjPrice());
                            mData.get(mPositon).setPjCode(mlistBeen.get(mPositon).get(position).getPjCode());
                        }
                    } else {
                        viewHolder.num_del.setVisibility(View.VISIBLE);
                        viewHolder.num_add.setVisibility(View.VISIBLE);
                        if (viewHolder.pj_num.getText().equals("1")) {
                            viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                            viewHolder.num_del.setClickable(false);
                        }
                        mData.get(mPositon).setPjName(mlistBeen.get(mPositon).get(position).getPjName());
                        mData.get(mPositon).setPjNum(mData.get(mPositon).getPjNum());
                        mData.get(mPositon).setPjpp(mlistBeen.get(mPositon).get(position).getPjpp());
                        mData.get(mPositon).setPjPrice(mlistBeen.get(mPositon).get(position).getPjPrice());
                        mData.get(mPositon).setPjCode(mlistBeen.get(mPositon).get(position).getPjCode());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            viewHolder.mSpinner.setPrompt("ceshi");
            viewHolder.num_add.setOnClickListener(v -> {
                int a = Integer.valueOf(mData.get(position).getPjNum());
                a++;
                if (a > 1) {
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus_normal);
                    viewHolder.num_del.setClickable(true);
                }
                mData.get(mPositon).setPjNum(a + "");
                viewHolder.pj_num.setText(a + "");
            });
            viewHolder.num_del.setOnClickListener(v -> {
                int a = Integer.valueOf(mData.get(position).getPjNum());
                if (a > 1) {
                    a--;
                }
                if (a == 1) {
                    viewHolder.num_del.setImageResource(R.drawable.maintenance_project_icon_minus);
                    viewHolder.num_del.setClickable(false);
                }
                mData.get(mPositon).setPjNum(a + "");
                viewHolder.pj_num.setText(a + "");
            });

            viewHolder.itemView.setTag(position);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return mData.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            Spinner mSpinner;
            TextView pj_num;
            ImageView num_del;
            ImageView num_add;

            ViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tv_xm);
                mSpinner = (Spinner) view.findViewById(R.id.sp_xm);
                pj_num = (TextView) view.findViewById(R.id.tv_xm_num);
                num_del = (ImageView) view.findViewById(R.id.tv_tc_add);
                num_add = (ImageView) view.findViewById(R.id.tv_tc_minus);
            }
        }

    }

    public RepairObjsBean.ResultDataBean getData() {
        return mData;
    }
}