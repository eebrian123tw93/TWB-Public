package twb.conwaybrian.com.twbandroid.view;

public interface LoginView  extends TWBView{
    public void onClearText();
    public void onLoginResult(boolean result);
    public void onSetProgressBarVisibility(int visibility);
    public void onForgetPassword();
    public void onRegister();

}
