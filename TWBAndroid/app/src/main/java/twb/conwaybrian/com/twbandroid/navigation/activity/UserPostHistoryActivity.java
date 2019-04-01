package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.navigation.fragment.UserPostHistoryFragment;
import twb.conwaybrian.com.twbandroid.presenter.UserPostHistoryPresenter;

public class UserPostHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_user_post_history);

        Fragment fragment = UserPostHistoryFragment.newInstance(getIntent().getStringExtra(UserPostHistoryPresenter.USER_ID));
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
