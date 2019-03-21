package twb.conwaybrian.com.twbandroid.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.SearchPresenter;
import twb.conwaybrian.com.twbandroid.view.SearchView;

public class SearchFragment extends Fragment implements SearchView {
    private SearchPresenter searchPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        searchPresenter=new SearchPresenter(this);
        return view;
    }
}
