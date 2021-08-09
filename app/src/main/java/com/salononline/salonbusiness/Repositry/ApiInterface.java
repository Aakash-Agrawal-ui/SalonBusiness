package com.salononline.salonbusiness.Repositry;


import com.salononline.salonbusiness.Data.BookingData;
import com.salononline.salonbusiness.Parse.AddBarberData;
import com.salononline.salonbusiness.Parse.AddSalonData;
import com.salononline.salonbusiness.Parse.FileParse;
import com.salononline.salonbusiness.Parse.ForgetData;
import com.salononline.salonbusiness.Parse.GetBarberResult;
import com.salononline.salonbusiness.Parse.GetQueueNameResult;
import com.salononline.salonbusiness.Parse.GetServiceResult;
import com.salononline.salonbusiness.Parse.GetShopResult;
import com.salononline.salonbusiness.Parse.GetUserProfileResult;
import com.salononline.salonbusiness.Parse.ParseAreas;
import com.salononline.salonbusiness.Parse.ParseBookingListResult;
import com.salononline.salonbusiness.Parse.ParseCategory;
import com.salononline.salonbusiness.Parse.ParseCities;
import com.salononline.salonbusiness.Parse.ParseCountries;
import com.salononline.salonbusiness.Parse.ParseGetAllBarberResult;
import com.salononline.salonbusiness.Parse.ParseGetAllServiceResult;
import com.salononline.salonbusiness.Parse.ParseGetQueueBarber;
import com.salononline.salonbusiness.Parse.ParseNewService;
import com.salononline.salonbusiness.Parse.ParseSalonAdd;
import com.salononline.salonbusiness.Parse.ParseShopStatus;
import com.salononline.salonbusiness.Parse.ParseStates;
import com.salononline.salonbusiness.Parse.ParseUpdateImage;
import com.salononline.salonbusiness.Parse.QueryData;
import com.salononline.salonbusiness.Parse.QueueData;
import com.salononline.salonbusiness.Parse.SendOtpClass;
import com.salononline.salonbusiness.Parse.ShopServiceStateData;
import com.salononline.salonbusiness.Parse.ShopStateData;
import com.salononline.salonbusiness.Parse.SignUpData;
import com.salononline.salonbusiness.Parse.SignUpVeify;
import com.salononline.salonbusiness.Parse.UpdateImageData;
import com.salononline.salonbusiness.Parse.UpdateNumberData;
import com.salononline.salonbusiness.Parse.UpdateSalonDetailsData;
import com.salononline.salonbusiness.Parse.UpdateServiceData;
import com.salononline.salonbusiness.Parse.VerifyShopStatus;
import com.salononline.salonbusiness.Parse.json;
import com.salononline.salonbusiness.Parse.jsonverify;
import com.salononline.salonbusiness.Parse.jsonverifylogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("auth/business")
    Call<json> PostData(@Body SignUpData signUpData);

    @POST("auth/user-verify")
    Call<jsonverify> VerifyData(@Body SignUpVeify signUpVeify);

    @POST("business/otp/generate")
    Call<json> ResendData(@Body SendOtpClass sendOtpClass);

    @POST("auth/login/verify")
    Call<jsonverify> VerifyLoginData(@Body SignUpVeify signUpVeify);

    @POST("auth/business/login")
    Call<jsonverifylogin> VerifyLoginWithPassword(@Body SignUpData signUpData);

    @PUT("auth/forgot-password")
    Call<json> ResetPassword(@Body ForgetData forgetData);

    @GET("countries")
    Call<ParseCountries> getCountries();

    @GET("states")
    Call<ParseStates> getStates(@Query("countryCode") String countryCode);

   @GET("cities")
    Call<ParseCities> getCities(@Query("stateCode") String stateCode);

    @GET("areas")
    Call<ParseAreas> getAreas(@Query("cityCode") String cityCode);

   @Multipart
   @POST("uploads/file")
    Call<FileParse> upload(@Header("Authorization") String token,
                           @Part MultipartBody.Part file,
                           @Part("fileGroup") RequestBody fileGroup);
   @POST("shop")
    Call<ParseSalonAdd> addSalonForm(@Header("Authorization") String token,
                                     @Body AddSalonData addSalonData);

   @GET("auth/token/refresh")
    Call<jsonverify> refreshToken(@Query("authorization") String token);

   @GET("shops/{shopUuid}/verify-status")
    Call<VerifyShopStatus> verifyShop(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid);

   @GET("services/category")
    Call<ParseCategory> getCategory(
           @Header("Authorization") String token);

   @POST("shops/{shopUuid}/services")
        Call<json> addNewService(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid,
           @Query("categoryUuid") String catUid,
           @Body ParseNewService parseNewService
   );
   @GET("shops/{shopUuid}/services")
    Call<ParseGetAllServiceResult> getAllService(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid,
           @Query("type") String gender
   );
    @GET("shops/{shopUuid}/barbers")
    Call<ParseGetAllBarberResult> getAllBarbers(
            @Header("Authorization") String token,
            @Path("shopUuid") String uid,
            @Query("type") String gender
    );

   @GET("shops/{shopUuid}")
    Call<GetShopResult> getShopData(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid
   );
   @PUT("shops/{shopUuid}/logo")
    Call<ParseUpdateImage> updateLogo(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid,
           @Body UpdateImageData updateImageData);

   @PUT("shops/{shopUuid}")
    Call<ParseUpdateImage> updateSalonDet(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid,
           @Body UpdateSalonDetailsData updateSalonDetailsData
           );

   @PUT("shops/{shopUuid}/status")
    Call<ParseUpdateImage> updateSalonState(
           @Header("Authorization") String token,
           @Path("shopUuid") String uid,
           @Body ShopStateData shopStateData

           );
    @PUT("services/{serviceUuid}/state")
    Call<ParseUpdateImage> updateSalonServiceState(
            @Header("Authorization") String token,
            @Path("serviceUuid") String uid,
            @Body ShopServiceStateData shopServiceStateData
            );
    @PUT("barbers/{barberUuid}/state")
    Call<ParseUpdateImage> updateSalonBarberState(
            @Header("Authorization") String token,
            @Path("barberUuid") String uid,
            @Body ShopServiceStateData shopServiceStateData
    );

    @POST("shops/{shopUuid}/barbers")
    Call<ParseUpdateImage> addNewBarber(
            @Header("Authorization") String token,
            @Path("shopUuid") String uid,
            @Body AddBarberData addBarberData
            );

    @GET("services/{serviceUuid}")
    Call<GetServiceResult> getServiceCall(
            @Header("Authorization") String token,
            @Path("serviceUuid") String uid);

    @GET("barbers/{barberUuid}")
    Call<GetBarberResult> getBarberCall(
            @Header("Authorization") String token,
            @Path("barberUuid") String uid);

    @PUT("services/{serviceUuid}")
    Call<ParseUpdateImage> updateService(
            @Header("Authorization") String token,
            @Path("serviceUuid") String uid,
            @Query("categoryUuid") String categoryId,
            @Body UpdateServiceData updateServiceData
    );

    @PUT("barbers/{barberUuid}")
    Call<ParseUpdateImage> updateBarber(
            @Header("Authorization") String token,
            @Path("barberUuid") String uid,
            @Body AddBarberData addBarberData
    );

    @GET("user-profile")
    Call<GetUserProfileResult> userProfileResult(
            @Header("Authorization") String token
    );

    @PUT("auth/mobile-no")
    Call<jsonverify> updateNumber(
            @Header("Authorization") String token,
            @Body UpdateNumberData updateNumberData
            );

    @GET("shops/{shopUuid}/status")
    Call<ParseShopStatus> getShopStatusResult(
            @Header("Authorization") String token,
            @Path("shopUuid") String uid

            );

    @POST("help/queries")
    Call<ParseUpdateImage> postQuery(
            @Header("Authorization") String token,
            @Body QueryData queryData);

    @GET("shops/{shopUuid}/live-bookings")
    Call<ParseBookingListResult> bookingListData(
        @Header("Authorization") String token,
        @Path("shopUuid") String uid
    );
    @GET("shops/{shopUuid}/bookings")
    Call<ParseBookingListResult> bookingHistoryListData(
            @Header("Authorization") String token,
            @Path("shopUuid") String uid
    );

    @GET("{shopUuid}/live-queue")
    Call<GetQueueNameResult> queueName(
            @Header("Authorization") String token,
            @Path("shopUuid") String uid,
            @Query("gender") String gender
    );

    @GET("barbers/{barberUuid}/bookings")
    Call<ParseGetQueueBarber> getQueueBarber(
            @Header("Authorization") String token,
            @Path("barberUuid") String uid
    );
    @PUT("bookings/{bookingUuid}/complete")
    Call<json> completeBooking(
            @Header("Authorization") String token,
            @Path("bookingUuid") String uid,
            @Body QueueData queueData
            );
    @PUT("bookings/{bookingUuid}/start")
    Call<json> startBooking(
            @Header("Authorization") String token,
            @Path("bookingUuid") String uid,
            @Body QueueData queueData
    );
    @PUT("bookings/{bookingUuid}/decline")
    Call<json> declineBooking(
            @Header("Authorization") String token,
            @Path("bookingUuid") String uid,
            @Body BookingData bookingData
    );
    @PUT("bookings/{bookingUuid}/accept")
    Call<json> acceptBooking(
            @Header("Authorization") String token,
            @Path("bookingUuid") String uid,
            @Body BookingData bookingData
            );

}
