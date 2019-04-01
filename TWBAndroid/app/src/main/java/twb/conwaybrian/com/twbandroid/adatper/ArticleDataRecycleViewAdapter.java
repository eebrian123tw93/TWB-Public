package twb.conwaybrian.com.twbandroid.adatper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.model.Comment;
import twb.conwaybrian.com.twbandroid.presenter.ArticlePresenter;
import twb.conwaybrian.com.twbandroid.presenter.adapter.ArticleDataRecyclerCommentViewHolderPresenter;
import twb.conwaybrian.com.twbandroid.reactbutton.ReactButton;
import twb.conwaybrian.com.twbandroid.reactbutton.Reaction;
import twb.conwaybrian.com.twbandroid.view.adapter.ArticleDataRecyclerArticleViewHolderView;
import twb.conwaybrian.com.twbandroid.view.adapter.ArticleDataRecyclerCommentViewHolderView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;


public class ArticleDataRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context context;
    private boolean move;
    private int position;

    private ArticlePresenter articlePresenter;
    private static int ARTICLE_VIEW=0;
    private static int COMMENT_VIEW=1;

    private ArticleDataRecyclerCommentViewHolderPresenter articleDataRecyclerCommentViewHolderPresenter;

    public class CommentViewHolder extends RecyclerView.ViewHolder implements ArticleDataRecyclerCommentViewHolderView {

        private TextView userIdTextView;
        private TextView commentTextView;


        public CommentViewHolder(View v) {
            super(v);
            userIdTextView=v.findViewById(R.id.userId_textView);
            commentTextView=v.findViewById(R.id.comment_textView);
        }

        @Override
        public void onSetUserId(String userId) {
            userIdTextView.setText(userId);
        }

        @Override
        public void onSetComment(String comment) {
            commentTextView.setText(comment);
        }
    }
    public class ArticleViewHolder extends RecyclerView.ViewHolder implements ArticleDataRecyclerArticleViewHolderView {

        private TextView titleTextView;
        private TextView contentTextView;
        private TextView pointsTextView;
        private TextView viewsTextView;
        private TextView commentCountTextView;
        private RecyclerView imageViewsRecyclerView;

        private ReactButton pointsReactButton;



        public ArticleViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textView);
            contentTextView=v.findViewById(R.id.content_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textView);
            commentCountTextView=v.findViewById(R.id.comment_count_textView);
            imageViewsRecyclerView=v.findViewById(R.id.imageViews_recyclerView);
            pointsReactButton =v.findViewById(R.id.points_reactButton);
            final LinearLayoutManager imageViewRecyclerLayoutManager = new LinearLayoutManager(getContext());
            imageViewRecyclerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            imageViewsRecyclerView.setLayoutManager(imageViewRecyclerLayoutManager);

        }
    }

    public ArticleDataRecycleViewAdapter(Context context, ArticlePresenter articlePresenter) {
        this.context=context;

        this.articlePresenter=articlePresenter;
        this.articleDataRecyclerCommentViewHolderPresenter=new ArticleDataRecyclerCommentViewHolderPresenter();

    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMove(boolean move) {
        this.move = move;
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0)return ARTICLE_VIEW;
        else return COMMENT_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType==ARTICLE_VIEW){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_article_data, parent, false);
            RecyclerView.ViewHolder vh = new ArticleViewHolder(v);
            return vh;
        }else {
             v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_article_comment, parent, false);
            RecyclerView.ViewHolder vh = new CommentViewHolder(v);

            return vh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        System.out.println(position);
        if(viewHolder instanceof CommentViewHolder){
           articleDataRecyclerCommentViewHolderPresenter.bindData((CommentViewHolder) viewHolder,position);
        }else if(viewHolder instanceof ArticleViewHolder){
            final ArticleViewHolder holder=(ArticleViewHolder)viewHolder;
            holder.pointsReactButton.setReactClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Your Code
                    System.out.println(holder.pointsReactButton.getCurrentReaction().getType().toString());
                    articlePresenter.sendReaction(holder.pointsReactButton.getCurrentReaction().getType());

                }
            });
            holder.pointsReactButton.setReactDismissListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Your Code
                    System.out.println(holder.pointsReactButton.getCurrentReaction().getType().toString());
                    articlePresenter.sendReaction(holder.pointsReactButton.getCurrentReaction().getType());
                    return false;
                }
            });



            holder.titleTextView.setText(articlePresenter.article.getTitle());
            holder.contentTextView.setText(articlePresenter.article.getContent());
            holder.pointsTextView.setText(String.valueOf(articlePresenter.article.getPoints()));
            holder.viewsTextView.setText(String.valueOf(articlePresenter.article.getViews()));
            holder.commentCountTextView.setText(String.valueOf(articlePresenter.article.getCommentCount()));
            holder.imageViewsRecyclerView.setAdapter(articlePresenter.imageViewsRecycleViewAdapter);


            int res=0;
            Reaction.Type type=articlePresenter.defaultType;

            switch (type){
                case LIKE:
                    res=R.drawable.like;
                    break;
                case DISLIKE:
                    res=R.drawable.dislike;
                    break;
                case LIKE_COLOR:
                    res=R.drawable.like_color;
                    break;
                case DISLIKE_COLOR:
                    res=R.drawable.dislike_color;
                    break;
                default:
                    res=R.drawable.like;
                    break;
            }
//            TWBReactions.defaultReact.setReactIconId(res);
//            TWBReactions.defaultReact.setType(type);
            Reaction currentReaction=new Reaction(type,res);
            holder.pointsReactButton.setCurrentReaction(currentReaction);
            if(move){
                articlePresenter.setArticleDataRecyclerViewScroll(position);
            }




//            holder.pointsReactButton.setDefaultReaction(TWBReactions.defaultReact);

        }
    }

    public void  addComments(List<Comment>comments){
       articleDataRecyclerCommentViewHolderPresenter.addComments(comments);
        notifyDataSetChanged();

    }

    public void clear(){
       articleDataRecyclerCommentViewHolderPresenter.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return articleDataRecyclerCommentViewHolderPresenter.getItemCount()+1;
    }
}
