package twb.conwaybrian.com.twbandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.adapterpresenter.ScalingImageViewsRecyclerViewPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ScalingImageViewsRecyclerViewHolderView;
import uk.co.senab.photoview.PhotoView;

public class ScalingImageViewsRecycleViewAdapter extends RecyclerView.Adapter<ScalingImageViewsRecycleViewAdapter.ViewHolder> {


    private Context context;
    private ScalingImageViewsRecyclerViewPresenter scalingImageViewsRecyclerViewPresenter;


    public ScalingImageViewsRecycleViewAdapter(Context context) {
        this.context = context;
        this.scalingImageViewsRecyclerViewPresenter = new ScalingImageViewsRecyclerViewPresenter();
    }

    @Override
    public ScalingImageViewsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scaling_imageview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        scalingImageViewsRecyclerViewPresenter.bindDate(holder, position);

    }

    @Override
    public int getItemCount() {
        return scalingImageViewsRecyclerViewPresenter.getItemCount();
    }

    public void addImage(String imageFile) {
        scalingImageViewsRecyclerViewPresenter.addImage(imageFile);
        notifyDataSetChanged();
    }

    public void addImages(List<String> images) {
        scalingImageViewsRecyclerViewPresenter.addImages(images);
        notifyDataSetChanged();
    }

    public void addImage(Uri uri) {
        scalingImageViewsRecyclerViewPresenter.addImage(uri);
    }

    public void removeImage(int position) {
        scalingImageViewsRecyclerViewPresenter.removeImage(position);
        notifyDataSetChanged();
    }

    public void clear() {
        scalingImageViewsRecyclerViewPresenter.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ScalingImageViewsRecyclerViewHolderView {
        private PhotoView photoView;

        public ViewHolder(View v) {
            super(v);
            photoView = v.findViewById(R.id.photo_view);


        }

        @Override
        public void onSetImage(String fileName) {
            Glide.with(context).load(fileName).into(photoView);
        }
    }


}
