package twb.conwaybrian.com.twbandroid.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadPresenter extends TWBPresenter {
    private UploadView uploadView;
    private Article article;

    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;

    public UploadPresenter(UploadView uploadView){
        this.uploadView=uploadView;
        article = new Article();
        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,article.getImages(),ImageViewsRecycleViewAdapter.Type.FILE);
    }

    public void postArticle(String title,String content){
        if(isLogin()) {
            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        uploadView.onPostArticle(true);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

            article.setTitle(title);
            article.setContent(content);
            article.setUserId(user.getUserId());
            article.setCreateTime(new Date());
            ShuoApiService.getInstance().postArticle(observer, user, article, false);
        }else {
            uploadView.onPostArticle(false);
            if (userListener!=null)userListener.toLoginPage();
        }
    }

    public void clear(){
        uploadView.onClearText();
    }

    public void setProgressBarVisibility(int visibility){
        uploadView.onSetProgressBarVisibility(visibility);
    }

    public void addImage(String imageFile){
        if(!article.getImages().contains(imageFile)) article.getImages().add(imageFile);
        imageViewsRecycleViewAdapter.notifyDataSetChanged();
    }
    public void addImage(Uri uri){
        addImage(getRealFilePath(context,uri));

    }

    public void setImageViewsRecycleViewAdapter() {
        uploadView.onSetImageViewAdapter(imageViewsRecycleViewAdapter);
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
