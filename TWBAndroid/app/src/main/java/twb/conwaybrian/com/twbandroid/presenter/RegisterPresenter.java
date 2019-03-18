package twb.conwaybrian.com.twbandroid.presenter;

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

        Observer<Response<String>> observer=new Observer<Response<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<String> response) {
                switch (response.code()){
                    case 204:
                        registerView.onRegisterResult(true);
                        break;
                    default:
                        registerView.onRegisterResult(false);
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

        ShuoApiService.getInstance().register(observer,username,password,email,false);



    }
    public void setProgressBarVisibility(int visibility){
        registerView.onSetProgressBarVisibility(visibility);
    }

}
