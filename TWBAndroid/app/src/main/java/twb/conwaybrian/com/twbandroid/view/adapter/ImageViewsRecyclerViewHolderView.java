package twb.conwaybrian.com.twbandroid.view.adapter;

import android.view.View;

public interface ImageViewsRecyclerViewHolderView {
    void onSetImage(String fileName);
    void onSetCancelListener(View.OnClickListener listener);
    void onSetImageViewListener(View.OnClickListener listener);
    void onSetCancelImageViewEnable(boolean enable);
    void onSetCancelImageViewSetVisibility(int visibility);
}
