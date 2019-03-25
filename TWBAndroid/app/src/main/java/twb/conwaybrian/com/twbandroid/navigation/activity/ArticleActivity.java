package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class ArticleActivity extends AppCompatActivity implements ArticleView {

    private ArticlePresenter articlePresenter;

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView pointsTextView;
    private TextView viewsTextView;
    private TextView commentCountTextView;
    private RecyclerView imageViewsRecyclerView;
    private RecyclerView commentRecyclerView;
    private ImageView pointsImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_article);

        titleTextView=findViewById(R.id.title_textView);
        contentTextView=findViewById(R.id.content_textView);
        pointsTextView=findViewById(R.id.points_textView);
        viewsTextView=findViewById(R.id.views_textView);
        commentCountTextView=findViewById(R.id.comment_count_textView);
        imageViewsRecyclerView=findViewById(R.id.imageViews_recyclerView);
        commentRecyclerView=findViewById(R.id.comment_recyclerView);
        pointsImageView=findViewById(R.id.points_imageView);


        final LinearLayoutManager imageViewRecyclerLayoutManager = new LinearLayoutManager(getContext());
        imageViewRecyclerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageViewsRecyclerView.setLayoutManager(imageViewRecyclerLayoutManager);

        final LinearLayoutManager commentViewRecyclerLayoutManager = new LinearLayoutManager(getContext());
        commentViewRecyclerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(commentViewRecyclerLayoutManager);



        articlePresenter=new ArticlePresenter(this,getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return  super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }

    @Override
    public void onSetImageViewAdapter(RecyclerView.Adapter adapter) {
        imageViewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetCommentViewAdapter(RecyclerView.Adapter adapter) {
        commentRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetArticle(String title, String content, String points, String views ,String commentCount) {
        onSetTitle(title);
        onSetContent(content);
        onSetPoints(points);
        onSetViews(views);
        onSetCommentCount(commentCount);
    }

    @Override
    public void onSetTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void onSetContent(String content) {
        contentTextView.setText(content);
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
    public void onSetPointsImageView(int res) {
        Glide.with(this).load(getDrawable(res)).into(pointsImageView);
    }

    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(),message,FancyToast.LENGTH_SHORT,type,false).show();
    }
}
