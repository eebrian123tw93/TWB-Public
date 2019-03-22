package twb.conwaybrian.com.twbandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticleActivity extends AppCompatActivity implements ArticleView {

    private ArticlePresenter articlePresenter;

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView pointsTextView;
    private TextView viewsTextView;
    private RecyclerView recyclerView;

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

        Intent intent=getIntent();
        String title=intent.getStringExtra("article_id");
        String content=intent.getStringExtra("article_content");
        String points=intent.getStringExtra("article_points");
        String views=intent.getStringExtra("article_views");

        titleTextView.setText(title);
        contentTextView.setText(content);
        pointsTextView.setText(points);
        viewsTextView.setText(views);

        articlePresenter=new ArticlePresenter(this);
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
}
