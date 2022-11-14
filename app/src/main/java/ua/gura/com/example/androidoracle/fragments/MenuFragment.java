package ua.gura.com.example.androidoracle.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.gura.com.example.androidoracle.BuildConfig;
import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.contract.ResponseListener;
import ua.gura.com.example.androidoracle.model.User;


public class MenuFragment extends BaseFragment {
    private static final String KEY_USER = "USER";
    private Button setQuestion;
    private User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        registerListener(User.class, listener);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            user = savedInstanceState
                    .getParcelable(KEY_USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_menu,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.settingButton)
                .setOnClickListener(v -> {
                    getAppContract().toSettingScreen(this, user);
                });
        view.findViewById(R.id.quitButton)
                .setOnClickListener(v -> {
                    getAppContract().cancel();
                });

        setQuestion = view
                .findViewById(R.id.setQuestionButton);
        setQuestion.setOnClickListener(v -> {
            getAppContract().toResultsScreen(this, user);
        });

        TextView versionEditText = view
                .findViewById(R.id.versionTextView);

        if (versionEditText != null) {
            versionEditText.setText(getString(
                    R.string.app_version,
                    BuildConfig.VERSION_NAME
            ));
        }
        updateView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_USER, user);
    }
    private void updateView() {
        setQuestion.setEnabled(user != null
                && user.isValid());
    }
    private ResponseListener<User> listener = user -> {
        this.user = user;
    };
}
