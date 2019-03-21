package twb.conwaybrian.com.twbandroid.presenter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            registerView.onMessage("username empty");
            registerView.onSetMessageColor(Color.RED);
        }else if(password.isEmpty()){
            registerView.onMessage("password empty");
            registerView.onSetMessageColor(Color.RED);
        }else if(email.isEmpty()){
            registerView.onMessage("email empty");
            registerView.onSetMessageColor(Color.RED);
        }else {

            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {

                    switch (response.code()) {
                        case 204:
                            registerView.onRegisterResult(true);
                            registerView.onMessage("Register Successfully");
                            registerView.onSetMessageColor(Color.GREEN);
                            break;
                        default:

                            try {
                                String responseString=response.errorBody().string();
                                registerView.onRegisterResult(false);
                                registerView.onMessage(responseString);
                                registerView.onSetMessageColor(Color.RED);
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                                onError(e);
                            }
                    }

                }

                @Override
                public void onError(Throwable e) {
                    registerView.onMessage(e.getMessage());
                    registerView.onSetMessageColor(Color.RED);
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
