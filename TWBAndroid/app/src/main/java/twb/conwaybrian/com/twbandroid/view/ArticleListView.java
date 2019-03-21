package twb.conwaybrian.com.twbandroid.view;

import java.util.List;

import twb.conwaybrian.com.twbandroid.model.Article;

public interface ArticleListView {
    public void onGetArticles(List <Article> articleList);
    public void onFinishRefreshOrLoad();
}
