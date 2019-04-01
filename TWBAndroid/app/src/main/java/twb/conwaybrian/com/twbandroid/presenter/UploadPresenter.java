package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.akiniyalocts.imgur_api.ImgurClient;
import com.akiniyalocts.imgur_api.model.Image;
import com.akiniyalocts.imgur_api.model.ImgurResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.presenter.adapterpresenter.ImageViewsRecyclerViewHolderPresenter;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadPresenter extends TWBPresenter {
    private UploadView uploadView;
    private Article article;
    private List<String>images;
    private List<String>canUploadImages;

    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;

    private static final String NOT_UPLOAD_ARTICLE="not_upload_article";
    private static final String IMAGES ="images";
    private static String ARTICLE="article";

    public UploadPresenter(UploadView uploadView){
        this.uploadView=uploadView;
        article = new Article();
        canUploadImages=new ArrayList<>();
        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,ImageViewsRecyclerViewHolderPresenter.Type.EDIT);
        images=imageViewsRecycleViewAdapter.getImages();
    }
    public  void uploadImages(){
        canUploadImages.clear();
        for(int i=0;i<images.size();i++){
            File file=new File(images.get(i));
            if(file.exists())canUploadImages.add(images.get(i));
        }


        for (int i=0;i<canUploadImages.size();i++) {
            final int index=i;
            final String path=canUploadImages.get(i);
            ImgurClient.getInstance()
                    .uploadImage(
                            new TypedFile("image/*", new File(path)),
                            article.getTitle()+i,
                            article.getTitle()+"description"+i,
                            new Callback<ImgurResponse<Image>>() {
                                @Override
                                public void success(ImgurResponse<Image> imageImgurResponse, retrofit.client.Response response) {
                                    if(imageImgurResponse.success) {
                                        if(checkAllImagesUploaded(path,imageImgurResponse.data.getLink()))postArticle();
//                                        uploadView.onPostArticle(true);
                                        uploadView.onSetMessage("Image upload "+(index+1)+" success",FancyToast.SUCCESS);
                                    }else {
                                        uploadView.onPostArticle(false);
                                        uploadView.onSetMessage("Image upload "+(index+1)+" failed",FancyToast.ERROR);
                                    }
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    uploadView.onPostArticle(false);
//                                    uploadView.onPostArticle(false);
                                    uploadView.onSetMessage("Image upload "+(index+1)+" failed",FancyToast.ERROR);
                                    //Notify user of failure
                                }
                            }
                    );
        }


    }

    public synchronized void  post(String title, String content){
        if (title.isEmpty() ){
            uploadView.onSetMessage("Title  can not be empty",FancyToast.ERROR);
            uploadView.onPostArticle(false);
        }else if(content.isEmpty()){
            uploadView.onSetMessage("Content  can not be empty",FancyToast.ERROR);
            uploadView.onPostArticle(false);
        }else {
            if(content.length()>=65535){
                uploadView.onSetMessage("Content  too long",FancyToast.ERROR);
                uploadView.onPostArticle(false);
            }else {
                saveArticle(title, content, false);
                if (isLogin()) {
                    article.setTitle(title);
                    article.setContent(content);
                    if (images.isEmpty()) postArticle();
                    else uploadImages();
                } else {
                    uploadView.onSetMessage("Login first", FancyToast.INFO);
                    uploadView.onPostArticle(false);
                    if (userListener != null) userListener.toLoginPage();
                }
            }
        }
    }

    public void clear(){
        article.getImages().clear();
        canUploadImages.clear();
        uploadView.onClearText();
        imageViewsRecycleViewAdapter.clear();
        article=new Article();
    }

    public void setProgressBarVisibility(int visibility){
        uploadView.onSetProgressBarVisibility(visibility);
    }


    public void addImage(Uri uri){
        String   path = ImageViewsRecyclerViewHolderPresenter.getRealFilePath(context,uri);
        File file=new File(path);
        if(file.exists()){
            if(file.length()<10000000){
                imageViewsRecycleViewAdapter.addImage(path);
            }else {
                uploadView.onSetMessage("image more than 10 MB",FancyToast.WARNING);
            }

        }else {
            uploadView.onSetMessage(path+" not fount",FancyToast.WARNING);
        }

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
                    uploadView.onSetMessage("Post success",FancyToast.SUCCESS);
                    saveArticle("","",false);
                }else {
                    uploadView.onPostArticle(false);
                    uploadView.onSetMessage("Post failed",FancyToast.ERROR);
                }
            }

            @Override
            public void onError(Throwable e) {
                uploadView.onPostArticle(false);
                uploadView.onSetMessage(e.getMessage(),FancyToast.ERROR);
            }

            @Override
            public void onComplete() {

            }
        };
        ShuoApiService.getInstance().postArticle(observer, user, article, false);
    }

    public void saveArticle(String title,String content,boolean show){

        article.setTitle(title);
        article.setContent(content);
        String notUploadArticleJson=new Gson().toJson(article,Article.class);
        Type listType = new TypeToken<List<String>>() {}.getType();
        String imagesJson=new Gson().toJson(this.images,listType);
        context.getSharedPreferences(ARTICLE,Context.MODE_PRIVATE).edit()
                .putString(NOT_UPLOAD_ARTICLE,notUploadArticleJson)
                .putString(IMAGES,imagesJson).apply();
       if (show) uploadView.onSetMessage("Saved Article",FancyToast.SUCCESS );
    }

    public void readArticle(boolean show){

        SharedPreferences sharedPreferences=context.getSharedPreferences(ARTICLE,Context.MODE_PRIVATE);
        String articleJson=sharedPreferences.getString(NOT_UPLOAD_ARTICLE,"");
        String imagesJson=sharedPreferences.getString(IMAGES,"");
        Article  article=new Gson().fromJson(articleJson,Article.class);
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String>images=new Gson().fromJson(imagesJson,listType);
        if(article!=null){
            clear();
            this.article=article;
            if(article.getTitle() != null)uploadView.onSetTitle(article.getTitle());
            if(article.getContent() != null)uploadView.onSetContent(article.getContent());
            if(article.getImages()!=null)imageViewsRecycleViewAdapter.addImages(article.getImages());
            if(show)uploadView.onSetMessage("Read Article",FancyToast.SUCCESS );
        }
        if(images!=null)imageViewsRecycleViewAdapter.addImages(images);

    }

    public void setCancelViewEnable(boolean enable){
        if(enable)
            imageViewsRecycleViewAdapter.setState(ImageViewsRecyclerViewHolderPresenter.State.NOT_UPLOAD);
        else
            imageViewsRecycleViewAdapter.setState(ImageViewsRecyclerViewHolderPresenter.State.UPLOADING);
    }

    public synchronized boolean checkAllImagesUploaded(String fileName,String link){
        article.getImages().add(link);
        canUploadImages.remove(fileName);
        return canUploadImages.size()==0;
    }


}
