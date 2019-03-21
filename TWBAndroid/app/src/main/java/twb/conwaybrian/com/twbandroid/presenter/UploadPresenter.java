package twb.conwaybrian.com.twbandroid.presenter;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadPresenter extends TWBPresenter {
    private UploadView uploadView;
    public UploadPresenter(UploadView uploadView){
        this.uploadView=uploadView;
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
            Article article = new Article();
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
}
