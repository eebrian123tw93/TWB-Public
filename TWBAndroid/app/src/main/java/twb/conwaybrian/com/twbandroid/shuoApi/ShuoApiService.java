package twb.conwaybrian.com.twbandroid.shuoApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.model.User;

public class ShuoApiService {
    private ShuoApi shuoApi;
    private Retrofit retrofitArticleExcerptApi;

    private ShuoApiService() {
        URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("http://34.80.143.220:9999/", true);
        shuoApi = retrofitArticleExcerptApi.create(ShuoApi.class);
    }

    // 創建實例
    private static class SingletonHolder {
        private static final ShuoApiService INSTANCE = new ShuoApiService();
    }

    // 獲取實例
    public static ShuoApiService getInstance() {
        return ShuoApiService.SingletonHolder.INSTANCE;
    }

    public void register(@NonNull Observer observer,
                         @NonNull User user, boolean isObserveOnIO) {

        Gson gson = new Gson();
        String json = gson.toJson(user);
        shuoApi
                .register(json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void login(@NonNull Observer observer,@NonNull User user,boolean isObserveOnIO){
        String authKey=user.authKey();
        shuoApi
                .login(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }
    public void forgotPassword(@NonNull Observer observer,@NonNull String email,boolean isObserveOnIO){
        shuoApi.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getArticles(@NonNull Observer observer,@NonNull String type,@NonNull int start,@NonNull int count,boolean isObserveOnIO){
        shuoApi.getArticles(type,start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void postArticle(@NonNull Observer observer, @NonNull User user,@NonNull Article article, boolean isObserveOnIO){
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gson.toJson(article);
        shuoApi.postArticle(user.authKey(),json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}

