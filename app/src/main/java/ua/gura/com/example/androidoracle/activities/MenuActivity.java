package ua.gura.com.example.androidoracle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.gura.com.example.androidoracle.BuildConfig;
import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.model.User;


public class MenuActivity extends BaseActivity {
    private Button setQuestion;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (User) bundle.get("USER");
        }
        findViewById(R.id.settingButton)
                .setOnClickListener(v -> {
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                });
        findViewById(R.id.quitButton)
                .setOnClickListener(v -> {
                    cancel();
                });

        setQuestion = findViewById(R.id.setQuestionButton);
        setQuestion.setOnClickListener(v -> {
            System.out.println(user);
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        });

        TextView versionEditText = findViewById(R.id.versionTextView);

        if (versionEditText != null) {
            versionEditText.setText(getString(
                    R.string.app_version,
                    BuildConfig.VERSION_NAME
            ));
        }
        updateView();
    }

    private void updateView() {
        System.out.println(user);
        setQuestion.setEnabled(user != null
                && user.isValid());
    }
}
