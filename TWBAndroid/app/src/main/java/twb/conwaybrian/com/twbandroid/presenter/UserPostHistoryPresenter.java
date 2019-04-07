package twb.conwaybrian.com.twbandroid.presenter;

import android.os.Bundle;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.adatper.ArticleListRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.UserPostHistoryView;

public class UserPostHistoryPresenter extends TWBPresenter {
    public static String USER_ID = "user_id";
    private UserPostHistoryView userPostHistoryView;
    private User author;
    private ArticleListRecycleViewAdapter articleListRecycleViewAdapter;

    public UserPostHistoryPresenter(UserPostHistoryView userPostHistoryView, Bundle bundle) {
        this.userPostHistoryView = userPostHistoryView;
        articleListRecycleViewAdapter = new ArticleListRecycleViewAdapter(context);
        userPostHistoryView.onSetArticleListRecyclerAdapter(articleListRecycleViewAdapter);
        if (bundle == null) {
            author = TWBPresenter.user;
            getUserPostHistory();
        } else {
            String userId = bundle.getString(USER_ID, TWBPresenter.user.getUserId());
            if (!userId.isEmpty()) {
                author = new User();
                author.setUserId(userId);
                getUserPostHistory();
            }
        }
    }

    public void getUserPostHistory() {
        Observer<Response<JsonArray>> observer = new Observer<Response<JsonArray>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonArray> response) {
                userPostHistoryView.onFinishRefreshOrLoad();
                if (response.isSuccessful()) {
                    JsonArray jsonArray = response.body();
                    System.out.println(jsonArray);
                    Type listType = new TypeToken<List<Article>>() {
                    }.getType();
                    List<Article> articleList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
                    articleListRecycleViewAdapter.addArticles(articleList);

                } else {
                    userPostHistoryView.onSetMessage("loading failed", FancyToast.ERROR);
                }
            }

            @Override
            public void onError(Throwable e) {
                userPostHistoryView.onSetMessage(e.getMessage(), FancyToast.ERROR);

            }

            @Override
            public void onComplete() {

            }
        };
        if(isLogin()){
            ShuoApiService.getInstance().getUserPostHistoryPrivate(observer,user ,this.author, false);
        }else {
            ShuoApiService.getInstance().getUserPostHistoryPublic(observer, this.author, false);
        }
//
    }

    public void refresh() {
        articleListRecycleViewAdapter.clear();
    }
}
