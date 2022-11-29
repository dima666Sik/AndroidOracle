package ua.gura.com.example.androidoracle.activities.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.activities.App;
import ua.gura.com.example.androidoracle.activities.details.DetailsActivity;

public class MainActivity extends BaseActivity {
    private Button searchButton;
    private RecyclerView holidaysViewList;
    private EditText namedHolidayEditText;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private TextView errorTextView;

    private MainViewModel mainViewModel;
    private HolidaysAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливается разметка

        searchButton = findViewById(R.id.searchButton);
        holidaysViewList = findViewById(R.id.holidaysList);
        namedHolidayEditText = findViewById(R.id.userNameEditText);
        progressBar = findViewById(R.id.progress);
        emptyTextView = findViewById(R.id.emptyTextView);
        errorTextView = findViewById(R.id.errorTextView);

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        mainViewModel = viewModelProvider.get(MainViewModel.class);

        mainViewModel.getViewState().observe(this, state -> {
            searchButton.setEnabled(state.isEnableSearchButton());
            holidaysViewList.setVisibility(toVisibility(state.isShowList()));
            progressBar.setVisibility(toVisibility(state.isShowProgress()));
            emptyTextView.setVisibility(toVisibility(state.isShowEmptyHint()));
            errorTextView.setVisibility(toVisibility(state.isShowError()));

            adapter.setHolidays(state.getHolidayList());
        });

        searchButton.setOnClickListener(v -> {
            String userName = namedHolidayEditText.getText().toString();
            mainViewModel.getHolidays(userName);
        });

        initHolidaysList();
    }

    private void initHolidaysList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        holidaysViewList.setLayoutManager(layoutManager);
        adapter = new HolidaysAdapter(holiday -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_HOLIDAY_NAME, holiday.getName());
            startActivity(intent);
        });
        holidaysViewList.setAdapter(adapter);
    }

    static int toVisibility(boolean show) {
        return show ? View.VISIBLE : View.GONE;
    }
}