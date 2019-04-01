package twb.conwaybrian.com.twbandroid.navigation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.RegisterPresenter;
import twb.conwaybrian.com.twbandroid.view.RegisterView;

import static twb.conwaybrian.com.twbandroid.TWBApplication.getContext;

public class RegisterActivity extends AppCompatActivity implements RegisterView, View.OnClickListener {
    private RegisterPresenter registerPresenter;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private ProgressBar progressBar;

    private Button registerButton;
    private Button clearButton;

    private TextView messageTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_register);


        usernameEditText = findViewById(R.id.username_editText);
        passwordEditText = findViewById(R.id.password_editText);
        emailEditText = findViewById(R.id.email_editText);
        progressBar = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.register_button);
        clearButton = findViewById(R.id.clear_button);
        messageTextView = findViewById(R.id.message_textView);

        registerButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


        registerPresenter = new RegisterPresenter(this);
        registerPresenter.setProgressBarVisibility(View.GONE);
    }

    @Override
    public void onClearText() {
        usernameEditText.setText("");
        passwordEditText.setText("");
        emailEditText.setText("");
        messageTextView.setText("");
    }

    @Override
    public void onRegisterResult(boolean result) {
        registerPresenter.setProgressBarVisibility(View.GONE);
        registerButton.setEnabled(true);
        clearButton.setEnabled(true);
        if (result) {
            finish();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                registerPresenter.setProgressBarVisibility(View.VISIBLE);
                registerButton.setEnabled(false);
                clearButton.setEnabled(false);
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                registerPresenter.doRegister(usernameEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString());
                break;
            case R.id.clear_button:
                registerPresenter.clear();
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
