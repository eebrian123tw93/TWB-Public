package twb.conwaybrian.com.twbandroid.view;

public interface LoginView {
    public void onClearText();
    public void onLoginResult(boolean result);
    public void onSetProgressBarVisibility(int visibility);
    public void onForgetPassword();
    public void onRegister();
    public void onMessage(String message);
    public void onSetMessageColor(int color);
}
