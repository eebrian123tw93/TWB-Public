package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Intent;
import android.util.Log;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.adatper.ArticleDataRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.model.ArticleData;
import twb.conwaybrian.com.twbandroid.model.Comment;
import twb.conwaybrian.com.twbandroid.model.Like;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticlePresenter extends TWBPresenter implements ImageViewsRecycleViewAdapter.ShowImageViewsFragmentListener {

    private static final String TAG="ArticlePresenter";
    public static final String ARTICLE_ID="article_id";
    public static final String ARTICLE_TITLE="article_title";
    public static final String ARTICLE_CONTENT="article_content";
    public static final String ARTICLE_POINTS="article_points";
    public static final String ARTICLE_VIEWS="article_views";
    public static final String ARTICLE_COMMENT_COUNT="article_comment_count";
    public static final String ARTICLE_IMAGES="articles_images";
    private ArticleView articleView;
    public Article article;
    public ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;
    private ArticleDataRecycleViewAdapter articleDataRecycleViewAdapter;
    boolean viewed;
    public Reaction.Type defaultType;


    public ArticlePresenter(ArticleView articleView, Intent intent){

        this.articleView=articleView;
        String articleId=intent.getStringExtra(ARTICLE_ID);
        String title=intent.getStringExtra(ARTICLE_TITLE);
        String content=intent.getStringExtra(ARTICLE_CONTENT);
        String points=intent.getStringExtra(ARTICLE_POINTS);
        String views=intent.getStringExtra(ARTICLE_VIEWS);
        String commentCount=intent.getStringExtra(ARTICLE_COMMENT_COUNT);
        String [] images=intent.getStringArrayExtra(ARTICLE_IMAGES);
        article=new Article();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setContent(content);
        article.setPoints(Integer.valueOf(points));
        article.setViews(Integer.valueOf(views));
        article.setCommentCount(Integer.valueOf(commentCount));
        article.getImages().addAll(Arrays.asList(images));


        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,article.getImages(),ImageViewsRecycleViewAdapter.Type.VIEW,this);
        articleDataRecycleViewAdapter =new ArticleDataRecycleViewAdapter(context,new ArrayList<Comment>(),this);

        articleView.onSetArticleDataRecyclerViewAdapter(articleDataRecycleViewAdapter);

//        if(article.getPoints()>0){
            defaultType=Reaction.Type.LIKE;
//
//        }else if(article.getPoints()<0){
//            defaultType=Reaction.Type.DISLIKE;
//        }else {
//            defaultType=Reaction.Type.NO_LIKE;
//
//        }
//        getComments();

        getArticleData(true);
        viewed=true;

    }
    public  void setArticleDataRecyclerViewScroll(final int position){

//    if(moveToTop) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
//                        Thread.sleep(1000);
                    articleView.onSetArticleDataRecyclerViewScroll(position);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
//    }


    }

    @Deprecated
    public void getComments(){
        Observer<Response<JsonArray>> observer = new Observer<Response<JsonArray>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    JsonArray jsonArray = response.body();
                    System.out.println(jsonArray);
                    Type listType = new TypeToken<List<Comment>>() {}.getType();
                    List<Comment> commentList = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonArray, listType);
                    articleDataRecycleViewAdapter.addComments(commentList);
                    articleView.onFinishRefreshOrLoad();
                } else {
                    articleView.onSendCommentResult(false);
                    articleView.onSetMessage("comment load failed", FancyToast.ERROR);
                }

            }

            @Override
            public void onError(Throwable e) {
                articleView.onSendCommentResult(false);
                articleView.onSetMessage(e.getMessage(), FancyToast.ERROR);
            }

            @Override
            public void onComplete() {

            }
        };
        ShuoApiService.getInstance().getComments(observer,article,false);
    }

    public void getArticleData(final boolean moveToTop){
       articleDataRecycleViewAdapter.setMoveToTop(moveToTop);
        Observer<Response<JsonObject>> observer = new Observer<Response<JsonObject>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    System.out.println(jsonObject);
                    Type listType = new TypeToken<ArticleData>() {}.getType();
                    ArticleData articleData = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create().fromJson(jsonObject, listType);
                    articleDataRecycleViewAdapter.addComments(articleData.getComments());


                    switch (articleData.getLikeStatus()){
                        case 0:
                            defaultType=Reaction.Type.LIKE;
                            break;
                        case 1:
                            defaultType=Reaction.Type.LIKE_COLOR;
                            break;
                        case -1:
                            defaultType=Reaction.Type.DISLIKE_COLOR;
                            break;
                        default:
                            defaultType=Reaction.Type.LIKE;
                            break;
                    }

                    article.setCommentCount(articleData.getCommentCount());
                    article.setPoints(articleData.getPoints());
                    article.setViews(articleData.getViews());
                    articleDataRecycleViewAdapter.notifyItemChanged(0);
                    articleView.onFinishRefreshOrLoad();
                } else {
                    articleView.onSendCommentResult(false);
                    articleView.onSetMessage("comment load failed", FancyToast.ERROR);
                }

            }

            @Override
            public void onError(Throwable e) {
                articleView.onSendCommentResult(false);
                articleView.onSetMessage(e.getMessage(), FancyToast.ERROR);
            }

            @Override
            public void onComplete() {

            }
        };
        if(isLogin()){
            ShuoApiService.getInstance().getArticleDataPrivate(observer,user,article,false);
        }else {
            ShuoApiService.getInstance().getArticleDataPublic(observer,article,false);

        }

    }

    public void sendReaction(Reaction.Type type){
        if(isLogin()){
            Like like=new Like();
            like.setArticleId(article.getArticleId());
            like.setUserId(user.getUserId());
            if(defaultType!=type){

                if(defaultType==Reaction.Type.LIKE_COLOR && type==Reaction.Type.DISLIKE_COLOR
                        || defaultType==Reaction.Type.DISLIKE_COLOR && type==Reaction.Type.LIKE_COLOR
                        || defaultType==Reaction.Type.LIKE && type==Reaction.Type.LIKE_COLOR
                        || defaultType==Reaction.Type.LIKE && type==Reaction.Type.DISLIKE_COLOR
                        || defaultType==Reaction.Type.DISLIKE && type==Reaction.Type.LIKE_COLOR
                        || defaultType==Reaction.Type.DISLIKE && type==Reaction.Type.DISLIKE_COLOR
                        ) {
                    like.setLikeType(type);
                }else {
                    like.setLikeType(defaultType);
                }

                Observer<Response<ResponseBody>> observer=new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//                    article_view
//                        articleView.onSendCommentResult(true);
                            articleView.onSetMessage("reaction reply success", FancyToast.SUCCESS);
                        } else {
//                        articleView.onSendCommentResult(false);
                            articleView.onSetMessage("reaction reply failed", FancyToast.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                ShuoApiService.getInstance().like(observer,user,like,false);
            }

        }else {
            articleView.onSendCommentResult(false);
            articleView.onSetMessage("Login first", FancyToast.INFO);
            if(userListener!=null)userListener.toLoginPage();
        }
    }

    public void sendComment(String commentString){
        if(commentString.isEmpty()){
            articleView.onSendCommentResult(false);
            articleView.onSetMessage("Comment can not be empty", FancyToast.ERROR);
        } else if(isLogin()) {
            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
//                    article_view
                        articleView.onSendCommentResult(true);
                        articleView.onSetMessage("comment reply success", FancyToast.SUCCESS);
                    } else {
                        articleView.onSendCommentResult(false);
                        articleView.onSetMessage("comment reply failed", FancyToast.ERROR);
                    }

                }

                @Override
                public void onError(Throwable e) {
                    articleView.onSendCommentResult(false);
                    articleView.onSetMessage(e.getMessage(), FancyToast.ERROR);
                }

                @Override
                public void onComplete() {

                }
            };
            Comment comment = new Comment();
            comment.setArticleId(article.getArticleId());
            comment.setComment(commentString);

            ShuoApiService.getInstance().comment(observer, user, comment, false);
        }else {
            articleView.onSendCommentResult(false);
            articleView.onSetMessage("Login first", FancyToast.INFO);
            if(userListener!=null)userListener.toLoginPage();
        }
    }

    public void addComments(List<Comment>comments){
        articleDataRecycleViewAdapter.addComments(comments);
    }

    public void clearComment(){
        articleView.onClearCommentText();
    }

    public void refresh(){
        articleDataRecycleViewAdapter.clear();
    }

    @Override
    public void onShowImageViewsFragment(List<String> images,int position) {
        articleView.onShowImageViewsFragment(images, position);
    }

    public void postViewed(long startTime,long endTime){
        double time=(endTime-startTime)/1000;
        if(time > 3){
            //post viewed
            if(viewed) {
                Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (responseBodyResponse.isSuccessful()) {
                            Log.i(TAG, "view++");
                        } else {
                            articleView.onSetMessage("something wrong", FancyToast.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                ShuoApiService.getInstance().viewed(observer, article, true);

                viewed = false;
            }
        }
    }
}
