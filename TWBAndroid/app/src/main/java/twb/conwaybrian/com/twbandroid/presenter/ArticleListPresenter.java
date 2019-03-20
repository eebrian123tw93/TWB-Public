package twb.conwaybrian.com.twbandroid.presenter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ArticleListView;

public class ArticleListPresenter {
    private ArticleListView articleListView;

    public ArticleListPresenter(ArticleListView articleListView){
        this.articleListView=articleListView;
    }
    public void getArticleList(String type,int start,int count){
                Observer<Response<JsonArray>> observer=new Observer<Response<JsonArray>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonArray> response) {
                if (response.isSuccessful()){
                    JsonArray jsonArray = response.body();
                    System.out.println(jsonArray);
                    Type listType = new TypeToken<List<Article>>() {
                    }.getType();
                    List<Article> articleList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
                    articleListView.onGetArticles(articleList);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        ShuoApiService.getInstance().getArticles(observer,"qwe",1,1,false);

    }
}

