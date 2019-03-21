package twb.conwaybrian.com.twbandroid.presenter;



import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.LoginView;

public class LoginPresenter extends TWBPresenter {
    private LoginView loginView;
    private User user;

    public LoginPresenter(LoginView loginView){
        this.loginView=loginView;
        user=new User();

    }

    public void clear(){
        loginView.onClearText();
    }
    public void doLogin(String username,String password){
        if(username.isEmpty()){
            loginView.onMessage("Username empty");
            loginView.onSetMessageColor(Color.RED);
        }else if(password.isEmpty()){
            loginView.onMessage("Password empty");
            loginView.onSetMessageColor(Color.RED);
        }else {
            Observer<Response<ResponseBody>>observer=new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {
                    switch (response.code()){
                        case 200:
                            loginView.onLoginResult(true);
                            loginView.onMessage("Login Successfully");
                            loginView.onSetMessageColor(Color.GREEN);

                            break;
                        default:
                            loginView.onLoginResult(false);
                            loginView.onMessage("Login Failed");
                            loginView.onSetMessageColor(Color.RED);
                            break;
                    }

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            user.setUserId(username);
            user.setPassword(password);
            ShuoApiService.getInstance().login(observer,user,false);
        }

    }
    public void setProgressBarVisibility(int visibility){
        loginView.onSetProgressBarVisibility(visibility);
    }
    public void forgetPassword(){
        loginView.onForgetPassword();
    }

    public void register(){
        loginView.onRegister();
    }

}
