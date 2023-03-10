package twb.conwaybrian.com.twbandroid.navigation;

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
import twb.conwaybrian.com.twbandroid.presenter.SearchPresenter;
import twb.conwaybrian.com.twbandroid.view.SearchView;

public class SearchFragment extends Fragment implements SearchView {
    private SearchPresenter searchPresenter;
    private android.widget.SearchView searchView;
    private RecyclerView searchRecyclerView;
    private TwinklingRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setAutoLoadMore(true);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                searchPresenter.loadMore();
            }
        });


        searchView = view.findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPresenter.clear();
                searchPresenter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPresenter.clear();
                if (newText.length() >= 3) {
                    searchPresenter.search(newText);
                }
                return false;
            }
        });

        searchRecyclerView = view.findViewById(R.id.search_recyclerview_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchPresenter = new SearchPresenter(this);

        return view;
    }

    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter) {
        searchRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onFinishRefreshOrLoad() {
        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();
    }

}
