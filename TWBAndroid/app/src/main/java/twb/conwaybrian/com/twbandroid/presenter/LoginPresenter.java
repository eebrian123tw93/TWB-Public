package twb.conwaybrian.com.twbandroid.presenter;



import android.os.Handler;
import android.os.Looper;

import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.view.LoginView;

public class LoginPresenter {
    LoginView loginView;
    User user;
    Handler handler;
     public LoginPresenter(LoginView loginView){
         this.loginView=loginView;
         user=new User();
         handler=new Handler(Looper.getMainLooper());
     }

     public void clear(){
         loginView.onClearText();
     }
     public void doLogin(String username,String password){

     }
    public void setProgressBarVisibility(int visibility){
        loginView.onSetProgressBarVisibility(visibility);
    }

}
