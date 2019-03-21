package twb.conwaybrian.com.twbandroid.presenter;

import android.content.Context;

import twb.conwaybrian.com.twbandroid.TWBApplication;

public class TWBPresenter {
    protected Context context;
    public TWBPresenter(){
        this.context=TWBApplication.getContext();
    }
}
