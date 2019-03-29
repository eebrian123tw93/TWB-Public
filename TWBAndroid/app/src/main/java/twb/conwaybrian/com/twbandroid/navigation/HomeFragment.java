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

import twb.conwaybrian.com.twbandroid.adatper.PageAdapter;
import twb.conwaybrian.com.twbandroid.R;

public class HomeFragment extends Fragment {

    private PageAdapter pageAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        TabLayout tabLayout =  view.findViewById(R.id.tabLayout);



        TabLayout.Tab  hotTab= tabLayout.newTab();
        hotTab.setText("熱門");
        hotTab.setIcon(R.drawable.popular);

        TabLayout.Tab  newTab= tabLayout.newTab();
        newTab.setText("最新");
        newTab.setIcon(R.drawable.new_icon);


        tabLayout.addTab(hotTab);
        tabLayout.addTab(newTab);
        final ViewPager viewPager = view.findViewById(R.id.viewPager);

        pageAdapter=new PageAdapter(this.getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
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
