package twb.conwaybrian.com.twbandroid.view.adapterview;

import android.support.v7.widget.RecyclerView;

import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;

public interface ArticleDataRecyclerArticleViewHolderView {
    void onSetTitle(String title);
    void onSetContent(String content);
    void onSetPoints(String points);
    void onSetViews(String views);
    void onSetCommentCount(String commentCount);
    void onSetImageViewsRecyclerViewAdapter(RecyclerView.Adapter adapter);

    void onSetCurrentReaction(Reaction reaction);
    void onSetReactClickListener();
    void  onSetReactDismissListener();

}
