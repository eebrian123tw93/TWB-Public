package twb.conwaybrian.com.twbandroid.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.Map;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.NavigationPresenter;
import twb.conwaybrian.com.twbandroid.view.NavigationView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView {

    private BottomNavigationView bottomNavigationView;
    private Map<Integer, Fragment> fragmentHashMap;
    private Fragment focusFragment;

    private NavigationPresenter navigationPresenter;

    private int exitCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_bottom_navigation);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        fragmentHashMap = new HashMap<>();

        focusFragment = new HomeFragment();
        fragmentHashMap.put(R.id.home, focusFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragmentHashMap.get(R.id.home)).commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(bottomNavigationView.getMenu().getItem(0).getTitle());
        }

        navigationPresenter = new NavigationPresenter(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = fragmentHashMap.get(menuItem.getItemId());
        if (fragment == null) {
            switch (menuItem.getItemId()) {
                case R.id.upload:
                    fragment = new UploadFragment();

                    break;
                case R.id.profile:
                    if (navigationPresenter.isLogin()) {
                        fragment = new ProfileHomeFragment();
                    } else {
                        fragment = new LoginFragment();
                    }
                    break;
                case R.id.search:
                    fragment = new SearchFragment();
                    break;
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                default:
                    fragment = new HomeFragment();
                    break;
            }
            fragmentHashMap.put(menuItem.getItemId(), fragment);
        }
        showFragment(fragment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(menuItem.getTitle());
        }
        return true;
    }

    @Override
    public void onLogin() {
        Fragment pre = fragmentHashMap.get(R.id.profile);
        getSupportFragmentManager().beginTransaction().remove(pre).commitAllowingStateLoss();
        Fragment fragment = new ProfileHomeFragment();
        fragmentHashMap.put(R.id.profile, fragment);
        showFragment(fragment);
    }

    @Override
    public void onLogout() {
        Fragment pre = fragmentHashMap.get(R.id.profile);
        getSupportFragmentManager().beginTransaction().remove(pre).commitAllowingStateLoss();
        Fragment fragment = new LoginFragment();
        fragmentHashMap.put(R.id.profile, fragment);
        showFragment(fragment);
    }


    public void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).add(R.id.frame_layout, fragment).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).show(fragment).commitAllowingStateLoss();
        }
        focusFragment = fragment;
    }

    @Override
    public void toLoginPage() {
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void onBackPressed() {
        exitCount++;
        if (exitCount == 1) {
            onSetMessage("再點擊一下離開", FancyToast.INFO);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        exitCount = 0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (exitCount == 2) {
            moveTaskToBack(true);
        }

    }

}
