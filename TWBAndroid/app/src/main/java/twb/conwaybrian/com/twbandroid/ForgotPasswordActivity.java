package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import twb.conwaybrian.com.twbandroid.presenter.ForgotPasswordPresenter;
import twb.conwaybrian.com.twbandroid.view.ForgotPasswordView;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener,ForgotPasswordView {

    private EditText emailEditText;
    private ImageView sendImageView;
    private TextView messageTextView;
    private ProgressBar progressBar;

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_forgot_password);

        emailEditText=findViewById(R.id.email_editText);
        sendImageView =findViewById(R.id.send_imageView);
        messageTextView=findViewById(R.id.message_textView);
        progressBar=findViewById(R.id.progressBar);

        sendImageView.setOnClickListener(this);

        forgotPasswordPresenter=new ForgotPasswordPresenter(this);
        forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);

    }


    @Override
    public void onForgotPassword(boolean result) {
        if(result){
            forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);
            sendImageView.setEnabled(true);
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onMessage(String message) {
        sendImageView.setEnabled(true);
        forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);
        messageTextView.setText(message);
    }

    @Override
    public void onSetMessageColor(int color) {
        messageTextView.setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_imageView:
                forgotPasswordPresenter.setProgressBarVisibility(View.VISIBLE);
                sendImageView.setEnabled(false);

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                forgotPasswordPresenter.doForgotPassword(emailEditText.getText().toString());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return  super.onOptionsItemSelected(item);
    }
}
