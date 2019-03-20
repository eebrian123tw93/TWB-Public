package twb.conwaybrian.com.twbandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import twb.conwaybrian.com.twbandroid.model.Article;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Article> mData;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView pointsTextView;
        private TextView viewsTextView;


        public ViewHolder(View v) {
            super(v);
            titleTextView=v.findViewById(R.id.title_textview);
            previewTextView=v.findViewById(R.id.preview_textView);
            pointsTextView=v.findViewById(R.id.points_textView);
            viewsTextView=v.findViewById(R.id.views_textview);
        }
    }

    public MyAdapter(List<Article> data) {
        mData = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(mData.get(position).getTitle());
        holder.previewTextView.setText(mData.get(position).getContent());
        holder.pointsTextView.setText(String.valueOf(mData.get(position).getPoints()));
        holder.viewsTextView.setText(String.valueOf(mData.get(position).getViews()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
