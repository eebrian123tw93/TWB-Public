package twb.conwaybrian.com.twbandroid.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.navigation.activity.ForgotPasswordActivity;
import twb.conwaybrian.com.twbandroid.navigation.activity.RegisterActivity;
import twb.conwaybrian.com.twbandroid.presenter.LoginPresenter;
import twb.conwaybrian.com.twbandroid.view.LoginView;

public class LoginFragment extends Fragment implements LoginView, View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private Button forgetPasswordButton;
    private Button loginButton;
    private Button clearButton;

    private TextView registerTextView;


    private LoginPresenter loginPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        usernameEditText = view.findViewById(R.id.username_editText);
        passwordEditText = view.findViewById(R.id.password_editText);
        progressBar = view.findViewById(R.id.progressBar);
        forgetPasswordButton = view.findViewById(R.id.forget_password_button);
        loginButton = view.findViewById(R.id.login_button);
        clearButton = view.findViewById(R.id.clear_button);
        registerTextView = view.findViewById(R.id.register_textview);


        forgetPasswordButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);
//
//
//
//
//    }

    @Override
    public void onClearText() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    @Override
    public void onLoginResult(boolean result) {
        loginPresenter.setProgressBarVisibility(View.INVISIBLE);
        loginButton.setEnabled(true);
        clearButton.setEnabled(true);
        forgetPasswordButton.setEnabled(true);
        if (result) {

        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onForgetPassword() {
        getContext().startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
    }

    @Override
    public void onRegister() {
        getContext().startActivity(new Intent(getContext(), RegisterActivity.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                loginPresenter.setProgressBarVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                forgetPasswordButton.setEnabled(false);
                clearButton.setEnabled(false);

                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                loginPresenter.doLogin(usernameEditText.getText().toString(), passwordEditText.getText().toString());
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


    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
