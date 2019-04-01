package twb.conwaybrian.com.twbandroid.shuoApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.LocalDateTime;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.model.Comment;
import twb.conwaybrian.com.twbandroid.model.Like;
import twb.conwaybrian.com.twbandroid.model.User;

public class ShuoApiService {
    private ShuoApi shuoApi;
    private Retrofit retrofitArticleExcerptApi;

    private ShuoApiService() {
        URLRetrofitBuilder urlRetrofitBuilder = new URLRetrofitBuilder();
        retrofitArticleExcerptApi = urlRetrofitBuilder.buildretrofit("http://34.80.143.220:9999/", true);
        shuoApi = retrofitArticleExcerptApi.create(ShuoApi.class);
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

    public void viewed(@NonNull Observer observer,
                       @NonNull Article article, boolean isObserveOnIO) {

        String articleId = article.getArticleId();
        shuoApi
                .viewed(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void login(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        shuoApi
                .login(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void deleteUser(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
        String authKey = user.authKey();
        shuoApi
                .deleteUser(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void forgotPassword(@NonNull Observer observer, @NonNull String email, boolean isObserveOnIO) {
        shuoApi.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getArticlesPrivate(@NonNull Observer observer, @NonNull User user, @NonNull LocalDateTime endDateTime, @NonNull LocalDateTime startDateTime, @NonNull String orderBy, @NonNull int offset, @NonNull int limit, boolean isObserveOnIO) {
        shuoApi.getArticlesPrivate(user.authKey(), endDateTime, startDateTime, orderBy, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getArticlesPublic(@NonNull Observer observer, @NonNull LocalDateTime endDateTime, @NonNull LocalDateTime startDateTime, @NonNull String orderBy, @NonNull int offset, @NonNull int limit, boolean isObserveOnIO) {
        shuoApi.getArticlesPublic(endDateTime, startDateTime, orderBy, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void searchArticle(@NonNull Observer observer, @NonNull String query, @NonNull int limit, @NonNull int offset, boolean isObserveOnIO) {
        shuoApi.searchArticle(query, limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getUserPostHistory(@NonNull Observer observer, @NonNull User user, boolean isObserveOnIO) {
        String userId = user.getUserId();
        shuoApi.getUserPostHistory(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void postArticle(@NonNull Observer observer, @NonNull User user, @NonNull Article article, boolean isObserveOnIO) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gson.toJson(article);
        System.out.println(json);
        shuoApi.postArticle(user.authKey(), json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void like(@NonNull Observer observer, @NonNull User user, @NonNull Like like, boolean isObserveOnIO) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gson.toJson(like);
        System.out.println(json);
        shuoApi.like(user.authKey(), json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    public void comment(@NonNull Observer observer, @NonNull User user, @NonNull Comment comment, boolean isObserveOnIO) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        String json = gson.toJson(comment);
        System.out.println(json);
        shuoApi.comment(user.authKey(), json)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @Deprecated
    public void getComments(@NonNull Observer observer, @NonNull Article article, boolean isObserveOnIO) {
        String articleId = article.getArticleId();
        shuoApi.getComments(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getArticleDataPublic(@NonNull Observer observer, @NonNull Article article, boolean isObserveOnIO) {
        String articleId = article.getArticleId();
        shuoApi.getArticleDataPublic(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public void getArticleDataPrivate(@NonNull Observer observer, @NonNull User user, @NonNull Article article, boolean isObserveOnIO) {
        String articleId = article.getArticleId();
        shuoApi.getArticleDataPrivate(user.authKey(), articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(isObserveOnIO ? Schedulers.io() : AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    // 創建實例
    private static class SingletonHolder {
        private static final ShuoApiService INSTANCE = new ShuoApiService();
    }
}

