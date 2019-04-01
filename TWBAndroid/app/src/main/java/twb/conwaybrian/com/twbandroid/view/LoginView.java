package twb.conwaybrian.com.twbandroid.view;

public interface LoginView extends TWBView {
    void onClearText();

    void onLoginResult(boolean result);

    void onSetProgressBarVisibility(int visibility);

    void onForgetPassword();

    void onRegister();

}
