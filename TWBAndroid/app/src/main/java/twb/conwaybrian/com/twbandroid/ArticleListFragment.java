package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.model.Article;

public class ArticleListFragment extends Fragment {
    private static String ARG_PARAM = "type";
    private String type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString(ARG_PARAM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_article_list,container,false);
        List<Article>articles=new ArrayList<>();
        for(int i=0;i<100;i++){
            Article article=new Article();
            article.setTitle("Tndgsfdkjk"+type);
            articles.add(article);
        }


        MyAdapter myAdapter = new MyAdapter(articles);
        RecyclerView mList = view. findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(layoutManager);
        mList.setAdapter(myAdapter);


        return view;
    }
    public static ArticleListFragment newInstance(String type) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
