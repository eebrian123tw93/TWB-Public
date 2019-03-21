package twb.conwaybrian.com.twbandroid.presenter;

import twb.conwaybrian.com.twbandroid.view.NavigationView;

public class NavigationPresenter  extends TWBPresenter implements TWBPresenter.UserListener {
    private NavigationView navigationView;
    public NavigationPresenter(NavigationView navigationView){
        this.navigationView=navigationView;
        readUser();
        userListener=this;
    }


    @Override
    public void onLogin() {

        navigationView.onLogin();
    }

    @Override
    public void onLogout() {
        navigationView.onLogout();
    }
}
