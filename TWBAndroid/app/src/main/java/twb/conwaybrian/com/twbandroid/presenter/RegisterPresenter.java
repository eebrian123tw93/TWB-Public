package twb.conwaybrian.com.twbandroid.presenter;

import android.graphics.Color;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.model.User;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.RegisterView;

public class RegisterPresenter  extends TWBPresenter{
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
            registerView.onSetMessage("Username can not be empty",FancyToast.ERROR);
            registerView.onRegisterResult(false);

        }else if(password.isEmpty()){
            registerView.onSetMessage("Password can not be empty",FancyToast.ERROR);
            registerView.onRegisterResult(false);

        }else if(email.isEmpty()){
            registerView.onSetMessage("E-mail can not be empty",FancyToast.ERROR);
            registerView.onRegisterResult(false);

        }else {

            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        registerView.onRegisterResult(true);
                        registerView.onSetMessage("Register Success",FancyToast.SUCCESS);

                    }else {
                        try {
                            String responseString=response.errorBody().string();

                            registerView.onRegisterResult(false);
                            registerView.onSetMessage(responseString,FancyToast.ERROR);
                        } catch (IOException e) {
                            e.printStackTrace();
                            onError(e);
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    registerView.onSetMessage(e.getMessage(),FancyToast.ERROR);
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
