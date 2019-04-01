package twb.conwaybrian.com.twbandroid.presenter.adapterpresenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.adatper.ScalingImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.presenter.TWBPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ScalingImageViewsRecyclerViewHolderView;

public class ScalingImageViewsRecyclerViewPresenter extends TWBPresenter {
    private List<String> images;

    public ScalingImageViewsRecyclerViewPresenter(){
        images=new ArrayList<>();

    }
    public void bindDate(ScalingImageViewsRecycleViewAdapter.ViewHolder viewHolder,int position){
        if(viewHolder instanceof ScalingImageViewsRecyclerViewHolderView){
            ScalingImageViewsRecyclerViewHolderView holderView=viewHolder ;
            holderView.onSetImage(images.get(position));
        }
    }

    public int getItemCount(){
        return images.size();
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



    public void addImage(String  imageFile){
        if(!images.contains(imageFile)) images.add(imageFile);

    }

    public void addImages(List<String>images){
        for(String imageFile :images){
            if(!this.images.contains(imageFile)) {
                this.images.add(imageFile);
            }
        }

    }
    public void addImage(Uri uri){
        addImage(getRealFilePath(context,uri));
    }

    public void removeImage(int position){
        images.remove(position);

    }

    public void clear(){
        images.clear();

    }

}
