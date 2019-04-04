package twb.conwaybrian.com.twbandroid.presenter.adapterpresenter;


import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.adatper.ArticleDataRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.model.Comment;
import twb.conwaybrian.com.twbandroid.presenter.TWBPresenter;
import twb.conwaybrian.com.twbandroid.view.adapterview.ArticleDataRecyclerCommentViewHolderView;

public class ArticleDataRecyclerCommentViewHolderPresenter extends TWBPresenter {

    private List<Comment> comments;

    public ArticleDataRecyclerCommentViewHolderPresenter() {
        this.comments = new ArrayList<>();
    }

    public void bindData(ArticleDataRecycleViewAdapter.CommentViewHolder viewHolder, int position) {
        ArticleDataRecyclerCommentViewHolderView holderView = viewHolder;
        Comment comment = comments.get(position - 1);
        holderView.onSetUserId(comment.getUserId());
        holderView.onSetComment(comment.getComment());
        holderView.onSetDateTime(DateFormat.format(dateFormat, comment.getCommentTime()).toString());
    }

    public int getItemCount() {
        return comments.size();
    }

    public void addComments(List<Comment> comments) {
        for (int i = this.comments.size(); i < comments.size(); i++) {
            this.comments.add(comments.get(i));
        }


    }

    public void clear() {
        this.comments.clear();

    }

}
