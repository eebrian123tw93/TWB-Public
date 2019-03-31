package twb.conwaybrian.com.twbandroid.adatper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.navigation.activity.ArticleActivity;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.presenter.adapter.ArticleListRecyclerViewHolderPresenter;
import twb.conwaybrian.com.twbandroid.view.adapter.ArticleListRecycleViewHolderView;

public class ArticleListRecycleViewAdapter extends RecyclerView.Adapter<ArticleListRecycleViewAdapter.ViewHolder> {

    private Context context;
    private ArticleListRecyclerViewHolderPresenter viewHolderPresenter;





    public class ViewHolder extends RecyclerView.ViewHolder  implements ArticleListRecycleViewHolderView {
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView pointsTextView;
        private TextView viewsTextView;
        private TextView commentCountTextView;
        private ImageView articleImageView;
        private ImageView pointsImageView;
        private CardView cardView;

//        private     ArticleListRecyclerViewHolderPresenter viewHolderPresenter;
//        public ViewHolder(View v,ArticleListRecyclerViewHolderPresenter viewHolderPresenter) {
//            this(v);
////            this.viewHolderPresenter=viewHolderPresenter;
//        }
        public ViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textView);
            previewTextView=v.findViewById(R.id.preview_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textView);
            commentCountTextView=v.findViewById(R.id.comment_count_textView);
            articleImageView=v.findViewById(R.id.article_imageView);
            pointsImageView=v.findViewById(R.id.points_reactButton);
            cardView=v.findViewById(R.id.card_view);
        }

        @Override
        public void onSetTile(String title) {
            titleTextView.setText(title);

        }

        @Override
        public void onSetPreview(String preview) {
            previewTextView.setText(preview);
        }

        @Override
        public void onSetPoints(String points) {
            pointsTextView.setText(points);
        }

        @Override
        public void onSetViews(String views) {
            viewsTextView.setText(views);
        }

        @Override
        public void onSetCommentCount(String commentCount) {
            commentCountTextView.setText(commentCount);
        }

        @Override
        public void onSetArticleImageView(String fileName) {
            Glide.with(context).load(fileName).into(articleImageView);
        }

        @Override
        public void onSetArticleImageViewVisibility(int visibility) {
            articleImageView.setVisibility(visibility);
        }

        @Override
        public void onSetPointsImageView(int res) {
            Glide.with(context).load(res).into(pointsImageView);
        }

        @Override
        public void onSetClickListener(View.OnClickListener listener) {
            cardView.setOnClickListener(listener);
        }






    }

    public ArticleListRecycleViewAdapter(Context context,ArticleListRecyclerViewHolderPresenter viewHolderPresenter) {
        this.context=context;
        this.viewHolderPresenter=viewHolderPresenter;
    }

    @Override
    public ArticleListRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article_preview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return viewHolderPresenter.getItemCount();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        viewHolderPresenter.bindData(holder,position);
//        final Article article=articleList.get(position);
//        holder.titleTextView.setText(article.getTitle());
//        holder.previewTextView.setText(article.getContent());
//        holder.pointsTextView.setText(String.valueOf(article.getPoints()));
//        holder.viewsTextView.setText(String.valueOf(article.getViews()));
//        holder.commentCountTextView.setText(String.valueOf(article.getCommentCount()));
//
//        if(article.getLikeStatus()==0) {
//            if (article.getPoints() < 0) {
//                holder.pointsImageView.setImageResource(R.drawable.dislike);
//            } else if (article.getPoints() >= 0) {
//                holder.pointsImageView.setImageResource(R.drawable.like);
//            }
//        }else {
//            if (article.getLikeStatus() == 1) {
//                holder.pointsImageView.setImageResource(R.drawable.like_color);
//            } else if (article.getLikeStatus() == -1) {
//                holder.pointsImageView.setImageResource(R.drawable.dislike_color);
//            }
//        }
//
//        if(articleList.get(position).getImages()==null || articleList.get(position).getImages().isEmpty()){
//            holder.articleImageView.setVisibility(View.GONE);
//            if(articleList.get(position).getImages()==null)articleList.get(position).setImages(new ArrayList<String>());
//        }
//        else
//        {
//            holder.articleImageView.setVisibility(View.VISIBLE);
//            Glide.with(context).load(article.getImages().get(0)).into(holder.articleImageView);
//        }
//
//
//
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context,ArticleActivity.class);
//                intent.putExtra(ArticlePresenter.ARTICLE_ID,article.getArticleId());
//                intent.putExtra(ArticlePresenter.ARTICLE_TITLE,article.getTitle());
//                intent.putExtra(ArticlePresenter.ARTICLE_CONTENT,article.getContent());
//                intent.putExtra(ArticlePresenter.ARTICLE_POINTS,String.valueOf(article.getPoints()));
//                intent.putExtra(ArticlePresenter.ARTICLE_VIEWS,String.valueOf(article.getViews()));
//                intent.putExtra(ArticlePresenter.ARTICLE_COMMENT_COUNT,String.valueOf(article.getCommentCount()));
//                intent.putExtra(ArticlePresenter.ARTICLE_IMAGES,article.getImages().toArray(new String[article.getImages().size()]));
//                context.startActivity(intent);
//            }
//        });
    }

    public void addArticles(List<Article> articles){
        viewHolderPresenter.addArticles(articles);
        notifyDataSetChanged();
    }
    public void clear(){
        viewHolderPresenter.clear();
        notifyDataSetChanged();
    }


}
