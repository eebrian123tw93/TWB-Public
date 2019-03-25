package twb.conwaybrian.com.twbandroid.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.model.Comment;

public class CommentListRecycleViewAdapter extends RecyclerView.Adapter<CommentListRecycleViewAdapter.ViewHolder> {


    private List<Comment> comments;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    public CommentListRecycleViewAdapter(Context context,List<Comment> comments) {
        this.context=context;
        this.comments = comments;
    }

    @Override
    public CommentListRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article_preview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Comment comment=comments.get(position);
    }

    public void  addComments(List<Comment>comments){
        this.comments.addAll(comments);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return comments.size();
    }
}
