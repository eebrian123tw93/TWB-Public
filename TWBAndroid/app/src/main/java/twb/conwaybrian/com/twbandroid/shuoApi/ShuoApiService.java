package twb.conwaybrian.com.twbandroid.shuoApi;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

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
                                    @NonNull String username,String password,String email, boolean isObserveOnIO) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",username);
            jsonObject.put("password",password);
            jsonObject.put("email",email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s=jsonObject.toString();
        shuoApi
                .register(s)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}

