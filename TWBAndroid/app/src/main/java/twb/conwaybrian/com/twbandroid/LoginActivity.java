package twb.conwaybrian.com.twbandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import twb.conwaybrian.com.twbandroid.presenter.LoginPresenter;
import twb.conwaybrian.com.twbandroid.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView , View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private Button forgetPasswordButton;
    private Button loginButton;
    private Button clearButton;

    private TextView registerTextView;

    private TextView messageTextView;

    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        usernameEditText=findViewById(R.id.username_editText);
        passwordEditText=findViewById(R.id.password_editText);
        progressBar=findViewById(R.id.progressBar);
        forgetPasswordButton=findViewById(R.id.forget_password_button);
        loginButton=findViewById(R.id.login_button);
        clearButton=findViewById(R.id.clear_button);
        registerTextView=findViewById(R.id.register_textview);
        messageTextView=findViewById(R.id.message_textView);

        forgetPasswordButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);

        loginPresenter=new LoginPresenter(this);
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);

    }

    @Override
    public void onClearText() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    public void onLoginResult(boolean result) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);
        loginButton.setEnabled(true);
        forgetPasswordButton.setEnabled(true);
        clearButton.setEnabled(true);
        if(result){

        }else {

        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onForgetPassword() {

    }

    @Override
    public void onRegister() {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void onMessage(String message) {
        messageTextView.setText(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                loginPresenter.setProgressBarVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                forgetPasswordButton.setEnabled(false);
                clearButton.setEnabled(false);
                loginPresenter.doLogin(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                break;
            case R.id.clear_button:
                loginPresenter.clear();
                break;
            case R.id.forget_password_button:
                loginPresenter.forgetPassword();
                break;
            case R.id.register_textview:
                loginPresenter.register();
                break;
        }

    }


}
