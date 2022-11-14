package ua.gura.com.example.androidoracle.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.generator.GenerateAnswer;
import ua.gura.com.example.androidoracle.model.User;

public class QuestionFragment extends BaseFragment {
    private static final String TAG =
            QuestionFragment.class.getSimpleName();
    private static final String ARG_USER = "USER";

    private static final String KEY_GENERIC = "GET_GENERIC";

    private Button doneButton;
    private Button tryAgainButton;
    private TextView answerTextView;
    private TextView errorTextView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_GENERIC, answerTextView.getText().toString());
    }

    public static QuestionFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_question,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doneButton = view
                .findViewById(R.id.doneButton);
        tryAgainButton = view
                .findViewById(R.id.tryAgainButton);
        answerTextView = view
                .findViewById(R.id.getAnswer);
        errorTextView = view
                .findViewById(R.id.errorTextView);
        EditText myQuestion = view.findViewById(R.id.myQuestion);

        if (savedInstanceState != null) {
            answerTextView.setText(savedInstanceState.getString(KEY_GENERIC));
        }

        tryAgainButton.setOnClickListener(v -> {
            try {
                toSuccessState();
                answerTextView.setText(GenerateAnswer.newInstance(myQuestion.getText().toString(), getUser()).getAnswer());
            } catch (Exception e) {
                toErrorState(e);
            }
        });

        doneButton.setOnClickListener(v ->
                getAppContract().cancel());
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

    private User getUser() {
        return getArguments().getParcelable(ARG_USER);
    }

}
