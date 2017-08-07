package mituo.wshoto.com.mituo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.Utils;
import mituo.wshoto.com.mituo.bean.PicBean;

/**
 * Created by Weshine on 2017/6/22.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<PicBean.ResultDataBean> mData;
    private int status;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public PhotosAdapter(List<PicBean.ResultDataBean> mData, int status, Context context) {
        this.mContext = context;
        this.mData = mData;
        this.status = status;
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photos, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (status == 0) {
            if (position == mData.size()) {
                viewHolder.name.setImageResource(R.drawable.order_details_btn_add_img);
            } else {
                viewHolder.name.setImageBitmap(Utils.stringtoBitmap(mData.get(position).getBase64()));
            }
        } else {
            viewHolder.name.setImageBitmap(Utils.stringtoBitmap(mData.get(position).getBase64()));
        }
        viewHolder.name.setOnClickListener(v -> {
            if (position == mData.size()) {
                OrderMessage msg = new OrderMessage(10,false);
                EventBus.getDefault().post(msg);
            }
        });
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (status == 0) {
            return mData.size() + 1;
        } else {
            return mData.size();
        }
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
        public ImageView name;


        public ViewHolder(View view) {
            super(view);
            name = (ImageView) view.findViewById(R.id.iv_photo);
        }
    }
}