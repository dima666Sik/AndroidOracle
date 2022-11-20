package ua.gura.com.example.androidoracle.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.model.User;


public class SettingActivity extends BaseActivity {
    private User user = null;
    private static final String ARG_USER = "USER";
    private static final String DATE_USER = "DATE_USER";

    private EditText firstNameEditText;
    private EditText lastNameEditText;

    private TextView dateOfBirth;
    private RadioGroup radioGroup;
    private RadioButton answerRadioButtonMale;
    private RadioButton answerRadioButtonFemale;
    private final int[] checkedAnswers = new int[1];
    private final int position = 0;
    private final Calendar dateAndTime = Calendar.getInstance();
    // установка обработчика выбора даты
    private final DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE_USER, dateOfBirth.getText().toString());
        outState.putParcelable(ARG_USER, user);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // устанавливается разметка
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);

        findViewById(R.id.chooseDate)
                .setOnClickListener(
                        this::onDateSet
                );

        dateOfBirth = findViewById(R.id.dateOfBirth);
        radioGroup = findViewById(R.id.radioGroup);

        //запоминаем какой элемент выбран в определенной позиции
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radioMale:
                        checkedAnswers[position] = 0;
                        break;
                    case R.id.radioFemale:
                        checkedAnswers[position] = 1;
                        break;

                    default:
                        break;
                }
            }
        });
        answerRadioButtonMale = findViewById(R.id.radioMale);
        answerRadioButtonFemale = findViewById(R.id.radioFemale);

        if (savedInstanceState != null) {
            dateOfBirth.setText(savedInstanceState.getString(DATE_USER));
            user = savedInstanceState.getParcelable(ARG_USER);
        } else {
            if (user != null) {
                firstNameEditText.setText(user
                        .getFirstName());
                lastNameEditText.setText(user.getLastName());
                dateOfBirth.setText(user.getDateOfBirth());
            }
        }
        setupButtons();
    }

    private void setupButtons() {
        findViewById(R.id.cancelButton)
                .setOnClickListener(v -> {
                    cancel();
                });
        findViewById(R.id.doneButton)
                .setOnClickListener(v -> {
                    user = new User(
                            firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            dateOfBirth.getText().toString(),
                            checkedAnswers[position] == 0 ? "male" : checkedAnswers[position] == 1 ? "female" : "trap?"
                    );
                    if (!user.isValid()) {
                        Toast.makeText(
                                this,
                                R.string.empty_fields_error,
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }
                    Intent intent = new Intent(this, MenuActivity.class);
                    System.out.println("+++"+user);
                    intent.putExtra(ARG_USER, user);
                    startActivity(intent);
                    cancel();
                });
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        dateOfBirth.setText(
                DateUtils.formatDateTime(
                        this,
                        dateAndTime.getTimeInMillis(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void onDateSet(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}
