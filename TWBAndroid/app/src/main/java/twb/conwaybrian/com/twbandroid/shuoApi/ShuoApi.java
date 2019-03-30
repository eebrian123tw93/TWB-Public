package twb.conwaybrian.com.twbandroid.shuoApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.threeten.bp.LocalDateTime;

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



    @Headers("Content-Type:application/json")
    @POST("/shuo/postArticle/")
    Observable<Response<ResponseBody>> postArticle(@Header("Authorization")String authKey,@Body String s);

    @Headers("Content-Type:application/json")
    @POST("/shuo/like/")
    Observable<Response<ResponseBody>>like(@Header("Authorization")String authKey,@Body String s );

    @Headers("Content-Type:application/json")
    @POST("/shuo/comment/")
    Observable<Response<ResponseBody>>comment(@Header("Authorization")String authKey,@Body String s );

    @GET("/shuo/public/getComments/")
    Observable<Response<JsonArray>>getComments(@Query("articleId") String articleId);


    @GET("/shuo/public/getArticles/")
    Observable<Response<JsonArray>>getArticlesPublic(@Query("endTime") LocalDateTime endDateTime, @Query("startTime") LocalDateTime startGateTime,@Query("orderBy")String orderBy ,@Query("offset")int offset, @Query("limit")int limit);

    @GET("/shuo/getArticles/")
    Observable<Response<JsonArray>>getArticlesPrivate(@Header("Authorization")String authKey,@Query("endTime") LocalDateTime endDateTime, @Query("startTime") LocalDateTime startGateTime,@Query("orderBy")String orderBy ,@Query("offset")int offset, @Query("limit")int limit);
}
