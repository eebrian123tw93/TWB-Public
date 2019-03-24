package twb.conwaybrian.com.twbandroid.view;

import twb.conwaybrian.com.twbandroid.ImageViewsRecycleViewAdapter;

public interface UploadView extends TWBView {
    public void onPostArticle(boolean result);
    public void onClearText();
    public void onSetProgressBarVisibility(int visibility);
    public void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter);

}
