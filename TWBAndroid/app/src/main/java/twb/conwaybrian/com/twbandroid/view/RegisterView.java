package twb.conwaybrian.com.twbandroid.view;

import android.graphics.Color;

public interface RegisterView extends TWBView{
    void onClearText();
    void onRegisterResult(boolean result);
    void onSetProgressBarVisibility(int visibility);

}
