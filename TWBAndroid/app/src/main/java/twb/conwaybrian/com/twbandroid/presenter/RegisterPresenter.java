package twb.conwaybrian.com.twbandroid.presenter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.RegisterView;

public class RegisterPresenter {
    private RegisterView registerView;
    private User user;


    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
        user = new User();
    }
    public void clear(){
        registerView.onClearText();
    }
    public void doRegister(String username,String password,String email){

        if(username.isEmpty()){
            registerView.onMessage("username empty");
            registerView.onSetMessageColor(Color.RED);
        }else if(password.isEmpty()){
            registerView.onMessage("password empty");
            registerView.onSetMessageColor(Color.RED);
        }else if(email.isEmpty()){
            registerView.onMessage("email empty");
            registerView.onSetMessageColor(Color.RED);
        }else {

            Observer<Response<String>> observer = new Observer<Response<String>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<String> response) {
                    switch (response.code()) {
                        case 204:
                            registerView.onRegisterResult(true);
                            registerView.onMessage("Register Successfully");
                            registerView.onSetMessageColor(Color.GREEN);
                            break;
                        default:
                            registerView.onRegisterResult(false);
                            registerView.onMessage("Register Error,username already taken");
                            registerView.onSetMessageColor(Color.RED);
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
            user.setEmail(email);
            ShuoApiService.getInstance().register(observer, user, false);
        }
    }
    public void setProgressBarVisibility(int visibility){
        registerView.onSetProgressBarVisibility(visibility);
    }

}
