package twb.conwaybrian.com.twbandroid.view.adapterview;

import android.view.View;


public interface ArticleListRecycleViewHolderView {
    void onSetTile(String title);

    void onSetPreview(String preview);

    void onSetPoints(String points);

    void onSetViews(String views);

    void onSetCommentCount(String commentCount);

    void onSetUserId(String id);

    void onSetArticleImageView(String fileName);

    void onSetArticleImageViewVisibility(int visibility);

    void onSetPointsImageView(int res);

    void onSetClickListener(View.OnClickListener listener);
}