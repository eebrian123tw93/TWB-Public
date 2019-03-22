package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;
import twb.conwaybrian.com.twbandroid.model.Article;
public class ArticleListRecycleViewAdapter extends RecyclerView.Adapter<ArticleListRecycleViewAdapter.ViewHolder> {

    public List<Article> getArticleList() {
        return articleList;
    }

    private List<Article> articleList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView pointsTextView;
        private TextView viewsTextView;
        private ImageView articleImageView;
        private CardView cardView;


        public ViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textView);
            previewTextView=v.findViewById(R.id.preview_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textView);
            articleImageView=v.findViewById(R.id.article_imageView);
            cardView=v.findViewById(R.id.card_view);
        }
    }

    public ArticleListRecycleViewAdapter(Context context,List<Article> articleList) {
        this.context=context;
        this.articleList = articleList;
    }

    @Override
    public ArticleListRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article_preview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Article article=articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.previewTextView.setText(article.getContent());
        holder.pointsTextView.setText(String.valueOf(article.getPoints()));
        holder.viewsTextView.setText(String.valueOf(article.getViews()));
//        if(articleList.get(position).getImages().isEmpty())
//            holder.articleImageView.setVisibility(View.GONE);
//        else
//            Picasso.get().load(articleList.get(position).getImages().get(0)).into(holder.articleImageView);
//        Picasso.get().load("http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png").resize(80,80).into(holder.articleImageView);

        Picasso.get().load("https://pbs.twimg.com/profile_images/941322358245154816/tF4dPHrS.jpg").into(holder.articleImageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ArticleActivity.class);
                intent.putExtra("article_id",article.getTitle());
                intent.putExtra("article_content",article.getContent());
                intent.putExtra("article_points",String.valueOf(article.getPoints()));
                intent.putExtra("article_views",String.valueOf(article.getViews()));

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
