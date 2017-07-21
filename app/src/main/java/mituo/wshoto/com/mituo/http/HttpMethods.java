package mituo.wshoto.com.mituo.http;

import java.util.concurrent.TimeUnit;

import mituo.wshoto.com.mituo.bean.AllRepairBean;
import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.bean.CouponBean;
import mituo.wshoto.com.mituo.bean.EmsBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.LoginBean;
import mituo.wshoto.com.mituo.bean.OrderBean;
import mituo.wshoto.com.mituo.bean.OrderInfoBean;
import mituo.wshoto.com.mituo.bean.PayStatusBean;
import mituo.wshoto.com.mituo.bean.PicBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.bean.ReportBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.bean.SearchBean;
import mituo.wshoto.com.mituo.bean.TimeBean;
import mituo.wshoto.com.mituo.bean.WeixinBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：JTR on 2016/11/25 10:18
 * 邮箱：2091320109@qq.com
 */
public class HttpMethods {
//    public static final String BASE_URL = "http://admin.mtest.inf-technology.com";
        public static final String BASE_URL = "http://admin.mauto.infinitsea.com";
    private static final int DEFAULT_TIMEOUT = 2;

    private Retrofit retrofit;
    private BlueService movieService;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(BlueService.class);
    }

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getOthers();
        }
    }

    public void getEms(Subscriber<EmsBean> subscriber, String phone) {
        movieService.get_ems(phone)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void savePass(Subscriber<ResultBean> subscriber, String phone, String pwd) {
        movieService.save_pass(phone, pwd)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void login(Subscriber<LoginBean> subscriber, String phone, String pwd) {
        movieService.login(phone, pwd)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void change_pass(Subscriber<ResultBean> subscriber, String phone, String oldPass, String newPass) {
        movieService.change_pass(phone, oldPass, newPass)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void search(Subscriber<SearchBean> subscriber, String toekn, String key) {
        movieService.search(toekn, key)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void check_order(Subscriber<OrderBean> subscriber, String token, int status, int pageSize, int pageNo) {
        movieService.order_list(token, status, pageSize, pageNo)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void order_detail(Subscriber<OrderInfoBean> subscriber, String token, String orderCode) {
        movieService.order_detail(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void car_info(Subscriber<CarInfoBean> subscriber, String token, String orderCode) {
        movieService.car_info(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void save_car_info(Subscriber<ResultBean> subscriber, String orderCode, String token, String carCjh,
                              String carXslc, String xcbylc, String xcbyDate) {
        movieService.save_car_info(token, orderCode, carCjh, carXslc, xcbylc, xcbyDate)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void repair_objs(Subscriber<RepairObjsBean> subscriber, String token, String orderCode) {
        movieService.repair_detail(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void gather(Subscriber<GatherBean> subscriber, String token, String orderCode) {
        movieService.gather(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void get_pic(Subscriber<PicBean> subscriber, String token, String orderCode) {
        movieService.get_pic(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void check_coupon(Subscriber<CouponBean> subscriber, String token, String orderCode, String couponNum) {
        movieService.check_coupon(token, orderCode, couponNum)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void finish_order(Subscriber<ResultBean> subscriber, String token, String orderCode) {
        movieService.finish_order(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void get_time(Subscriber<TimeBean> subscriber, String token) {
        movieService.sync_time(token)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void start_record(Subscriber<ResultBean> subscriber, String token, String orderCode, String time) {
        movieService.start_record(token, orderCode, time)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void end_record(Subscriber<ResultBean> subscriber, String token, String orderCode, String time) {
        movieService.end_record(token, orderCode, time)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void save_pay(Subscriber<ResultBean> subscriber, String token, String orderCode, String paySum, String couponCode, String payType, String khqm) {
        movieService.save_pay(token, orderCode, paySum, couponCode, payType, khqm)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void all_check(Subscriber<ReportBean> subscriber, String token, String orderCode) {
        movieService.all_check(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void save_repair(Subscriber<ResultBean> subscriber, String token, String orderCode, String json) {
        movieService.save_repair(token, orderCode, json)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void all_repair_objs(Subscriber<AllRepairBean> subscriber, String token, String orderCode) {
        movieService.repair_objs(token,orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void wechat_sao(Subscriber<WeixinBean> subscriber, String token, String orderCode) {
        movieService.wechat_sao(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void ali_sao(Subscriber<WeixinBean> subscriber, String token, String orderCode) {
        movieService.ali_sao(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void check_pay(Subscriber<PayStatusBean> subscriber, String token, String orderCode) {
        movieService.check_pay(token, orderCode)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void wechat_pay(Subscriber<WeixinBean> subscriber, String token, String orderCode, String code) {
        movieService.wechat_pay(token, orderCode, code)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void ali_pay(Subscriber<WeixinBean> subscriber, String token, String orderCode, String code) {
        movieService.ali_pay(token, orderCode, code)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void upload_img(Subscriber<ResultBean> subscriber, String token, String orderCode, String base, String picName) {
        movieService.upload_img(token, orderCode, base, picName)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void save_report(Subscriber<ResultBean> subscriber, String token, String orderCode, String jsonStr) {
        movieService.save_report(token, orderCode, jsonStr)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
