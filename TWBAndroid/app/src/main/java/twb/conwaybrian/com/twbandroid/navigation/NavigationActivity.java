package twb.conwaybrian.com.twbandroid.navigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.NavigationPresenter;
import twb.conwaybrian.com.twbandroid.view.NavigationView;

public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,NavigationView {

    private BottomNavigationView bottomNavigationView;
    private Map<Integer,Fragment>fragmentHashMap;
    private Fragment focusFragment;

    private NavigationPresenter navigationPresenter;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_bottom_navigation);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        fragmentHashMap=new HashMap<>();

        focusFragment=new HomeFragment();
        fragmentHashMap.put(R.id.home,focusFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragmentHashMap.get(R.id.home)).commit();

        navigationPresenter=new NavigationPresenter(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment=fragmentHashMap.get(menuItem.getItemId());
        if(fragment==null) {
            switch (menuItem.getItemId()) {
                case R.id.upload:
                    fragment = new UploadFragment();

                    break;
                case R.id.profile:
                    if(navigationPresenter.isLogin()){
                        fragment = new ProfileFragment();
                    }else {
                        fragment=new LoginFragment();
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
            fragmentHashMap.put(menuItem.getItemId(),fragment);
        }
        showFragment(fragment);
        return true;
    }

    @Override
    public void onLogin() {
            Fragment fragment=new ProfileFragment();
            fragmentHashMap.put(R.id.profile,fragment);
             showFragment(fragment);
    }

    @Override
    public void onLogout() {
            Fragment fragment=new LoginFragment();
            fragmentHashMap.put(R.id.profile,fragment);
            showFragment(fragment);

    }



    public void showFragment(Fragment fragment){
        if(!fragment.isAdded()){
            getSupportFragmentManager().beginTransaction().hide(focusFragment).add(R.id.frame_layout, fragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).show(fragment).commit();
        }
        focusFragment=fragment;
    }

    @Override
    public void toLoginPage() {
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }


}