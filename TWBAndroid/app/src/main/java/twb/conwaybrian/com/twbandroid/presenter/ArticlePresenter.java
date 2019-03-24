package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;

import twb.conwaybrian.com.twbandroid.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticlePresenter extends TWBPresenter {
    private ArticleView articleView;
    private Article article;
    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;

    public ArticlePresenter(ArticleView articleView, Intent intent){

        this.articleView=articleView;
        String title=intent.getStringExtra("article_id");
        String content=intent.getStringExtra("article_content");
        String points=intent.getStringExtra("article_points");
        String views=intent.getStringExtra("article_views");
        String [] images=intent.getStringArrayExtra("articles_images");
        article=new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setPoints(Integer.valueOf(points));
        article.setViews(Integer.valueOf(views));
        article.getImages().addAll(Arrays.asList(images));
        imageViewsRecycleViewAdapter=new ImageViewsRecycleViewAdapter(context,article.getImages());

        articleView.onSetArticle(article.getTitle(),article.getContent(),String.valueOf(article.getPoints()),String.valueOf(article.getViews()));
        articleView.onSetImageViewAdapter(imageViewsRecycleViewAdapter);

    }

}
