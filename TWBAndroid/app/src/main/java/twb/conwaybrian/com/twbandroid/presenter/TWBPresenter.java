package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import twb.conwaybrian.com.twbandroid.TWBApplication;
import twb.conwaybrian.com.twbandroid.model.User;

public class TWBPresenter {
    protected Context context;
    protected static User user;
    static UserListener userListener;

    private static String PROFILE="profile";
     TWBPresenter(){
        this.context=TWBApplication.getContext();
    }
     void saveUser(User user){
        context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE).edit()
                .putString("userId",user.getUserId())
                .putString("password",user.getPassword())
                .putString("email",user.getEmail()).apply();
    }
    void readUser(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("userId","");
        String password=sharedPreferences.getString("password","");
        String email=sharedPreferences.getString("email","");
        if( userId.isEmpty() || password .isEmpty()){
             user= null;
        }else {
            user= new User(userId,password,email);
        }
    }
    public boolean isLogin(){
        return user!=null;
    }

    public interface UserListener{
        void onLogin();
        void  onLogout();
        void  toLoginPage();
    }

}
