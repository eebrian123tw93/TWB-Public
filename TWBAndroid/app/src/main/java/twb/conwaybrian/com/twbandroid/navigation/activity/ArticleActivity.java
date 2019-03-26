package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.mengpeng.recyclerviewgallery.CarouselLayoutManager;
import com.mengpeng.recyclerviewgallery.CarouselZoomPostLayoutListener;
import com.mengpeng.recyclerviewgallery.CenterScrollListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.reactbutton.ReactButton;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;
import twb.conwaybrian.com.twbandroid.reactbutton.TWBReactions;
import twb.conwaybrian.com.twbandroid.view.ArticleView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class ArticleActivity extends AppCompatActivity implements ArticleView,View.OnClickListener {

    private ArticlePresenter articlePresenter;

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView pointsTextView;
    private TextView viewsTextView;
    private TextView commentCountTextView;
    private RecyclerView imageViewsRecyclerView;
    private RecyclerView commentRecyclerView;
    private ReactButton pointsReactButton;

    private ImageView sendImageView;
    private EditText commentEditView;
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
        pointsReactButton =findViewById(R.id.points_reactButton);
        sendImageView=findViewById(R.id.send_button);
        commentEditView=findViewById(R.id.comment_editText);



        pointsReactButton.setReactClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Your Code
                System.out.println(pointsReactButton.getCurrentReaction().getType().toString());

            }
        });
        pointsReactButton.setReactDismissListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Your Code
                System.out.println(pointsReactButton.getCurrentReaction().getType().toString());
                return false;
            }
        });


        sendImageView.setOnClickListener(this);

        final LinearLayoutManager imageViewRecyclerLayoutManager = new LinearLayoutManager(getContext());
        imageViewRecyclerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageViewsRecyclerView.setLayoutManager(imageViewRecyclerLayoutManager);

//        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
//        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
//        imageViewsRecyclerView.setLayoutManager(layoutManager);
//        imageViewsRecyclerView.setHasFixedSize(true);
//        imageViewsRecyclerView.addOnScrollListener(new CenterScrollListener());



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
    public void onSetPointsImageView(Reaction.Type type) {
        int res=0;
        switch (type){
            case LIKE:
                res=R.drawable.like;
                break;
            case DISLIKE:
                res=R.drawable.dislike;
                break;
            case NO_LIKE:
                res=R.drawable.no_like;
                break;
            case LIKE_COLOR:
                res=R.drawable.like_color;
                break;
            case DISLIKE_COLOR:
                res=R.drawable.dislike_color;
                break;
            case NO_LIKE_COLORS:
                res=R.drawable.no_like_color;
                break;
            default:
                res=R.drawable.no_like;
                break;
        }
        TWBReactions.defaultReact.setReactIconId(res);
        TWBReactions.defaultReact.setType(type);
        pointsReactButton.setCurrentReaction(new Reaction(type,res));
    }
    @Override
    public void onSetDefaultPointsImageView(Reaction.Type type) {
        int res=0;
        switch (type){
            case LIKE:
                res=R.drawable.like;
                break;
            case DISLIKE:
                res=R.drawable.dislike;
                break;
            case NO_LIKE:
                res=R.drawable.no_like;
                break;
            case LIKE_COLOR:
                res=R.drawable.like_color;
                break;
            case DISLIKE_COLOR:
                res=R.drawable.dislike_color;
                break;
            case NO_LIKE_COLORS:
                res=R.drawable.no_like_color;
                break;
                default:
                    res=R.drawable.no_like;
                    break;
        }
        TWBReactions.defaultReact.setReactIconId(res);
        TWBReactions.defaultReact.setType(type);
        pointsReactButton.setDefaultReaction(TWBReactions.defaultReact);
    }

    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(),message,FancyToast.LENGTH_SHORT,type,false).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_imageView:
                articlePresenter.sendComment(commentEditView.getText().toString());
            break;
        }
    }
}
