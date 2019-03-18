package twb.conwaybrian.com.twbandroid.shuoApi;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
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
}

