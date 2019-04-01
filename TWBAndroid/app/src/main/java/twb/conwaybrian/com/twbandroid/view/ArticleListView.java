package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

public interface ArticleListView extends TWBView {
    void onFinishRefreshOrLoad();

    void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter);
}
