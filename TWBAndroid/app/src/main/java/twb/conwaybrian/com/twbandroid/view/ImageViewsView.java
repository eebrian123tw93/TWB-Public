package twb.conwaybrian.com.twbandroid.view;

import android.support.v7.widget.RecyclerView;

public interface ImageViewsView extends TWBView {
    void onSetImageViewsRecyclerViewAdapter(RecyclerView.Adapter adapter);
    void onImageViewsRecycleViewScrollPosition(int position);
}
