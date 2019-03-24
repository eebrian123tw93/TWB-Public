package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.List;

public class ImageViewsRecycleViewAdapter extends RecyclerView.Adapter<ImageViewsRecycleViewAdapter.ViewHolder> {

    public List<String> getImages() {
        return images;
    }

    private List<String> images;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        private CardView cardView;


        public ViewHolder(View v) {
            super(v);

            imageView=v.findViewById(R.id.imageView);

            cardView=v.findViewById(R.id.card_view);
        }
    }

    public ImageViewsRecycleViewAdapter(Context context,List<String> images) {
        this.context=context;
        this.images = images;

    }
//    public ImageViewsRecycleViewAdapter(Context context,List<String> images) {
//        this(context,images,Type.URL);
//    }

    @Override
    public ImageViewsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imageview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


//        if(articleList.get(position).getImages().isEmpty())
//            holder.articleImageView.setVisibility(View.GONE);
//        else
//            Picasso.get().load(articleList.get(position).getImages().get(0)).into(holder.articleImageView);
//        Picasso.get().load("http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png").resize(80,80).into(holder.articleImageView);

//        Picasso.get().load("https://pbs.twimg.com/profile_images/941322358245154816/tF4dPHrS.jpg").into(holder.imageView);
//        Picasso.get().load(new File(images.get(position))).into(holder.imageView);

        Glide.with(context).load(images.get(position)).into(holder.imageView);





    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
