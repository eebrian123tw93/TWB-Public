package twb.conwaybrian.com.twbandroid.presenter;

import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.view.NavigationView;

public class NavigationPresenter {
    private NavigationView navigationView;
    private User user;
    public NavigationPresenter(NavigationView navigationView){
        this.navigationView=navigationView;
    }
    public void setUserProfile(String userId,String password,String email){
        user=new User(userId,password,email);
    }


}
