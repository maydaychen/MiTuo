package mituo.wshoto.com.mituo.http;

import android.content.Context;
import android.widget.Toast;

import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 作者：JTR on 2016/11/25 14:54
 * 邮箱：2091320109@qq.com
 */
public class ProgressErrorSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener;
    private Context context;

    public ProgressErrorSubscriber(SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener, Context context) {
        this.mSubscriberOnNextAndErrorListener = mSubscriberOnNextAndErrorListener;
        this.context = context;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof UnknownHostException) {
            Toast.makeText(context, "服务器未响应，请稍后重试", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mSubscriberOnNextAndErrorListener.onError(e);
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextAndErrorListener.onNext(t);
    }
}