package twb.conwaybrian.com.twbandroid.navigation.fragment;

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

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ArticleListPresenter;
import twb.conwaybrian.com.twbandroid.view.ArticleListView;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

public class ArticleListFragment extends Fragment implements ArticleListView {
    private static String ARG_PARAM = "orderBy";
    private String orderBy;
    private ArticleListPresenter articleListPresenter;
    private RecyclerView recyclerView;
    private TwinklingRefreshLayout refreshLayout;

    public static ArticleListFragment newInstance(String type) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        orderBy = getArguments().getString(ARG_PARAM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);


        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                articleListPresenter.refresh();
                articleListPresenter.getArticleList(orderBy, 0, 1000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                articleListPresenter.getArticleList(orderBy, 1000);
            }
        });

        recyclerView = view.findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        VerticalRecyclerViewFastScroller fastScroller =  view.findViewById(R.id.fast_scroller);
        fastScroller.setVerticalFadingEdgeEnabled(true);
        fastScroller.setScrollBarFadeDuration(10);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(recyclerView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());



        articleListPresenter = new ArticleListPresenter(this);

        articleListPresenter.getArticleList(orderBy, 0, 1000);

        return view;
    }

    @Override
    public void onFinishRefreshOrLoad() {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
