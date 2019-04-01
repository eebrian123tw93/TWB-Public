package twb.conwaybrian.com.twbandroid.adatper;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.presenter.adapterpresenter.ArticleListRecyclerViewHolderPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ArticleListRecycleViewHolderView;

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

    public ArticleListRecycleViewAdapter(Context context) {
        this.context=context;
        this.viewHolderPresenter=new ArticleListRecyclerViewHolderPresenter();
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
