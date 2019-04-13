package twb.conwaybrian.com.twbandroid.view;

import twb.conwaybrian.com.twbandroid.adapter.ImageViewsRecycleViewAdapter;

public interface UploadView extends TWBView {
    void onPostArticle(boolean result);

    void onClearText();

    void onSetProgressBarVisibility(int visibility);

    void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter);

    void onSetTitle(String title);

    void onSetContent(String content);

}
