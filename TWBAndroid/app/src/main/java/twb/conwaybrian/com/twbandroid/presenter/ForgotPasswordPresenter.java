package twb.conwaybrian.com.twbandroid.presenter;

import com.shashank.sony.fancytoastlib.FancyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ForgotPasswordView;

public class ForgotPasswordPresenter extends TWBPresenter {
    private ForgotPasswordView forgotPasswordView;

    public ForgotPasswordPresenter(ForgotPasswordView forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
    }

    public void doForgotPassword(final String email) {
        if (email.isEmpty()) {
            forgotPasswordView.onForgotPassword(false);
            forgotPasswordView.onSetMessage("Email cant not be empty", FancyToast.ERROR);
        } else {
            Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        forgotPasswordView.onForgotPassword(true);
                        forgotPasswordView.onSetMessage("請至" + email + "獲取密碼", FancyToast.SUCCESS);
                    } else {
                        forgotPasswordView.onForgotPassword(false);
                        forgotPasswordView.onSetMessage("查無此email", FancyToast.ERROR);

                    }
                }

                @Override
                public void onError(Throwable e) {
                    forgotPasswordView.onForgotPassword(false);

                    forgotPasswordView.onSetMessage(e.getMessage(), FancyToast.ERROR);
                }

                @Override
                public void onComplete() {

                }
            };
            ShuoApiService.getInstance().forgotPassword(observer, email, false);
        }
    }

    public void setProgressBarVisibility(int visibility) {
        forgotPasswordView.onSetProgressBarVisibility(visibility);
    }
}
