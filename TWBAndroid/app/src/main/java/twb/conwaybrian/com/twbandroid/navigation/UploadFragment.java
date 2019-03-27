package twb.conwaybrian.com.twbandroid.navigation;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.asksira.bsimagepicker.BSImagePicker;
import com.asksira.bsimagepicker.Utils;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import twb.conwaybrian.com.twbandroid.adatper.ImageViewsRecycleViewAdapter;
import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.UploadPresenter;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadFragment extends Fragment  implements UploadView ,View.OnClickListener ,  BSImagePicker.OnMultiImageSelectedListener,BSImagePicker.OnSingleImageSelectedListener {
    private UploadPresenter uploadPresenter;

    private EditText titleEditText;
    private EditText contentEditText;
    private ImageView postImageView;
    private ImageView cameraImageView;
    private ImageView galleryImageView;

    private ImageView clearImageView;
    private ImageView saveImageView;
    private ImageView restoreImageView;

    private ProgressBar progressBar;
    private RecyclerView imageViewsRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_upload,container,false);
        postImageView =view.findViewById(R.id.post_imageView);
        cameraImageView=view.findViewById(R.id.camera_imageView);
        galleryImageView=view.findViewById(R.id.gallery_imageView);
        clearImageView= view.findViewById(R.id.clear_imageView);
        saveImageView=view.findViewById(R.id.save_imageView);
        restoreImageView=view.findViewById(R.id.restore_imageView);

        titleEditText=view.findViewById(R.id.title_editText);
        contentEditText=view.findViewById(R.id.content_editText);
        progressBar=view.findViewById(R.id.progressBar);
        imageViewsRecyclerView=view.findViewById(R.id.imageViews_recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imageViewsRecyclerView.setLayoutManager(layoutManager);



        postImageView.setOnClickListener(this);
        cameraImageView.setOnClickListener(this);
        galleryImageView.setOnClickListener(this);
        saveImageView.setOnClickListener(this);
        clearImageView.setOnClickListener(this);
        restoreImageView.setOnClickListener(this);

        uploadPresenter=new UploadPresenter(this);
        uploadPresenter.setProgressBarVisibility(View.GONE);
        uploadPresenter.setImageViewsRecycleViewAdapter();
        uploadPresenter.readArticle(false);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_imageView:
              viewSetEnable(false);
                uploadPresenter.setProgressBarVisibility(View.VISIBLE);
                uploadPresenter.post(titleEditText.getText().toString(),contentEditText.getText().toString());
                break;
            case R.id.camera_imageView:
                BSImagePicker singleSelectionPicker = new BSImagePicker.Builder("twb.conwaybrian.com.twbandroid.fileprovider")
                        .setMaximumDisplayingImages(0) //Default: Integer.MAX_VALUE. Don't worry about performance :)
                        .setSpanCount(1) //Default: 3. This is the number of columns

                        .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in pixel.
                        .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of the dialog.
                        //Default: show. Set this if you don't want user to take photo.
                        .hideGalleryTile() //Default: show. Set this if you don't want to further let user select from a gallery app. In such case, I suggest you to set maximum displaying images to Integer.MAX_VALUE.
                        .setTag("A request ID") //Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
                         //Default: true. Set this if you do not want the picker to dismiss right after selection. But then you will have to dismiss by yourself.
                        .build();
                singleSelectionPicker.show(getChildFragmentManager(),"ticker");
                break;
            case R.id.gallery_imageView:
                BSImagePicker multiSelectionPicker = new BSImagePicker.Builder("twb.conwaybrian.com.twbandroid.fileprovider")
                        .isMultiSelect() //Set this if you want to use multi selection mode.
                        .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                        .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                        .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                        .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                        .disableOverSelectionMessage() //You can also decide not to show this over select message.
                        .build();
                multiSelectionPicker.show(getChildFragmentManager(),"ticker");
                break;
            case R.id.clear_imageView:
                new BottomDialog.Builder(getContext())
                        .setTitle("Warning ")
                        .setContent("This action will erase title , content, images")
                        .setPositiveText("Confirm")
                        .setPositiveBackgroundColorResource(R.color.colorPrimary)
                        //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(BottomDialog dialog) {
                               uploadPresenter.clear();
                            }
                        })
                        .setNegativeText("Cancel")
                        .show();
                break;
            case R.id.save_imageView:
                uploadPresenter.saveArticle(titleEditText.getText().toString(),contentEditText.getText().toString(),true);
                break;
            case R.id.restore_imageView:
                uploadPresenter.readArticle(true);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1101 && resultCode == Activity.RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            Uri uri=getImageUri(getContext(),imageBitmap);

                final ContentResolver cr = getContext().getContentResolver();
                final String[] p1 = new String[] {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DATE_TAKEN
                };
                Cursor c1 = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, p1, null, null, p1[1] + " DESC");
                if ( c1.moveToFirst() ) {
                    String uristringpic = "content://media/external/images/media/" + c1.getInt(0);
                    Uri uri = Uri.parse(uristringpic);
                    uploadPresenter.addImage(uri);
                }

        }

    }

    @Override
    public void onPostArticle(boolean result) {
        viewSetEnable(true);
        uploadPresenter.setProgressBarVisibility(View.GONE);
        if(result){
            uploadPresenter.clear();
        }
    }

    @Override
    public void onClearText() {
        titleEditText.setText("");
        contentEditText.setText("");
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onSetImageViewAdapter(ImageViewsRecycleViewAdapter adapter) {
        imageViewsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSetTitle(String title) {
        titleEditText.setText(title);
    }

    @Override
    public void onSetContent(String content) {
        contentEditText.setText(content);
    }

    @Override
    public void onSetMessage(String message,int type) {
        FancyToast.makeText(getContext(),message,FancyToast.LENGTH_SHORT,type,false).show();
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        for(Uri uri:uriList){
            uploadPresenter.addImage(uri);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        uploadPresenter.addImage(uri);
    }

    private void viewSetEnable(boolean enable){
        titleEditText.setEnabled(enable);
        contentEditText.setEnabled(enable);
        cameraImageView.setEnabled(enable);
        galleryImageView.setEnabled(enable);
        clearImageView.setEnabled(enable);
        restoreImageView.setEnabled(enable);
        saveImageView.setEnabled(enable);
        postImageView.setEnabled(enable);
        uploadPresenter.setCancelViewEnable(enable);
    }
}
