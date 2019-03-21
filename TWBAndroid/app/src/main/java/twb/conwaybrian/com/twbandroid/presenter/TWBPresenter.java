package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import twb.conwaybrian.com.twbandroid.TWBApplication;
import twb.conwaybrian.com.twbandroid.model.User;

public class TWBPresenter {
    protected Context context;
    private static String PROFILE="profile";
    public TWBPresenter(){
        this.context=TWBApplication.getContext();
    }
    public void saveUser(User user){
        context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE).edit()
                .putString("userId",user.getUserId())
                .putString("password",user.getPassword())
                .putString("email",user.getEmail()).apply();
    }
    public User readUser(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("userId","");
        String password=sharedPreferences.getString("password","");
        String email=sharedPreferences.getString("email","");
        if( userId.isEmpty() || password .isEmpty()||email.isEmpty()){
            return null;
        }else {
            return new User(userId,password,email);
        }
    }
}
