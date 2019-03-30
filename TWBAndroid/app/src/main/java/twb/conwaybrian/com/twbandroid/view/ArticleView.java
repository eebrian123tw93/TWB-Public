package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

import java.util.List;


public interface ArticleView extends TWBView {
    void onSetProgressBarVisibility(int visibility);
    void onSetArticleDataRecyclerViewAdapter(RecyclerView.Adapter adapter);


    void onSendCommentResult(boolean result);
    void onClearCommentText();
    void onFinishRefreshOrLoad();

    void  onShowImageViewsFragment(List<String> images, int position);
    void  onSetArticleDataRecyclerViewScroll(int position);
}
