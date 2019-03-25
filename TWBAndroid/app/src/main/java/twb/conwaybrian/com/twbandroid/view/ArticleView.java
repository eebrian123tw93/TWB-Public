package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;

public interface ArticleView extends TWBView {
    public void onSetProgressBarVisibility(int visibility);
    public void onSetImageViewAdapter(RecyclerView.Adapter adapter);
    public void onSetCommentViewAdapter(RecyclerView.Adapter adapter);
    public void onSetArticle(String title,String content,String points,String views,String commentCount);
    public void onSetTitle(String title);
    public void onSetContent(String content);
    public void onSetPoints(String points);
    public void onSetViews(String views);
    public void onSetCommentCount(String commentCount);
    public void onSetPointsImageView(Reaction.Type type);
    public void onSetDefaultPointsImageView(Reaction.Type type);
}
