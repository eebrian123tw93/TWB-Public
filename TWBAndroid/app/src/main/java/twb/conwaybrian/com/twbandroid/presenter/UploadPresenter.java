package twb.conwaybrian.com.twbandroid.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.akiniyalocts.imgur_api.ImgurClient;
import com.akiniyalocts.imgur_api.model.Image;
import com.akiniyalocts.imgur_api.model.ImgurResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadPresenter extends TWBPresenter {
    private UploadView uploadView;
    private Article article;
    private List<String>images;

    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;

    public UploadPresenter(UploadView uploadView){
        this.uploadView=uploadView;
        article = new Article();
        images=new ArrayList<>();
        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,images);
    }
    public  void uploadImages(){

        for (int i=0;i<images.size();i++) {
            final String path=images.get(i);
            ImgurClient.getInstance()
                    .uploadImage(
                            new TypedFile("image/*", new File(path)),
                            article.getTitle()+i,
                            article.getTitle()+"description"+i,
                            new Callback<ImgurResponse<Image>>() {
                                @Override
                                public void success(ImgurResponse<Image> imageImgurResponse, retrofit.client.Response response) {
                                    if(imageImgurResponse.success) {
                                        System.out.println(imageImgurResponse.data.getLink());
                                        System.out.println(imageImgurResponse.data.getDescription());
                                        article.getImages().add(imageImgurResponse.data.getLink());
                                        if(article.getImages().size()==images.size()){
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
                                            ShuoApiService.getInstance().postArticle(observer, user, article, false);
                                        }
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    //Notify user of failure
                                }
                            }
                    );
        }


    }

    public void postArticle(String title,String content){
        if(isLogin()) {


            article.setTitle(title);
            article.setContent(content);
            article.setUserId(user.getUserId());
            article.setCreateTime(new Date());
            uploadImages();

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
        if(!images.contains(imageFile)) images.add(imageFile);
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
