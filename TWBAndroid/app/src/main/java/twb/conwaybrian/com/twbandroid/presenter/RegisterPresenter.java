package twb.conwaybrian.com.twbandroid.presenter;

import android.os.Handler;
import android.os.Looper;

import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.view.RegisterView;

public class RegisterPresenter {
    private RegisterView registerView;
    private User user;
    private Handler handler;

    public RegisterPresenter(RegisterView registerView){
        this.registerView=registerView;
        user=new User();
        handler=new Handler(Looper.getMainLooper());
    }
    public void clear(){
        registerView.onClearText();
    }
    public void doRegister(String username,String password,String email){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                registerView.onRegisterResult(true);
            }
        },5000);

    }
    public void setProgressBarVisibility(int visibility){
        registerView.onSetProgressBarVisibility(visibility);
    }

}
