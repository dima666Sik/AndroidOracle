package ua.gura.com.example.androidoracle.activities.details;

import android.os.Bundle;
import android.widget.TextView;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.activities.App;
import ua.gura.com.example.androidoracle.activities.main.BaseActivity;
import ua.gura.com.example.androidoracle.activities.model.Holiday;

import androidx.lifecycle.ViewModelProvider;

public class DetailsActivity extends BaseActivity {

    public static final String EXTRA_HOLIDAY_NAME = "HOLIDAY_NAME";
    public static final String EXTRA_HOLIDAY_COUNTY_CODE = "HOLIDAY_COUNTY_CODE";
    public static final String EXTRA_HOLIDAY_DATE = "HOLIDAY_DATE";
    public static final String EXTRA_HOLIDAY_LAUNCH_YEAR = "HOLIDAY_LAUNCH_YEAR";

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView launchYearTextView;
    private TextView countryCodeTextView;

    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTextView = findViewById(R.id.nameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        launchYearTextView = findViewById(R.id.launchYearTextView);
        countryCodeTextView = findViewById(R.id.countryCodeTextView);

        String holidayName = getIntent().getStringExtra(EXTRA_HOLIDAY_NAME);
        String holidayCountryCode = getIntent().getStringExtra(EXTRA_HOLIDAY_COUNTY_CODE);
        String holidayDate = getIntent().getStringExtra(EXTRA_HOLIDAY_DATE);
        int holidayLaunchYear = getIntent().getExtras().getInt(EXTRA_HOLIDAY_LAUNCH_YEAR);

        if (holidayName.isEmpty() && holidayCountryCode.isEmpty() && holidayDate.isEmpty()) {
            throw new RuntimeException("There is holiday haven`t name...");
        }
        nameTextView.setText(holidayName);
        dateTextView.setText(holidayDate);
        countryCodeTextView.setText(holidayCountryCode);
        launchYearTextView.setText(holidayLaunchYear == 0 ? "Unknown" : String.valueOf(holidayLaunchYear));

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        detailsViewModel = viewModelProvider.get(DetailsViewModel.class);
    }
}