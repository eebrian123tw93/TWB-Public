package twb.conwaybrian.com.twbandroid;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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
        Glide.with(context).load(images.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addImage(String  imageFile){
        if(!images.contains(imageFile)) images.add(imageFile);
        notifyDataSetChanged();
    }
    public void addImage(Uri uri){
        addImage(getRealFilePath(context,uri));
    }

    public void clear(){
        images.clear();
        notifyDataSetChanged();
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
