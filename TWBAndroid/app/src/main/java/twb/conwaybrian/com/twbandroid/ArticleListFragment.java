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


import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.model.Article;
import twb.conwaybrian.com.twbandroid.presenter.ArticleListPresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleListView;

public class ArticleListFragment extends Fragment implements ArticleListView {
    private static String ARG_PARAM = "type";
    private String type;
    private ArticleListPresenter articleListPresenter;
    private ArticleListRecycleViewAdapter articleListRecycleViewAdapter;
    private PullLoadMoreRecyclerView recyclerView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getString(ARG_PARAM);
        articleListPresenter=new ArticleListPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_article_list,container,false);
        List<Article>articles=new ArrayList<>();
        articleListRecycleViewAdapter = new ArticleListRecycleViewAdapter(articles);


         recyclerView = view. findViewById(R.id.list_view);
         recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
             @Override
             public void onRefresh() {
                 articleListRecycleViewAdapter.getArticleList().clear();
                 articleListPresenter.getArticleList(type,1,10);
             }

             @Override
             public void onLoadMore() {
                 articleListPresenter.getArticleList(type,1,10);
             }
         });
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLinearLayout();
        recyclerView.setAdapter(articleListRecycleViewAdapter);

        articleListPresenter.getArticleList(type,1,10);

        return view;
    }
    public static ArticleListFragment newInstance(String type) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onGetArticles(List<Article> articleList) {
        recyclerView.setPullLoadMoreCompleted();
        articleListRecycleViewAdapter.getArticleList().addAll(articleList);
        articleListRecycleViewAdapter.notifyDataSetChanged();
    }
}
