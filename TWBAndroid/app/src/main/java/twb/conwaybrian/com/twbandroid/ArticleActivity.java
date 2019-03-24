package twb.conwaybrian.com.twbandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class ArticleActivity extends AppCompatActivity implements ArticleView {

    private ArticlePresenter articlePresenter;

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView pointsTextView;
    private TextView viewsTextView;
    private RecyclerView imageViewsRecyclerView;
    private RecyclerView commentRecyclerView;
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
        imageViewsRecyclerView=findViewById(R.id.imageViews_recyclerView);
        commentRecyclerView=findViewById(R.id.comment_recyclerView);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageViewsRecyclerView.setLayoutManager(layoutManager);

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
    public void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter) {
        imageViewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetArticle(String title, String content, String points, String views) {
        titleTextView.setText(title);
        contentTextView.setText(content);
        pointsTextView.setText(points);
        viewsTextView.setText(views);
    }
}
