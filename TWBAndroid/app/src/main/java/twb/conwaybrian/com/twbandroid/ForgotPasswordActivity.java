package twb.conwaybrian.com.twbandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import twb.conwaybrian.com.twbandroid.presenter.ForgotPasswordPresenter;
import twb.conwaybrian.com.twbandroid.view.ForgotPasswordView;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener,ForgotPasswordView {

    private EditText emailEditText;
    private Button sendButton;
    private TextView messageTextView;
    private ProgressBar progressBar;

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText=findViewById(R.id.email_editText);
        sendButton=findViewById(R.id.send_button);
        messageTextView=findViewById(R.id.message_textView);
        progressBar=findViewById(R.id.progressBar);

        sendButton.setOnClickListener(this);

        forgotPasswordPresenter=new ForgotPasswordPresenter(this);
        forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);

    }


    @Override
    public void onForgotPassword(boolean result) {
        if(result){
            forgotPasswordPresenter.setProgressBarVisibility(View.INVISIBLE);
            sendButton.setEnabled(true);
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onMessage(String message) {
        sendButton.setEnabled(true);
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
            case R.id.send_button:
                forgotPasswordPresenter.setProgressBarVisibility(View.VISIBLE);
                sendButton.setEnabled(false);

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                forgotPasswordPresenter.doForgotPassword(emailEditText.getText().toString());
                break;
        }
    }
}
