package twb.conwaybrian.com.twbandroid.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.shashank.sony.fancytoastlib.FancyToast;

import twb.conwaybrian.com.twbandroid.R;
import twb.conwaybrian.com.twbandroid.presenter.ProfilePresenter;
import twb.conwaybrian.com.twbandroid.view.ProfileView;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {

    private ProfilePresenter profilePresenter;
    private Button logoutButton;
    private Button deleteUserButton;

    private TextView userIdTextView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = view.findViewById(R.id.logout_button);

        deleteUserButton = view.findViewById(R.id.delete_user_button);
        userIdTextView = view.findViewById(R.id.userId_textView);

        logoutButton.setOnClickListener(this);

        deleteUserButton.setOnClickListener(this);

        profilePresenter = new ProfilePresenter(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                profilePresenter.logout();
                break;

            case R.id.delete_user_button:
                new BottomDialog.Builder(getContext())
                        .setTitle("Warning ")
                        .setContent("This action will delete user")
                        .setPositiveText("Confirm")
                        .setPositiveBackgroundColorResource(R.color.colorPrimary)
                        //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(BottomDialog dialog) {
                                profilePresenter.deleteUser();
                            }
                        })
                        .setNegativeText("Cancel")
                        .show();
                break;

        }
    }

    @Override
    public void onSetUserId(String userId) {
        userIdTextView.setText(userId);
    }

    @Override
    public void onDeleteUserResult(boolean result) {

    }

    @Override
    public void onSetMessage(String message, int type) {

        FancyToast.makeText(getContext(), message, FancyToast.LENGTH_SHORT, type, false).show();
    }
}
