package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Intent;

import java.util.Arrays;

import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticlePresenter extends TWBPresenter {
    public static final String ARTICLE_ID="article_id";
    public static final String ARTICLE_CONTENT="article_content";
    public static final String ARTICLE_POINTS="article_points";
    public static final String ARTICLE_VIEWS="article_views";
    public static final String ARTICLE_COMMENT_COUNT="article_comment_count";
    public static final String ARTICLE_IMAGES="articles_images";
    private ArticleView articleView;
    private Article article;
    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;

    public ArticlePresenter(ArticleView articleView, Intent intent){

        this.articleView=articleView;
        String title=intent.getStringExtra(ARTICLE_ID);
        String content=intent.getStringExtra(ARTICLE_CONTENT);
        String points=intent.getStringExtra(ARTICLE_POINTS);
        String views=intent.getStringExtra(ARTICLE_VIEWS);
        String commentCount=intent.getStringExtra(ARTICLE_COMMENT_COUNT);
        String [] images=intent.getStringArrayExtra(ARTICLE_IMAGES);
        article=new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setPoints(Integer.valueOf(points));
        article.setViews(Integer.valueOf(views));
        article.setCommentCount(Integer.valueOf(commentCount));
        article.getImages().addAll(Arrays.asList(images));


        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,article.getImages(),ImageViewsRecycleViewAdapter.Type.VIEW);

        articleView.onSetArticle(article.getTitle(),article.getContent(),String.valueOf(article.getPoints()),String.valueOf(article.getViews()),String.valueOf(article.getCommentCount()));
        articleView.onSetImageViewAdapter(imageViewsRecycleViewAdapter);

        if(article.getPoints()>0){
            articleView.onSetDefaultPointsImageView(Reaction.Type.LIKE);
        }else if(article.getPoints()<0){
            articleView.onSetDefaultPointsImageView(Reaction.Type.DISLIKE);
        }else {
            articleView.onSetDefaultPointsImageView(Reaction.Type.NO_LIKE);
        }

    }

    public void sendComment(String comment){

    }


}
