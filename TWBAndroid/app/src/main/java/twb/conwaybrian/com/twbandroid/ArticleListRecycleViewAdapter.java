package twb.conwaybrian.com.twbandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import twb.conwaybrian.com.twbandroid.model.Article;
public class ArticleListRecycleViewAdapter extends RecyclerView.Adapter<ArticleListRecycleViewAdapter.ViewHolder> {

    public List<Article> getArticleList() {
        return articleList;
    }

    private List<Article> articleList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView pointsTextView;
        private TextView viewsTextView;
        private ImageView articleImageView;


        public ViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textview);
            previewTextView=v.findViewById(R.id.preview_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textview);
            articleImageView=v.findViewById(R.id.article_imageView);
        }
    }

    public ArticleListRecycleViewAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public ArticleListRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleTextView.setText(articleList.get(position).getTitle());
        holder.previewTextView.setText(articleList.get(position).getContent());
        holder.pointsTextView.setText(String.valueOf(articleList.get(position).getPoints()));
        holder.viewsTextView.setText(String.valueOf(articleList.get(position).getViews()));
//        if(articleList.get(position).getImages().isEmpty())
//            holder.articleImageView.setVisibility(View.GONE);
//        else
//            Picasso.get().load(articleList.get(position).getImages().get(0)).into(holder.articleImageView);
//        Picasso.get().load("http://cdn.journaldev.com/wp-content/uploads/2016/11/android-image-picker-project-structure.png").resize(80,80).into(holder.articleImageView);

        Picasso.get().load("https://pbs.twimg.com/profile_images/941322358245154816/tF4dPHrS.jpg").into(holder.articleImageView);


    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
