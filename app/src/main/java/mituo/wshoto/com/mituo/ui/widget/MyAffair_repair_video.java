package mituo.wshoto.com.mituo.ui.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.OrderMessage;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.StorageUtils;
import mituo.wshoto.com.mituo.bean.StorageBean;
import mituo.wshoto.com.mituo.ui.activity.RecordActivity;

import static mituo.wshoto.com.mituo.MemorySpaceCheck.fileIsExists;
import static mituo.wshoto.com.mituo.MemorySpaceCheck.getSDAvailableSize;
import static mituo.wshoto.com.mituo.Utils.changeTime;

/**
 * Created by Weshine on 2017/6/19.
 */

public class MyAffair_repair_video extends RelativeLayout {
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.textView4)
    TextView mTextView4;
    private Context mContext;

    private RelativeLayout titleRl;
    private ImageView iv_remind;
    private TextView tv_remind;
    private LinearLayout infoRl;

    private Button edit;
    private View baseV;
    private String orderNum;
    private int orderStatus;
    private boolean isOpened = false;
    private boolean isFileExist = false;
    private boolean animatorLock = false;
    public boolean IS_OK = true;
    //    private String path;
    private SharedPreferences preferences;

    public MyAffair_repair_video(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public MyAffair_repair_video(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAffair_repair_video(Context context) {
        this(context, null);
    }

    private void init() {
        baseV = LayoutInflater.from(mContext).inflate(R.layout.affair_repair_video, this);
        ButterKnife.bind(this);
        preferences = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
//        path = preferences.getString("path", Config.PATH_MOBILE);
        titleRl = (RelativeLayout) baseV.findViewById(R.id.title);
        infoRl = (LinearLayout) baseV.findViewById(R.id.affair_info_rl);
        iv_remind = (ImageView) baseV.findViewById(R.id.iv_remind);
        tv_remind = (TextView) baseV.findViewById(R.id.tv_remind);
        edit = (Button) baseV.findViewById(R.id.video_edit);
//        directionIv.setBackgroundResource(R.drawable.p7_2_001);
        titleRl.setOnClickListener(v -> {
            if (!animatorLock) {
                if (!isOpened) {
                    getObjectAnimator().start();
                    OrderMessage msg = new OrderMessage(3, isOpened);
                    EventBus.getDefault().post(msg);
                }
            }
        });
        edit.setOnClickListener(v -> {
            ArrayList<StorageBean> storageData = StorageUtils.getStorageData(mContext);
            if (preferences.getBoolean("is_inner", true)) {
                if (getSDAvailableSize() / 1048576 < 150) {
                    show();
                }
            } else {
                if (storageData.get(1).getAvailableSize() / 1048576 < 150) {
                    show();
                }
            }

            Intent intent = new Intent(mContext, RecordActivity.class);
            intent.putExtra("oid", orderNum);
            mContext.startActivity(intent);
        });
        mTextView2.setOnClickListener(v -> {
            Uri uri = Uri.parse(Config.PATH_MOBILE + "/" + orderNum + ".mp4");
            //调用系统自带的播放器
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Log.v("URI:::::::::", uri.toString());
            intent.setDataAndType(uri, "video/mp4");
            mContext.startActivity(intent);

        });
    }

    private ObjectAnimator getObjectAnimator() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
                if (isFileExist) {
                    if (!isOpened) {
                        infoRl.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (orderStatus != 1) {
                        if (!isOpened) {
                            edit.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFileExist) {
                    if (isOpened) {
                        infoRl.setVisibility(View.GONE);
                    }
                } else {
                    if (isOpened) {
                        edit.setVisibility(View.GONE);
                    }
                }
                isOpened = true;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        if (isFileExist) {
            if (!isOpened) {
                result = ObjectAnimator.ofFloat(infoRl, "scaleY", 0, 1f);
            } else {
                result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
            }
        } else {
            if (!isOpened) {
                result = ObjectAnimator.ofFloat(edit, "scaleY", 0, 1f);
            } else {
                result = ObjectAnimator.ofFloat(edit, "scaleY", 1f, 0);
            }
        }

        result.setDuration(300);
        result.addListener(changeStatusListener);
        return result;
    }

    public ObjectAnimator close() {
        Animator.AnimatorListener changeStatusListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                animatorLock = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFileExist) {
                    if (isOpened) {
                        infoRl.setVisibility(View.GONE);
                    }
                } else {
                    if (isOpened) {
                        edit.setVisibility(View.GONE);
                    }
                }
                isOpened = false;
                animatorLock = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        };
        ObjectAnimator result = null;
        if (isFileExist) {
            result = ObjectAnimator.ofFloat(infoRl, "scaleY", 1f, 0);
        } else {
            result = ObjectAnimator.ofFloat(edit, "scaleY", 1f, 0);
        }

        result.setDuration(300);
        result.addListener(changeStatusListener);
        return result;
    }

    public void setInfo(int status, String oid) {
        orderStatus = status;
        orderNum = oid;
        if (status == 1) {
            edit.setVisibility(GONE);
        }
        if (isFileExist = fileIsExists(Config.PATH_MOBILE + "/" + orderNum + ".mp4")) {
            edit.setVisibility(GONE);
            mTextView2.setText(String.format(getResources().getString(R.string.video_name), orderNum + ".mp4"));
            SpannableStringBuilder builder1 = new SpannableStringBuilder(mTextView2.getText().toString());
            ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.blue));
            builder1.setSpan(redSpan, 4, (orderNum + ".mp4").length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextView2.setText(builder1);
            try {
                mTextView3.setText(String.format(getResources().getString(R.string.video_time), changeTime(test2())));

                SpannableStringBuilder builder2 = new SpannableStringBuilder(mTextView3.getText().toString());
                ForegroundColorSpan fontSpan = new ForegroundColorSpan(getResources().getColor(R.color.font_99));
                builder2.setSpan(fontSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTextView3.setText(builder2);

                mTextView4.setText(String.format(getResources().getString(R.string.video_size), getsizeize(Config.PATH_MOBILE + "/" + orderNum + ".mp4")));
                SpannableStringBuilder builder3 = new SpannableStringBuilder(mTextView4.getText().toString());
                builder3.setSpan(fontSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTextView4.setText(builder3);
                IS_OK = true;
                iv_remind.setVisibility(GONE);
                tv_remind.setVisibility(GONE);
                if (isOpened) {
                    infoRl.setVisibility(VISIBLE);
                    infoRl.setScaleY(1f);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            IS_OK = false;
            iv_remind.setVisibility(VISIBLE);
            tv_remind.setVisibility(VISIBLE);
        }
    }


    private int test() throws IOException {
        String url = Config.PATH_MOBILE + "/" + orderNum + ".mp4";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        int a = mediaPlayer.getDuration();
        return a;
    }

    private double test2() {
        String url = Config.PATH_MOBILE + "/" + orderNum + ".mp4";
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(url);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double duration = player.getDuration();//获取音频的时间
        Log.d("ACETEST", "### duration: " + duration);
        player.release();//记得释放资源
        return duration;
    }


    private static String getsizeize(String filePath) throws Exception {
        File file = new File(filePath);
        long size = 0;
        FileInputStream fis = null;
        fis = new FileInputStream(file);
        size = fis.available();
        DecimalFormat df = new DecimalFormat("#.00");
        String sizeizeString;
        if (size < 1024) {
            sizeizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            sizeizeString = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            sizeizeString = df.format((double) size / 1048576) + "MB";
        } else {
            sizeizeString = df.format((double) size / 1073741824) + "GB";
        }
        return sizeizeString;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("存储空间不足，是否设置存储空间？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
//            mContext.startActivity(new Intent(mContext, StorageActivity.class));
            Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
            mContext.startActivity(intent);
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}