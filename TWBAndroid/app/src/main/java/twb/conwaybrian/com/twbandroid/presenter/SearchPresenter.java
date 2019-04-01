package twb.conwaybrian.com.twbandroid.presenter;

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
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.SearchView;

public class SearchPresenter extends TWBPresenter {
    private SearchView searchView;
    private ArticleListRecycleViewAdapter articleListRecycleViewAdapter;
    private String query;

    public SearchPresenter(SearchView searchView) {
        this.searchView = searchView;
        articleListRecycleViewAdapter = new ArticleListRecycleViewAdapter(context);
        searchView.onSetArticleListRecyclerAdapter(articleListRecycleViewAdapter);
    }

    public void search(String query) {
        search(query, 1000, 0);
    }

    public void search(String query, int limit, int offset) {
        this.query = query;
        Observer<Response<JsonArray>> observer = new Observer<Response<JsonArray>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonArray> response) {
                searchView.onFinishRefreshOrLoad();
                try {
                    if (response.isSuccessful()) {
                        JsonArray jsonArray = response.body();
                        System.out.println(jsonArray);
                        Type listType = new TypeToken<List<Article>>() {
                        }.getType();
                        List<Article> articleList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
                        articleListRecycleViewAdapter.addArticles(articleList);

                    } else {
                        searchView.onSetMessage("loading failed", FancyToast.ERROR);
                    }
                } catch (OutOfMemoryError e) {
                    onError(e);
                }

            }

            @Override
            public void onError(Throwable e) {
                searchView.onSetMessage(e.getMessage(), FancyToast.ERROR);

            }

            @Override
            public void onComplete() {

            }
        };
        ShuoApiService.getInstance().searchArticle(observer, query, limit, offset, false);
    }

    public void loadMore() {
        search(query, 1000, articleListRecycleViewAdapter.getItemCount());
    }

    public void clear() {
        articleListRecycleViewAdapter.clear();
    }
}
