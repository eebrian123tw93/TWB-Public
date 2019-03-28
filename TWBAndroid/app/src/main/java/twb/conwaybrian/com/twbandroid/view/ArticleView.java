package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;

public interface ArticleView extends TWBView {
    void onSetProgressBarVisibility(int visibility);
    void onSetImageViewAdapter(RecyclerView.Adapter adapter);
    void onSetCommentViewAdapter(RecyclerView.Adapter adapter);
    void onSetArticle(String title, String content, String points, String views, String commentCount);
    void onSetTitle(String title);
    void onSetContent(String content);
    void onSetPoints(String points);
    void onSetViews(String views);
    void onSetCommentCount(String commentCount);
    void onSetPointsImageView(Reaction.Type type);
    void onSetDefaultPointsImageView(Reaction.Type type);

    void onSendCommentResult(boolean result);
    void onClearCommentText();
}
