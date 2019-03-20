package twb.conwaybrian.com.twbandroid.bottonNavigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import twb.conwaybrian.com.twbandroid.R;

public class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private Map<Integer,Fragment>fragmentHashMap;
    private Fragment focusFragment;


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
                    fragment = new ProfileFragment();
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
        if(!fragment.isAdded()){
            getSupportFragmentManager().beginTransaction().hide(focusFragment).add(R.id.frame_layout, fragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().hide(focusFragment).show(fragment).commit();
        }
        focusFragment=fragment;
        return true;
    }

}
