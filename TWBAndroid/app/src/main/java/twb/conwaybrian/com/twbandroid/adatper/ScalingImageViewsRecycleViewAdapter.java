package twb.conwaybrian.com.twbandroid.adatper;

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


import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ScalingImageViewsRecycleViewAdapter extends RecyclerView.Adapter<ScalingImageViewsRecycleViewAdapter.ViewHolder> {




    private List<String> images;
    private Context context;



    public class ViewHolder extends RecyclerView.ViewHolder {


//        private ImageView imageView;
//        private ImageView cancelImageView;
//
//        private CardView cardView;
            private PhotoView photoView;

        public ViewHolder(View v) {
            super(v);
            photoView=v.findViewById(R.id.photo_view);

//            imageView=v.findViewById(R.id.imageView);
//            cancelImageView=v.findViewById(R.id.cancel_imageView);
//            cardView=v.findViewById(R.id.card_view);
        }
    }

    public ScalingImageViewsRecycleViewAdapter(Context context,List<String> images) {
        this.context=context;
        this.images = images;


    }


//    public ImageViewsRecycleViewAdapter(Context context,List<String> images) {
//        this(context,images,Type.URL);
//    }

    @Override
    public ScalingImageViewsRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scaling_imageview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
//        PhotoViewAttacher attacher=new PhotoViewAttacher(holder.photoView);
        Glide.with(context).load(images.get(position)).into(holder.photoView);
//        attacher.update();
//        switch (type){
//            case EDIT:
//                holder.cancelImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        removeImage(position);
//                    }
//                });
//                if(state==State.UPLOADING)holder.cancelImageView.setEnabled(false);
//                else holder.cancelImageView.setEnabled(true);
//                break;
//            case VIEW:
//                holder.cancelImageView.setVisibility(View.GONE);
//                holder.imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(showImageViewsFragmentListener!=null){
//                            showImageViewsFragmentListener.onShowImageViewsFragment(images,position);
//                        }
//                    }
//                });
//                break;
//        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void addImage(String  imageFile){
        if(!images.contains(imageFile)) images.add(imageFile);
        notifyDataSetChanged();
    }

    public void addImages(List<String>images){
        for(String imageFile :images){
            if(!this.images.contains(imageFile)) {
                this.images.add(imageFile);
            }
        }
        notifyDataSetChanged();
    }
    public void addImage(Uri uri){
        addImage(getRealFilePath(context,uri));
    }

    public void removeImage(int position){
        images.remove(position);
        notifyDataSetChanged();
    }

    public void clear(){
        images.clear();
        notifyDataSetChanged();
    }

    private static String getRealFilePath(final Context context, final Uri uri ) {
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
