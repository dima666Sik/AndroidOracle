package ua.gura.com.example.androidoracle.activities;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ua.gura.com.example.androidoracle.R;

public class MainActivity extends BaseActivity {
    private Button searchButton;
    private RecyclerView recyclerViewList;
    private EditText userNamedEditText;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private TextView errorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливается разметка

        searchButton = findViewById(R.id.searchButton);
        recyclerViewList = findViewById(R.id.repositoriesList);
        userNamedEditText = findViewById(R.id.userNameEditText);
        progressBar = findViewById(R.id.progress);
        emptyTextView = findViewById(R.id.emptyTextView);
        errorTextView = findViewById(R.id.errorTextView);

    }

}