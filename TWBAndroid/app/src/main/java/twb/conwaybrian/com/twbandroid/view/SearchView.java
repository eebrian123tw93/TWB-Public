package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

public interface SearchView extends TWBView {
    void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter);

    void onFinishRefreshOrLoad();
}
