package twb.conwaybrian.com.twbandroid.adatper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

import twb.conwaybrian.com.twbandroid.navigation.fragment.ArticleListFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int number;
    private HashMap<Integer, Fragment> fragmentHashMap ;

    public PageAdapter(FragmentManager fm,int number) {
        super(fm);
        fragmentHashMap=new HashMap<>();
        this.number=number;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentHashMap.get(position);

        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment =ArticleListFragment.newInstance("points");
                    break;
                case 1:
                    fragment =ArticleListFragment.newInstance("create_time");
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
