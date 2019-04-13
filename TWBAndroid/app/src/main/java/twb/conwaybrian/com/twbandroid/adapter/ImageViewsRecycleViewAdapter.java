package twb.conwaybrian.com.twbandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.adapterpresenter.ImageViewsRecyclerViewHolderPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ImageViewsRecyclerViewHolderView;

public class ImageViewsRecycleViewAdapter extends RecyclerView.Adapter<ImageViewsRecycleViewAdapter.ViewHolder> implements ImageViewsRecyclerViewHolderPresenter.ItemChangeListener {


    private ImageViewsRecyclerViewHolderPresenter imageViewsRecyclerViewHolderPresenter;
    private Context context;

    public ImageViewsRecycleViewAdapter(Context context, ImageViewsRecyclerViewHolderPresenter.Type type) {
        this.context = context;
        imageViewsRecyclerViewHolderPresenter = new ImageViewsRecyclerViewHolderPresenter(type, this);

    }

    public ImageViewsRecycleViewAdapter(Context context, ImageViewsRecyclerViewHolderPresenter.Type type, ImageViewsRecyclerViewHolderPresenter.ShowImageViewsFragmentListener listener) {
        this.context = context;
        imageViewsRecyclerViewHolderPresenter = new ImageViewsRecyclerViewHolderPresenter(type, listener);

    }

    @Override
    public void onItemChangeLister(int position) {
        removeImage(position);
    }

    @Override
    public ImageViewsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imageview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        imageViewsRecyclerViewHolderPresenter.bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return imageViewsRecyclerViewHolderPresenter.getItemCount();
    }

    public void addImage(String imageFile) {
        imageViewsRecyclerViewHolderPresenter.addImage(imageFile);
        notifyDataSetChanged();
    }

    public void addImages(List<String> images) {
        imageViewsRecyclerViewHolderPresenter.addImages(images);
        notifyDataSetChanged();
    }

    public void addImage(Uri uri) {
        imageViewsRecyclerViewHolderPresenter.addImage(uri);
    }

    public void removeImage(int position) {
        imageViewsRecyclerViewHolderPresenter.removeImage(position);
        notifyDataSetChanged();
    }

    public void clear() {
        imageViewsRecyclerViewHolderPresenter.clear();
        notifyDataSetChanged();
    }

    public void setState(ImageViewsRecyclerViewHolderPresenter.State state) {
        imageViewsRecyclerViewHolderPresenter.setState(state);
        for (int i = 0; i < getItemCount(); i++) {
            notifyItemChanged(i);
        }

    }

    public List<String> getImages() {
        return imageViewsRecyclerViewHolderPresenter.getImages();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ImageViewsRecyclerViewHolderView {


        private ImageView imageView;
        private ImageView cancelImageView;

        public ViewHolder(View v) {
            super(v);

            imageView = v.findViewById(R.id.imageView);
            cancelImageView = v.findViewById(R.id.cancel_imageView);

        }

        @Override
        public void onSetImage(String fileName) {
            Glide.with(context).load(fileName).into(imageView);
        }

        @Override
        public void onSetCancelListener(View.OnClickListener listener) {
            cancelImageView.setOnClickListener(listener);
        }

        @Override
        public void onSetImageViewListener(View.OnClickListener listener) {
            imageView.setOnClickListener(listener);
        }

        @Override
        public void onSetCancelImageViewEnable(boolean enable) {
            cancelImageView.setEnabled(enable);
        }

        @Override
        public void onSetCancelImageViewSetVisibility(int visibility) {
            cancelImageView.setVisibility(visibility);
        }
    }


}
