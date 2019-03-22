package twb.conwaybrian.com.twbandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

public class ArticleActivity extends AppCompatActivity implements ArticleView {

    private ArticlePresenter articlePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);



        articlePresenter=new ArticlePresenter(this);
    }
}
