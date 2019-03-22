package twb.conwaybrian.com.twbandroid.presenter;


import twb.conwaybrian.com.twbandroid.view.ProfileView;

public class ProfilePresenter extends TWBPresenter {
    private ProfileView profileView;
    public ProfilePresenter (ProfileView profileView){
        this.profileView=profileView;
        profileView.onSetUserId(user.getUserId());
    }
    public void logout(){
        if(userListener!=null)userListener.onLogout();
    }

}
