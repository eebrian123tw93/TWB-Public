package twb.conwaybrian.com.twbandroid.view;

import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;

public interface UploadView extends TWBView {
    void onPostArticle(boolean result);
    void onClearText();
    void onSetProgressBarVisibility(int visibility);
    void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter);

}
