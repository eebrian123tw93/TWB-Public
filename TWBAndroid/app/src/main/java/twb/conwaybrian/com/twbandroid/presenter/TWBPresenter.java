package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import twb.conwaybrian.com.twbandroid.TWBApplication;
import twb.conwaybrian.com.twbandroid.model.User;

public class TWBPresenter {
    protected Context context;
    protected static User user;
    static UserListener userListener;
    private static final String USER_PROFILE="user_profile";


    private static String PROFILE="profile";
     public TWBPresenter(){
        this.context=TWBApplication.getContext();
    }
     void saveUser(User user){


         String profileJson=new Gson().toJson(user,User.class);

         context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE).edit()
                .putString(USER_PROFILE,profileJson).apply();
    }
    void readUser(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PROFILE,Context.MODE_PRIVATE);
        String profileJson=sharedPreferences.getString(USER_PROFILE,"");
        User user=new Gson().fromJson(profileJson,User.class);
        if( user==null || user.getUserId()==null || user.getPassword()==null||user.getUserId().isEmpty() || user.getPassword() .isEmpty()){
             this.user= null;
        }else {
            this.user= new User(user.getUserId(),user.getPassword(),user.getEmail());
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
