package twb.conwaybrian.com.twbandroid.presenter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.threeten.bp.LocalDateTime;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.adatper.ArticleListRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ArticleListView;

public class ArticleListPresenter extends TWBPresenter {
    private ArticleListView articleListView;


    private ArticleListRecycleViewAdapter articleListRecycleViewAdapter;

    public ArticleListPresenter(ArticleListView articleListView){
        this.articleListView=articleListView;
        articleListRecycleViewAdapter=new ArticleListRecycleViewAdapter(context,new ArrayList<Article>());
        articleListView.onSetArticleListRecyclerAdapter(articleListRecycleViewAdapter);
    }

    public void getArticleList(String order,int limit){
        this.getArticleList(order,articleListRecycleViewAdapter.getItemCount(),limit);
    }

    public void getArticleList(String order,int offset,int limit){
        Observer<Response<JsonArray>> observer=new Observer<Response<JsonArray>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonArray> response) {
                if (response.isSuccessful()){
                    JsonArray jsonArray = response.body();
                    System.out.println(jsonArray);
                    Type listType = new TypeToken<List<Article>>() {}.getType();
                    List<Article> articleList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
                    articleListRecycleViewAdapter.addArticles(articleList);
                    articleListView.onFinishRefreshOrLoad();
                }else{
                    articleListView.onSetMessage("loading failed",FancyToast.ERROR);
                }
            }

            @Override
            public void onError(Throwable e) {
                articleListView.onSetMessage(e.getMessage(),FancyToast.ERROR);
                articleListView.onFinishRefreshOrLoad();
            }

            @Override
            public void onComplete() {

            }
        };

        LocalDateTime endDateTime=LocalDateTime.now();
        LocalDateTime startDateTime=endDateTime.minusDays(10);

        if(isLogin()){
            ShuoApiService.getInstance().getArticlesPrivate(observer,user,endDateTime,startDateTime,order,offset,limit,false);
        }else {
            ShuoApiService.getInstance().getArticlesPublic(observer,endDateTime,startDateTime,order,offset,limit,false);
        }
    }

    public void refresh(){
        articleListRecycleViewAdapter.clear();
    }
}

