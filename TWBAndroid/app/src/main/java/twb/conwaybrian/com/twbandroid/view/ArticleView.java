package twb.conwaybrian.com.twbandroid.view;

import twb.conwaybrian.com.twbandroid.ImageViewsRecycleViewAdapter;

public interface ArticleView {
    public void onSetProgressBarVisibility(int visibility);
    public void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter);
    public void onSetArticle(String title,String content,String points,String views,String commentCount);

}
