package ua.gura.com.example.androidoracle.activities;

import android.os.Bundle;
import android.widget.TextView;

import ua.gura.com.example.androidoracle.R;

public class DetailsActivity extends BaseActivity {

    public static final String EXTRA_REPOSITORY_ID = "REPOSITORY_ID";

    private TextView nameTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        long repositoryId = getIntent().getLongExtra(EXTRA_REPOSITORY_ID,-1);
        if(repositoryId==-1){
            throw new RuntimeException("There is repository ID");
        }
    }
}