package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import twb.conwaybrian.com.twbandroid.model.Article;

public interface ArticleListView extends TWBView {
    void onFinishRefreshOrLoad();
    void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter);
}
