package twb.conwaybrian.com.twbandroid.presenter;


import com.shashank.sony.fancytoastlib.FancyToast;

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

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        user = new User();
    }

    public void clear() {
        loginView.onClearText();
    }

    public void doLogin(String username, String password) {
        if (username.isEmpty()) {
            loginView.onSetMessage("Username cant not be empty", FancyToast.ERROR);
            loginView.onLoginResult(false);
        } else if (password.isEmpty()) {

            loginView.onSetMessage("Password cant not be empty", FancyToast.ERROR);
            loginView.onLoginResult(false);
        } else {
            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        saveUser(user);
                        loginView.onLoginResult(true);
                        loginView.onSetMessage("Login Success", FancyToast.SUCCESS);
                        if (userListener != null) userListener.onLogin();

                    } else {
                        loginView.onLoginResult(false);
                        loginView.onSetMessage("Login Failed", FancyToast.ERROR);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    loginView.onLoginResult(false);

                    loginView.onSetMessage(e.getMessage(), FancyToast.ERROR);
                }

                @Override
                public void onComplete() {

                }
            };
            user.setUserId(username);
            user.setPassword(password);
            ShuoApiService.getInstance().login(observer, user, false);
        }

    }

    public void setProgressBarVisibility(int visibility) {
        loginView.onSetProgressBarVisibility(visibility);
    }

    public void forgetPassword() {
        loginView.onForgetPassword();
    }

    public void register() {
        loginView.onRegister();
    }

}
