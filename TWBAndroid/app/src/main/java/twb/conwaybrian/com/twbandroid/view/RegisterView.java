package twb.conwaybrian.com.twbandroid.view;

import android.graphics.Color;

public interface RegisterView {
    public void onClearText();
    public void onRegisterResult(boolean result);
    public void onSetProgressBarVisibility(int visibility);
    public void onMessage(String message);
    public void onSetMessageColor(int color);
}
