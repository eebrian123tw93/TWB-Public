package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import twb.conwaybrian.com.twbandroid.model.Article;

public interface ArticleListView extends TWBView {
    public void onGetArticles(List <Article> articleList);
    public void onFinishRefreshOrLoad();
    public void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter);
}
