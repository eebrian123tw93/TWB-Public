package twb.conwaybrian.com.twbandroid.presenter;


import com.shashank.sony.fancytoastlib.FancyToast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import twb.conwaybrian.com.twbandroid.shuoApi.ShuoApiService;
import twb.conwaybrian.com.twbandroid.view.ProfileView;

public class ProfilePresenter extends TWBPresenter {
    private ProfileView profileView;

    public ProfilePresenter(ProfileView profileView) {
        this.profileView = profileView;
        profileView.onSetUserId(user.getUserId());
    }

    public void logout() {
        if (userListener != null) userListener.onLogout();
    }

    public void deleteUser() {
        Observer<Response<ResponseBody>> observer = new Observer<Response<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    profileView.onDeleteUserResult(true);
                    profileView.onSetMessage("Delete user Success", FancyToast.SUCCESS);
                    logout();

                } else {
                    profileView.onDeleteUserResult(false);
                    profileView.onSetMessage("Delete user Failed", FancyToast.ERROR);
                }
            }

            @Override
            public void onError(Throwable e) {
                profileView.onDeleteUserResult(false);

                profileView.onSetMessage(e.getMessage(), FancyToast.ERROR);
            }

            @Override
            public void onComplete() {

            }
        };
        ShuoApiService.getInstance().deleteUser(observer, user, false);
    }

    public void uploadedArticle() {
        if (userListener != null) userListener.onLogout();
    }


}
