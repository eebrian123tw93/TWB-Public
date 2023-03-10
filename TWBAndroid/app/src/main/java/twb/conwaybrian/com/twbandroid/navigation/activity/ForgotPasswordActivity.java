package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ForgotPasswordPresenter;
import twb.conwaybrian.com.twbandroid.view.ForgotPasswordView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, ForgotPasswordView {

    private EditText emailEditText;
    private ImageView sendImageView;

    private ProgressBar progressBar;

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.email_editText);
        sendImageView = findViewById(R.id.send_imageView);

        progressBar = findViewById(R.id.progressBar);

        sendImageView.setOnClickListener(this);

        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
        forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);

    }


    @Override
    public void onForgotPassword(boolean result) {
        forgotPasswordPresenter.setProgressBarVisibility(View.GONE);
        sendImageView.setEnabled(true);
        if (result) {

        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_imageView:
                forgotPasswordPresenter.setProgressBarVisibility(View.VISIBLE);
                sendImageView.setEnabled(false);

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                forgotPasswordPresenter.doForgotPassword(emailEditText.getText().toString());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
