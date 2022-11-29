package ua.gura.com.example.androidoracle.activities.details;

import android.os.Bundle;
import android.widget.TextView;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.activities.App;
import ua.gura.com.example.androidoracle.activities.main.BaseActivity;

import androidx.lifecycle.ViewModelProvider;

public class DetailsActivity extends BaseActivity {

    public static final String EXTRA_HOLIDAY_NAME = "HOLIDAY_NAME";

    private TextView nameTextView;
    private TextView descriptionTextView;

    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        String holidayName = getIntent().getStringExtra(EXTRA_HOLIDAY_NAME);
        if (holidayName.isEmpty()) {
            throw new RuntimeException("There is holiday haven`t name...");
        }

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this,app.getViewModelFactory());
        detailsViewModel = viewModelProvider.get(DetailsViewModel.class);
    }
}