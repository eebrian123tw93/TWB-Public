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
        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,images,ImageViewsRecycleViewAdapter.Type.EDIT);
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
                                        if(checkAllImagesUploaded(imageImgurResponse.data.getLink()))postArticle();
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

    public void post(String title, String content){
        if (title.isEmpty() ){
            uploadView.onSetMessage("title empty");
            uploadView.onPostArticle(false);
        }else if(content.isEmpty()){
            uploadView.onSetMessage("content empty");
            uploadView.onPostArticle(false);
        }else {
            if (isLogin()) {
                article.setTitle(title);
                article.setContent(content);
                article.setUserId(user.getUserId());
                article.setCreateTime(new Date());
                uploadImages();

            } else {
                uploadView.onSetMessage("Login first");
                uploadView.onPostArticle(false);
                if (userListener != null) userListener.toLoginPage();
            }
        }
    }

    public void clear(){
        uploadView.onClearText();
        imageViewsRecycleViewAdapter.clear();
    }

    public void setProgressBarVisibility(int visibility){
        uploadView.onSetProgressBarVisibility(visibility);
    }


    public void addImage(Uri uri){
        imageViewsRecycleViewAdapter.addImage(uri);
    }

    public void setImageViewsRecycleViewAdapter() {
        uploadView.onSetImageViewAdapter(imageViewsRecycleViewAdapter);
    }

    private void postArticle(){
        Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    uploadView.onPostArticle(true);
                }else {

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

    public synchronized boolean checkAllImagesUploaded(String link){
        article.getImages().add(link);
        System.out.println(images.size());
        System.out.println(article.getImages().size());
        return article.getImages().size()==images.size();
    }


}
