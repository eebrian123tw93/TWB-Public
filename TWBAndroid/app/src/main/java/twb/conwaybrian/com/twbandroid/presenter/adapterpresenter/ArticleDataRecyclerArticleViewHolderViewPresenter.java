package twb.conwaybrian.com.twbandroid.presenter.adapterpresenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.adapter.ArticleDataRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.adapter.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.navigation.activity.UserPostHistoryActivity;
import twb.conwaybrian.com.twbandroid.presenter.TWBPresenter;
import twb.conwaybrian.com.twbandroid.presenter.UserPostHistoryPresenter;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;

public class ArticleDataRecyclerArticleViewHolderViewPresenter extends TWBPresenter {
    private Article article;
    private boolean move;
    private int position;
    private RecyclerView.Adapter adapter;
    private ImageViewsRecycleViewAdapter imageViewsRecycleViewAdapter;
    private Reaction.Type currentType;
    private ScrollListener scrollListener;

    public ArticleDataRecyclerArticleViewHolderViewPresenter(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setCurrentType(Reaction.Type currentType) {
        this.currentType = currentType;
    }

    public void bindData(ArticleDataRecycleViewAdapter.ArticleViewHolder viewHolder) {
        final ArticleDataRecycleViewAdapter.ArticleViewHolder holder = viewHolder;
        holder.onSetReactClickListener();
        holder.onSetReactDismissListener();

        if (article != null) {
            holder.onSetTitle(article.getTitle());
            holder.onSetContent(article.getContent());
            holder.onSetPoints(String.valueOf(article.getPoints()));
            holder.onSetViews(String.valueOf(article.getViews()));
            holder.onSetCommentCount(String.valueOf(article.getCommentCount()));
            holder.onSetUserId(article.getUserId());
            holder.onSetDateTime(DateFormat.format(dateFormat, article.getCreateTime()).toString());
            holder.onSetUserClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId = article.getUserId();
                    Intent intent = new Intent(context, UserPostHistoryActivity.class);
                    intent.putExtra(UserPostHistoryPresenter.USER_ID, userId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }

        if (adapter != null)
            holder.onSetImageViewsRecyclerViewAdapter(adapter);

        if (currentType != null) {
            int res = 0;
            Reaction.Type type = currentType;

            switch (type) {
                case LIKE:
                    res = R.drawable.like;
                    break;
                case DISLIKE:
                    res = R.drawable.dislike;
                    break;
                case LIKE_COLOR:
                    res = R.drawable.like_color;
                    break;
                case DISLIKE_COLOR:
                    res = R.drawable.dislike_color;
                    break;
                default:
                    res = R.drawable.like;
                    break;
            }
//            TWBReactions.defaultReact.setReactIconId(res);
//            TWBReactions.defaultReact.setType(type);
            Reaction currentReaction = new Reaction(type, res);
            holder.onSetCurrentReaction(currentReaction);
        }
        if (move) {
            if (scrollListener != null) scrollListener.onSetArticleDataRecyclerViewScroll(position);
        }

    }

    public interface ScrollListener {
        void onSetArticleDataRecyclerViewScroll(int position);
    }

    public int getItemCount(){
        return article==null?0:1;
    }

}
