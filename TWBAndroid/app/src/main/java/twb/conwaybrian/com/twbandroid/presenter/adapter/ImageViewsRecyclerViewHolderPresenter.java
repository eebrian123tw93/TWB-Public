package twb.conwaybrian.com.twbandroid.presenter.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.presenter.TWBPresenter;
import twb.conwaybrian.com.twbandroid.view.adapter.ImageViewsRecyclerViewHolderView;

public class ImageViewsRecyclerViewHolderPresenter extends TWBPresenter {
    public enum Type{
        VIEW,EDIT
    }
    public enum State{
        UPLOADING,NOT_UPLOAD
    }
    public interface ItemChangeListener{
        void onItemChangeLister(int position);
    }
    public interface ShowImageViewsFragmentListener{
        void onShowImageViewsFragment(List<String> images, int position);
    }

    private Type type;
    private State state;
    private ShowImageViewsFragmentListener showImageViewsFragmentListener;
    private ItemChangeListener itemChangeListener;


    public List<String> getImages() {
        return images;
    }

    private List<String>images;

    public ImageViewsRecyclerViewHolderPresenter(Type type,ItemChangeListener listener){
        this(type);
        this.itemChangeListener =listener;
    }
    public ImageViewsRecyclerViewHolderPresenter(Type type,ShowImageViewsFragmentListener listener){
        this(type);
        this.showImageViewsFragmentListener =listener;
    }

    public ImageViewsRecyclerViewHolderPresenter(Type type){

        this.type=type;
        state=State.NOT_UPLOAD;
        images=new ArrayList<>();
    }

    public void bindData(ImageViewsRecycleViewAdapter.ViewHolder viewHolder,final int position){

        if(viewHolder instanceof ImageViewsRecyclerViewHolderView){
            ImageViewsRecyclerViewHolderView holderView=viewHolder;
           holderView.onSetImage(images.get(position));

            switch (type){
                case EDIT:
                    holderView.onSetCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(itemChangeListener!=null)itemChangeListener.onItemChangeLister(position);

                        }
                    });
                    if(state==State.UPLOADING)holderView.onSetCancelImageViewEnable(false);
                    else holderView.onSetCancelImageViewEnable(true);
                    break;
                case VIEW:
                    holderView.onSetCancelImageViewSetVisibility(View.GONE);
                    holderView.onSetImageViewListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showImageViewsFragmentListener !=null){
                                showImageViewsFragmentListener.onShowImageViewsFragment(images,position);
                            }
                        }
                    });
            }
        }


    }

    public int getItemCount(){
        return images.size();
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

    public void setState(State state){
        this.state=state;
    }



}
