package mituo.wshoto.com.mituo.http;

import mituo.wshoto.com.mituo.bean.CarInfoBean;
import mituo.wshoto.com.mituo.bean.CouponBean;
import mituo.wshoto.com.mituo.bean.EmsBean;
import mituo.wshoto.com.mituo.bean.GatherBean;
import mituo.wshoto.com.mituo.bean.LoginBean;
import mituo.wshoto.com.mituo.bean.OrderBean;
import mituo.wshoto.com.mituo.bean.OrderInfoBean;
import mituo.wshoto.com.mituo.bean.PicBean;
import mituo.wshoto.com.mituo.bean.RepairObjsBean;
import mituo.wshoto.com.mituo.bean.ResultBean;
import mituo.wshoto.com.mituo.bean.TimeBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者：JTR on 2016/11/24 14:15
 * 邮箱：2091320109@qq.com
 */
public interface BlueService {
    @FormUrlEncoded
    @POST("/api/js/account/sendVcode")
    rx.Observable<EmsBean> get_ems(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("/api/js/account/savePwdByPhone")
    rx.Observable<ResultBean> save_pass(@Field("phone") String phone, @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("/api/js/account/savePwdByOld")
    rx.Observable<ResultBean> change_pass(@Field("token") String token, @Field("oldpwd") String oldpwd,
                                          @Field("newpwd") String newpwd);

    @FormUrlEncoded
    @POST("/api/js/account/login")
    rx.Observable<LoginBean> login(@Field("phone") String phone, @Field("pwd") String pwd);


    @GET("/api/js/order/queryOrderList")
    rx.Observable<OrderBean> order_list(@Query("token") String token, @Query("status") int status,
                                        @Query("pageSize") int pageSize, @Query("pageNo") int pageNo);

    @GET("/api/js/order/searchOrderList")
    rx.Observable<ResultBean> search(@Query("token") String token, @Query("searchStr") String searchStr);

    @GET("/api/js/order/getOrderDetail")
    rx.Observable<OrderInfoBean> order_detail(@Query("token") String token, @Query("orderCode") String orderCode);

    @GET("/api/js/order/getCarDetail")
    rx.Observable<CarInfoBean> car_info(@Query("token") String token, @Query("orderCode") String orderCode);

    @FormUrlEncoded
    @POST("/api/js/order/saveCarDetail")
    rx.Observable<ResultBean> save_car_info(@Field("token") String token, @Field("orderCode") String orderCode,
                                            @Field("carCjh") String carCjh, @Field("carFdjbh") String carFdjbh,
                                            @Field("carXslc") String carXslc, @Field("xcbylc") String xcbylc,
                                            @Field("xcbyDate") String xcbyDate);

    @GET("/api/js/order/getWxxmDetail")
    rx.Observable<RepairObjsBean> repair_detail(@Query("token") String token, @Query("orderCode") String orderCode);

    @GET("/api/js/order/getPayList")
    rx.Observable<GatherBean> gather(@Query("token") String token, @Query("orderCode") String orderCode);

    @GET("/api/js/order/getAllWxxm")
    rx.Observable<ResultBean> repair_objs(@Query("token") String token);

    @FormUrlEncoded
    @POST("/api/js/order/startVideo")
    rx.Observable<ResultBean> start_record(@Field("token") String token, @Field("orderCode") String orderCode,
                                           @Field("time") String time);

    @FormUrlEncoded
    @POST("/api/js/order/endVideo")
    rx.Observable<ResultBean> end_record(@Field("token") String token, @Field("orderCode") String orderCode,
                                         @Field("time") String time);

    @GET("/api/js/order/getAllBgxm")
    rx.Observable<ResultBean> all_check(@Query("token") String token);

    @GET("/api/js/order/getSysTime")
    rx.Observable<TimeBean> sync_time(@Query("token") String token);

    @GET("/api/js/order/getWxpic")
    rx.Observable<PicBean> get_pic(@Query("token") String token, @Query("orderCode") String orderCode);

    @FormUrlEncoded
    @POST("api/js/order/finished")
    rx.Observable<ResultBean> finish_order(@Field("token") String token, @Field("orderCode") String orderCode);

    @GET("/api/js/order/checkCouponCode")
    rx.Observable<CouponBean> check_coupon(@Query("token") String token, @Query("orderCode") String orderCode,
                                           @Query("couponCode") String couponCode);
}