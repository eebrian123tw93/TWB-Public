package twb.conwaybrian.com.twbandroid;

import android.app.Application;
import android.content.Context;

import com.akiniyalocts.imgur_api.ImgurClient;

public class TWBApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        ImgurClient.initialize("7611ce223ea0842");
    }
    //7611ce223ea0842
    //2f18ee6998162e046f860e709d0b0fcf080552e2
    public static Context getContext() {
        return context;
    }
}
