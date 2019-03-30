package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.navigation.fragment.ImageViewsFragment;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class ArticleActivity extends AppCompatActivity implements ArticleView,View.OnClickListener {


    private ArticlePresenter articlePresenter;




    private TwinklingRefreshLayout refreshLayout;
    private ImageViewsFragment focusFragment;

    private ImageView sendImageView;
    private EditText commentEditView;

    private RecyclerView articleDataRecyclerView;
    private long startTime;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_article);


        articleDataRecyclerView =findViewById(R.id.article_data_recyclerView);

        sendImageView=findViewById(R.id.send_imaeView);
        commentEditView=findViewById(R.id.comment_editText);

        refreshLayout=findViewById(R.id.refreshLayout);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                articlePresenter.refresh();
                articlePresenter.getArticleData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                articlePresenter.getArticleData();
            }
        });







        sendImageView.setOnClickListener(this);


//        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
//        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//        imageViewsRecyclerView.setLayoutManager(layoutManager);
//        imageViewsRecyclerView.setHasFixedSize(true);
//        imageViewsRecyclerView.addOnScrollListener(new CenterScrollListener());


        articleDataRecyclerView.setHasFixedSize(true);
        articleDataRecyclerView.setNestedScrollingEnabled(false);
        final LinearLayoutManager commentViewRecyclerLayoutManager = new LinearLayoutManager(getContext());
        commentViewRecyclerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        articleDataRecyclerView.setLayoutManager(commentViewRecyclerLayoutManager);



        articlePresenter=new ArticlePresenter(this,getIntent());


    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime=System.currentTimeMillis();

    }

    @Override
    protected void onPause() {
        super.onPause();
        endTime=System.currentTimeMillis();
        articlePresenter.postViewed(startTime,endTime);
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
    public void onSetArticleDataRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        articleDataRecyclerView.setAdapter(adapter);
    }



    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(),message,FancyToast.LENGTH_SHORT,type,false).show();
    }

    @Override
    public void onSendCommentResult(boolean result) {
        if(result){
            articlePresenter.clearComment();
            refreshLayout.startLoadMore();
        }else {

        }
    }

    @Override
    public void onClearCommentText() {
        commentEditView.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_imaeView:
                articlePresenter.sendComment(commentEditView.getText().toString());
            break;
        }
    }

    @Override
    public void onFinishRefreshOrLoad() {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onShowImageViewsFragment(List<String> images, int position) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(getSupportActionBar()!=null)getSupportActionBar().hide();
        if( focusFragment==null || !focusFragment.isAdded()){
            focusFragment= ImageViewsFragment.newInstance(images,position);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,focusFragment).commit();
        }else {

            getSupportFragmentManager().beginTransaction().show(focusFragment).commit();
            focusFragment.setPosition(position);
        }


    }

    @Override
    public void onBackPressed() {
        if(getSupportActionBar()!=null)getSupportActionBar().show();
        if(focusFragment!=null && focusFragment.isVisible()){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportFragmentManager().beginTransaction().hide(focusFragment).commit();

        }else {
            super.onBackPressed();
        }
    }
}
