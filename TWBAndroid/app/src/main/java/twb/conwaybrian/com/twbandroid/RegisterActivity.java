package twb.conwaybrian.com.twbandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import twb.conwaybrian.com.twbandroid.presenter.LoginPresenter;
import twb.conwaybrian.com.twbandroid.presenter.RegisterPresenter;
import twb.conwaybrian.com.twbandroid.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView ,View.OnClickListener {
    private RegisterPresenter registerPresenter;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private ProgressBar progressBar;

    private Button registerButton;
    private Button clearButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        usernameEditText=findViewById(R.id.username_editText);
        passwordEditText=findViewById(R.id.password_editText);
        emailEditText=findViewById(R.id.email_editText);
        progressBar=findViewById(R.id.progressBar);
        registerButton=findViewById(R.id.register_button);
        clearButton=findViewById(R.id.clear_button);

        registerButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);




        registerPresenter=new RegisterPresenter(this);
        registerPresenter.setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void onClearText() {
        usernameEditText.setText("");
        passwordEditText.setText("");
        emailEditText.setText("");
    }

    @Override
    public void onRegisterResult(boolean result) {
        registerPresenter.setProgressBarVisibility(View.INVISIBLE);
        registerButton.setEnabled(true);
        clearButton.setEnabled(true);
        if(result){
            Toast.makeText(this,"register successfully",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
        }else {
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_button:
                registerPresenter.setProgressBarVisibility(View.VISIBLE);
                registerButton.setEnabled(false);
                clearButton.setEnabled(false);
                registerPresenter.doRegister(usernameEditText.getText().toString(),passwordEditText.getText().toString(),emailEditText.getText().toString());
                break;
            case R.id.clear_button:
                registerPresenter.clear();
                break;
        }

    }
}
