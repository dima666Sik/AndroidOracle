package ua.gura.com.example.androidoracle.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.generator.GenerateAnswerService;
import ua.gura.com.example.androidoracle.model.User;


public class QuestionActivity extends BaseActivity {
    private static final String TAG =
            QuestionActivity.class.getSimpleName();

    private static final String KEY_GENERIC = "GET_GENERIC";

    private Button doneButton;
    private Button tryAgainButton;
    private TextView answerTextView;
    private TextView errorTextView;
    private GenerateAnswerService service;
    private ServiceConnection connection;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, GenerateAnswerService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_GENERIC, answerTextView.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(TAG, "QuestionActivity onServiceConnected");
                service = ((GenerateAnswerService.TestBinder) binder).generateAnswerService();
                if(service != null){
                    Log.i(TAG, "Service is bonded successfully!");
                }else {
                    Log.i(TAG,"Service is not bonded successfully!");
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                service = null;
            }
        };

        setContentView(R.layout.activity_ask_question); // устанавливается разметка
        doneButton = findViewById(R.id.doneButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);
        answerTextView = findViewById(R.id.getAnswer);
        errorTextView = findViewById(R.id.errorTextView);
        EditText myQuestion = findViewById(R.id.myQuestion);
        if (savedInstanceState != null) {
            answerTextView.setText(savedInstanceState.getString(KEY_GENERIC));
        }
        Bundle bundle = getIntent().getExtras();
        tryAgainButton.setOnClickListener(v -> {
            try {
                User user = null;
                if (bundle != null) {
                    user = (User) bundle.get("USER");
                }
                if (!TextUtils.isEmpty(myQuestion.getText())) {
                    toSuccessState();
                    System.out.println(service+"-----");
                    if(service == null) return;
                    answerTextView.setText(service.generateAnswerBndg(user,myQuestion.getText().toString()));
                } else {
                    Toast.makeText(this, "You not create question!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                toErrorState(e);
            }
        });

        doneButton.setOnClickListener(v ->
                cancel());
    }

    private void toSuccessState() {
        doneButton.setVisibility(View.VISIBLE);
        tryAgainButton.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
    }

    private void toErrorState(Throwable error) {
        Log.e(TAG, "Error!", error);
        doneButton.setVisibility(View.INVISIBLE);
        tryAgainButton.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
        if (error instanceof Exception) {
            errorTextView.setText(R.string.error);
        } else {
            errorTextView.setText(error.getMessage());
        }
    }
}
