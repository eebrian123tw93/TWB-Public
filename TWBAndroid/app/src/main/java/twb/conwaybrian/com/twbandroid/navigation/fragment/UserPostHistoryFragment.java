package twb.conwaybrian.com.twbandroid.navigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.SearchPresenter;
import twb.conwaybrian.com.twbandroid.presenter.UserPostHistoryPresenter;
import twb.conwaybrian.com.twbandroid.view.UserPostHistoryView;

public class UserPostHistoryFragment extends Fragment implements UserPostHistoryView {
    private UserPostHistoryPresenter userPostHistoryPresenter;
    private RecyclerView articleListRecyclerView;

    private UserPostHistoryFragment(){

    }

    public static UserPostHistoryFragment newInstance(String userId) {
        UserPostHistoryFragment fragment = new UserPostHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserPostHistoryPresenter.USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_post_history, container, false);


        articleListRecyclerView = view.findViewById(R.id.article_list_recycleView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        articleListRecyclerView.setLayoutManager(layoutManager);

        userPostHistoryPresenter = new UserPostHistoryPresenter(this,getArguments());


        return view;
    }

    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onSetArticleListRecyclerAdapter(RecyclerView.Adapter adapter) {
        articleListRecyclerView.setAdapter(adapter);
    }
}
