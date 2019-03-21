package twb.conwaybrian.com.twbandroid.presenter;

import android.graphics.Color;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ForgotPasswordView;

public class ForgotPasswordPresenter extends TWBPresenter {
    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenter(ForgotPasswordView forgotPasswordView){
        this.forgotPasswordView=forgotPasswordView;
    }
    public void doForgotPassword(final String email){
        if(email.isEmpty()){
            forgotPasswordView.onMessage("email is empty");
            forgotPasswordView.onSetMessageColor(Color.RED);
        }else {
            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {

                    if(response.isSuccessful()){
                        forgotPasswordView.onForgotPassword(true);
                        forgotPasswordView.onMessage("請至" + email + "獲取密碼");
                        forgotPasswordView.onSetMessageColor(Color.GREEN);
                    }else {
                        forgotPasswordView.onForgotPassword(false);
                        forgotPasswordView.onMessage("查無此email");
                        forgotPasswordView.onSetMessageColor(Color.RED);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    forgotPasswordView.onForgotPassword(false);
                    forgotPasswordView.onMessage(e.getMessage());
                    forgotPasswordView.onSetMessageColor(Color.RED);

                }

                @Override
                public void onComplete() {

                }
            };
            ShuoApiService.getInstance().forgotPassword(observer, email, false);
        }
    }
    public void setProgressBarVisibility(int visibility){
        forgotPasswordView.onSetProgressBarVisibility(visibility);
    }
}
