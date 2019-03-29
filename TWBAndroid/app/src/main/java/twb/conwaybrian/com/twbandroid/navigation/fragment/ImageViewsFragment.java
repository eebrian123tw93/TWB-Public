package twb.conwaybrian.com.twbandroid.navigation.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mengpeng.recyclerviewgallery.CarouselLayoutManager;
import com.mengpeng.recyclerviewgallery.CarouselZoomPostLayoutListener;
import com.mengpeng.recyclerviewgallery.CenterScrollListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ImagesViewsPresenter;
import twb.conwaybrian.com.twbandroid.presenter.SearchPresenter;
import twb.conwaybrian.com.twbandroid.view.ImageViewsView;
import twb.conwaybrian.com.twbandroid.view.TWBView;

public class ImageViewsFragment extends Fragment implements ImageViewsView,View.OnClickListener {
    public static final String IMAGES="images";
    public static final  String POSITION="position";

    private ImagesViewsPresenter imagesViewsPresenter;
    private RecyclerView imageViewsRecyclerView;
    private ConstraintLayout constraintLayout;

    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_imageviews,container,false);
        imageViewsRecyclerView=view.findViewById(R.id.imageViews_recyclerView);
        constraintLayout=view.findViewById(R.id.constraint_layout);

        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());


        imageViewsRecyclerView.setLayoutManager(layoutManager);
        imageViewsRecyclerView.setHasFixedSize(true);
        imageViewsRecyclerView.addOnScrollListener(new CenterScrollListener());
        constraintLayout.setOnClickListener(this);

        imagesViewsPresenter=new ImagesViewsPresenter(this,getArguments());



        return view;
    }
    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(),message,FancyToast.LENGTH_SHORT,type,false).show();
    }

    @Override
    public void onSetImageViewsRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        imageViewsRecyclerView.setAdapter(adapter);
    }

    public static ImageViewsFragment newInstance(List<String>images,int position) {
        ImageViewsFragment imageViewsFragment = new ImageViewsFragment();
        String imagesJson=new Gson().toJson(images);
        Bundle args = new Bundle();
        args.putString(IMAGES, imagesJson);
        args.putInt(POSITION,position);
        imageViewsFragment.setArguments(args);

        return imageViewsFragment;
    }

    public void setPosition(int position) {
        imagesViewsPresenter.setImageViewsRecycleViewScrollPosition(position);
    }

    @Override
    public void onImageViewsRecycleViewScrollPosition(int position) {

        imageViewsRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.constraint_layout:
                getFragmentManager().beginTransaction().hide(this).commit();
                break;
        }
    }
}
