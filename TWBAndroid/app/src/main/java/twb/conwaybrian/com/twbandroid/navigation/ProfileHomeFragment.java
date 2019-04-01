package twb.conwaybrian.com.twbandroid.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.adatper.ProfileHomePageAdapter;

public class ProfileHomeFragment extends Fragment {
    private ProfileHomePageAdapter profileHomePageAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);


        TabLayout.Tab profileTab = tabLayout.newTab();
        profileTab.setText("簡介");
        profileTab.setIcon(R.drawable.user);

        TabLayout.Tab postHistoryTab = tabLayout.newTab();
        postHistoryTab.setText("上傳的文章");
        postHistoryTab.setIcon(R.drawable.post);

//        TabLayout.Tab  mostViewsTab= tabLayout.newTab();
//        mostViewsTab.setText("觀看最多");
//        mostViewsTab.setIcon(R.drawable.group_of_people);

        tabLayout.addTab(profileTab);
        tabLayout.addTab(postHistoryTab);

        final ViewPager viewPager = view.findViewById(R.id.view_pager);
        profileHomePageAdapter = new ProfileHomePageAdapter(this.getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(profileHomePageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

}
