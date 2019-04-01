package twb.conwaybrian.com.twbandroid.presenter.adapterpresenter;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.adatper.ArticleListRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.navigation.activity.ArticleActivity;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.presenter.TWBPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ArticleListRecycleViewHolderView;


public class ArticleListRecyclerViewHolderPresenter extends TWBPresenter {

    private List<Article> articles;

    public ArticleListRecyclerViewHolderPresenter() {
        articles = new ArrayList<>();
    }

    public void bindData(ArticleListRecycleViewAdapter.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ArticleListRecycleViewHolderView) {
            ArticleListRecycleViewHolderView viewHolderView = viewHolder;
            final Article article = articles.get(position);
            viewHolderView.onSetTile(article.getTitle());
            viewHolderView.onSetPreview(article.getContent());
            viewHolderView.onSetPoints(String.valueOf(article.getPoints()));
            viewHolderView.onSetViews(String.valueOf(article.getViews()));
            viewHolderView.onSetCommentCount(String.valueOf(article.getCommentCount()));
            viewHolderView.onSetUserId(article.getUserId());

            if (article.getLikeStatus() == 0) {
                if (article.getPoints() < 0) {
                    viewHolderView.onSetPointsImageView(R.drawable.dislike);
                } else if (article.getPoints() >= 0) {
                    viewHolderView.onSetPointsImageView(R.drawable.like);
                }
            } else {
                if (article.getLikeStatus() == 1) {
                    viewHolderView.onSetPointsImageView(R.drawable.like_color);

                } else if (article.getLikeStatus() == -1) {
                    viewHolderView.onSetPointsImageView(R.drawable.dislike_color);
                }
            }


            if (articles.get(position).getImages() == null || articles.get(position).getImages().isEmpty()) {
                viewHolderView.onSetArticleImageViewVisibility(View.GONE);
                if (articles.get(position).getImages() == null)
                    articles.get(position).setImages(new ArrayList<String>());
            } else {
                viewHolderView.onSetArticleImageViewVisibility(View.VISIBLE);
                viewHolderView.onSetArticleImageView(article.getImages().get(0));
            }

            viewHolderView.onSetClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra(ArticlePresenter.ARTICLE_ID, article.getArticleId());
                    intent.putExtra(ArticlePresenter.ARTICLE_TITLE, article.getTitle());
                    intent.putExtra(ArticlePresenter.ARTICLE_CONTENT, article.getContent());
                    intent.putExtra(ArticlePresenter.ARTICLE_USER_ID, article.getUserId());
                    intent.putExtra(ArticlePresenter.ARTICLE_POINTS, String.valueOf(article.getPoints()));
                    intent.putExtra(ArticlePresenter.ARTICLE_VIEWS, String.valueOf(article.getViews()));
                    intent.putExtra(ArticlePresenter.ARTICLE_COMMENT_COUNT, String.valueOf(article.getCommentCount()));
                    intent.putExtra(ArticlePresenter.ARTICLE_IMAGES, article.getImages().toArray(new String[article.getImages().size()]));
                    context.startActivity(intent);
                }
            });


        }


    }

    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(List<Article> articles) {
        this.articles.addAll(articles);

    }

    public void clear() {
        this.articles.clear();

    }


}