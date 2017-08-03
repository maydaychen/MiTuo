package mituo.wshoto.com.mituo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mituo.wshoto.com.mituo.Config;
import mituo.wshoto.com.mituo.R;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.http.HttpMethods;
import mituo.wshoto.com.mituo.http.ProgressSubscriber;
import mituo.wshoto.com.mituo.http.SubscriberOnNextListener;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static mituo.wshoto.com.mituo.Utils.LongToDate;

public class RecordActivity extends Activity implements SurfaceHolder.Callback {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    @BindView(R.id.iv_start)
    ImageButton mIvStart;
    @BindView(R.id.ll_result)
    LinearLayout mLlResult;
    private SubscriberOnNextListener<ResultBean> startOnNext;
    private SubscriberOnNextListener<ResultBean> endOnNext;
    private String orderNumm;

    //    private Button start;// 开始录制按钮
//    private Button stop;// 停止录制按钮
    private boolean IS_RECORDING = false;
    private Camera mCamera;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private MediaRecorder mediarecorder;// 录制视频的类
    private SurfaceView surfaceview;// 显示视频的控件
    private String start_time;
    private String end_time;
    private boolean HAVA_TIME = false;
    // 用来显示视频的一个接口，我靠不用还不行，也就是说用mediarecorder录制视频还得给个界面看
    // 想偷偷录视频的同学可以考虑别的办法。。嗯需要实现这个接口的Callback接口
    private SurfaceHolder surfaceHolder;
    private Camera.Parameters cameraParams;
    private Camera.AutoFocusCallback myAutoFocusCallback = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 设置横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();
        init();
    }

    private void init() {
//        start = (Button) this.findViewById(R.id.start);
//        stop = (Button) this.findViewById(R.id.stop);
//        start.setOnClickListener(new TestVideoListener());
//        stop.setOnClickListener(new TestVideoListener());
        myAutoFocusCallback = (success, camera) -> {
            if (success) {
                camera.setOneShotPreviewCallback(null);
            }
        };
        orderNumm = getIntent().getStringExtra("oid");
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        startOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                editor.putBoolean(orderNumm + "start", true);
                editor.apply();
            } else {
                editor.putBoolean(orderNumm + "start", false);
                editor.apply();
            }
        };
        endOnNext = resultBean -> {
            if (resultBean.getCode().equals("200")) {
                editor.putBoolean(orderNumm + "end", true);
                editor.apply();
            } else {
                editor.putBoolean(orderNumm + "end", false);
                editor.apply();
            }
        };
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;

        try {
            initCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        surfaceview = null;
        surfaceHolder = null;
        mediarecorder = null;
        freeCameraResource();
    }

    private void record() {
        IS_RECORDING = true;
        mIvStart.setImageResource(R.drawable.ic_pause_white_24dp);
        mediarecorder = new MediaRecorder();// 创建mediarecorder对象
        // 设置录制视频源为Camera(相机)

        if (mCamera != null)
            mediarecorder.setCamera(mCamera);
        mediarecorder.setOnErrorListener((mr, what, extra) -> {
            try {
                if (mr != null)
                    mr.reset();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        // 设置录制的视频编码h263 h264
        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
        mediarecorder.setVideoSize(176, 144);
        // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
        mediarecorder.setVideoFrameRate(20);
        mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
        // 设置视频文件输出的路径
        Log.d("video", "record: " + Environment.getExternalStorageDirectory().getPath());
        String path = preferences.getString("path", Config.PATH_MOBILE);
        File destDir = new File(path);
        if (!destDir.exists()) {
            Toast.makeText(this, destDir.mkdirs() + "", Toast.LENGTH_SHORT).show();
        }
        mediarecorder.setOutputFile(path + "/" + orderNumm + ".mp4");
        try {
            // 准备录制
            mediarecorder.prepare();
            // 开始录制
            mediarecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                record();
            } else {
                //用户不同意，自行处理即可
                finish();
            }
        }
    }

    @OnClick({R.id.iv_start, R.id.iv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_start:
                long time = System.currentTimeMillis();
                time += preferences.getLong("sys_time", 1);
                if (!IS_RECORDING) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, READ_EXTERNAL_STORAGE, CAMERA},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else {
                            record();
                        }
                    } else record();
                    start_time = LongToDate(time);
                    editor.putString(orderNumm + "startTime", start_time);
                    editor.putBoolean(orderNumm + "start", false);
                    if (editor.commit()) {
                        HttpMethods.getInstance().start_record(
                                new ProgressSubscriber<>(startOnNext, this), preferences.getString("token", ""),
                                getIntent().getStringExtra("oid"), LongToDate(time));
                    }
                } else {
                    end_time = LongToDate(time);
                    editor.putString(orderNumm + "endTime", end_time);
                    editor.putBoolean(orderNumm + "end", false);
                    if (editor.commit()) {
                        HttpMethods.getInstance().end_record(
                                new ProgressSubscriber<>(endOnNext, this), preferences.getString("token", ""),
                                getIntent().getStringExtra("oid"), LongToDate(time));
                    }
                    freeCameraResource();
                    mLlResult.setVisibility(View.VISIBLE);
                    mIvStart.setVisibility(View.GONE);
                    IS_RECORDING = false;
                    mIvStart.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                    if (mediarecorder != null) {
                        // 停止录制
                        mediarecorder.stop();
                        // 释放资源
                        mediarecorder.release();
                        mediarecorder = null;
                    }
                }
                break;
            case R.id.iv_confirm:
                finish();
                break;
        }
    }

    private void initCamera() throws IOException {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            mCamera = Camera.open();
            cameraParams = mCamera.getParameters();
            cameraParams.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//            mCamera.autoFocus(myAutoFocusCallback);


        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;
        // setCameraParams();
        mCamera.setPreviewDisplay(surfaceHolder);
        mCamera.startPreview();
        mCamera.unlock();
    }

    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

}