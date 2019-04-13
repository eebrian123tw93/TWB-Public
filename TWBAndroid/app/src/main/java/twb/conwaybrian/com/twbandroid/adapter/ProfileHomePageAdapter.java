package twb.conwaybrian.com.twbandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

import twb.conwaybrian.com.twbandroid.navigation.ProfileFragment;
import twb.conwaybrian.com.twbandroid.navigation.fragment.UserPostHistoryFragment;

public class ProfileHomePageAdapter extends FragmentStatePagerAdapter {

    private int number;
    private HashMap<Integer, Fragment> fragmentHashMap;

    public ProfileHomePageAdapter(FragmentManager fm, int number) {
        super(fm);
        fragmentHashMap = new HashMap<>();
        this.number = number;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentHashMap.get(position);

        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new ProfileFragment();
                    break;
                case 1:
                    fragment = new UserPostHistoryFragment();
                    break;
//                case 2:
//                    fragment =ArticleListFragment.newInstance("view");
//                    break;

            }
            fragmentHashMap.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return number;
    }
}
