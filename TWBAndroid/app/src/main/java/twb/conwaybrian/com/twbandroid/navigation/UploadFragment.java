package twb.conwaybrian.com.twbandroid.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;



import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.UploadPresenter;
import twb.conwaybrian.com.twbandroid.view.UploadView;

public class UploadFragment extends Fragment  implements UploadView ,View.OnClickListener {
    private UploadPresenter uploadPresenter;

    private EditText titleEditText;
    private EditText contentEditText;
    private Button postButton;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_upload,container,false);
        postButton=view.findViewById(R.id.post_button);
        titleEditText=view.findViewById(R.id.titile_editText);
        contentEditText=view.findViewById(R.id.content_editText);
        progressBar=view.findViewById(R.id.progressBar);




        postButton.setOnClickListener(this);
        uploadPresenter=new UploadPresenter(this);
        uploadPresenter.setProgressBarVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_button:
                postButton.setEnabled(false);
                uploadPresenter.setProgressBarVisibility(View.VISIBLE);
                uploadPresenter.postArticle(titleEditText.getText().toString(),contentEditText.getText().toString());
                break;
        }

    }

    @Override
    public void onPostArticle(boolean result) {
        postButton.setEnabled(true);
        uploadPresenter.setProgressBarVisibility(View.INVISIBLE);
        if(result){
            uploadPresenter.clear();
            Toast.makeText(getContext(),"upload success",Toast.LENGTH_SHORT).show();
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
}
