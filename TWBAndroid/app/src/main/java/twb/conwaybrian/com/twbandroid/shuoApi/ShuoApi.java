package twb.conwaybrian.com.twbandroid.shuoApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Observable;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShuoApi {

    @Headers("Content-Type:application/json")
    @POST("/shuo/public/register")
    Observable<Response<ResponseBody>> register(@Body String s);

    @GET("/shuo/checkUserExist/")
    Observable<Response<ResponseBody>>login(@Header("Authorization")String authKey);

    @GET("/shuo/public/forgotPassword/")
    Observable<Response<ResponseBody>>forgotPassword(@Query("email") String email);

    @GET("/shuo/public/getArticles/")
    Observable<Response<JsonArray>>getArticles(@Query("type") String type, @Query("start")int start, @Query("count")int count);
}
