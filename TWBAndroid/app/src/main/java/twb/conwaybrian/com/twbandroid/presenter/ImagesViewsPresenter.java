package twb.conwaybrian.com.twbandroid.presenter;

import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.adatper.ScalingImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.navigation.fragment.ImageViewsFragment;
import twb.conwaybrian.com.twbandroid.presenter.adapter.ScalingImageViewsRecyclerViewPresenter;
import twb.conwaybrian.com.twbandroid.view.ImageViewsView;

public class ImagesViewsPresenter extends TWBPresenter {
    private ImageViewsView imageViewsView;
    private ScalingImageViewsRecycleViewAdapter scalingImageViewsRecycleViewAdapter;

    public ImagesViewsPresenter(ImageViewsView imageViewsView, Bundle bundle){
        this.imageViewsView=imageViewsView;

        scalingImageViewsRecycleViewAdapter=new ScalingImageViewsRecycleViewAdapter(context);

        String imagesJson =bundle.getString(ImageViewsFragment.IMAGES,"");
        int position=bundle.getInt(ImageViewsFragment.POSITION,0);
        List<String> images=new Gson().fromJson(imagesJson,List.class);
        scalingImageViewsRecycleViewAdapter.addImages(images);
        setImageViewsRecycleViewAdapter();
        setImageViewsRecycleViewScrollPosition(position);

    }

    public void setImageViewsRecycleViewAdapter() {
        imageViewsView.onSetImageViewsRecyclerViewAdapter(scalingImageViewsRecycleViewAdapter);
    }
    public void setImageViewsRecycleViewScrollPosition(int position){
        imageViewsView.onImageViewsRecycleViewScrollPosition(position);
    }

}
