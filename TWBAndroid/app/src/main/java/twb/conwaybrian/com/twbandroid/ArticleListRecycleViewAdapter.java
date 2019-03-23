package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
        private ImageView pointsImageView;
        private CardView cardView;
        private ConstraintLayout constraintLayout;


        public ViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textView);
            previewTextView=v.findViewById(R.id.preview_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textView);
            articleImageView=v.findViewById(R.id.article_imageView);
            pointsImageView=v.findViewById(R.id.points_imageView);
            cardView=v.findViewById(R.id.card_view);
            constraintLayout=v.findViewById(R.id.layout);
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

        if(article.getPoints()<0){
            holder.pointsImageView.setImageResource(R.drawable.dislike);
        }else if(article.getPoints()>0){
         holder.pointsImageView.setImageResource(R.drawable.like);
        }

        if(articleList.get(position).getImages()==null || articleList.get(position).getImages().isEmpty()){
//            Picasso.get().load("https://cdn1.medicalnewstoday.com/content/images/articles/322/322868/golden-retriever-puppy.jpg").into(holder.articleImageView);
//            holder.articleImageView.setVisibility(View.GONE);
//            holder.articleImageView.getLayoutParams().width=0;
        }
        else
        {
//            ImageView imageView=new ImageView(context);;
//            imageView.setId(View.generateViewId());
            Picasso.get().load(article.getImages().get(0)).into(holder.articleImageView);
//            holder.constraintLayout.addView(imageView);
//            ConstraintSet set = new ConstraintSet();
//
//            set.constrainHeight(imageView.getId(), dpToPx(90));
//            set.constrainWidth(imageView.getId(), dpToPx(90));
////             center horizontally in the container
//            set.centerVertically(imageView.getId(), ConstraintSet.PARENT_ID);
////             pin to the bottom of the container
//            set.connect(imageView.getId(),ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0);
//            // Apply the changes
//            set.applyTo(holder.constraintLayout);

        }


//        Picasso.get().load("https://i.imgur.com/0tb9ofV.jpg").into(holder.articleImageView);

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

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
