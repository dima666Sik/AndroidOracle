package ua.gura.com.example.androidoracle.fragments;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

import ua.gura.com.example.androidoracle.R;
import ua.gura.com.example.androidoracle.model.User;


public class SettingFragment extends BaseFragment{
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
        outState.putString(DATE_USER, dateOfBirth.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public static SettingFragment newInstance(
            @Nullable User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_setting,
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstNameEditText = view
                .findViewById(R.id.firstNameEditText);
        lastNameEditText = view
                .findViewById(R.id.lastNameEditText);

        view.findViewById(R.id.chooseDate)
                .setOnClickListener(
                        this::onDateSet
                );

        dateOfBirth = view.findViewById(R.id.dateOfBirth);
        radioGroup = view.findViewById(R.id.radioGroup);

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
        answerRadioButtonMale = view.findViewById(R.id.radioMale);
        answerRadioButtonFemale = view.findViewById(R.id.radioFemale);

        if (savedInstanceState != null) {
            dateOfBirth.setText(savedInstanceState.getString(DATE_USER));
        } else {
            User user = getUserArg();
            if (user != null) {
                firstNameEditText.setText(user
                        .getFirstName());
                lastNameEditText.setText(user.getLastName());
                dateOfBirth.setText(user.getDateOfBirth());
            }
        }
        setupButtons(view);
    }

    private void setupButtons(View view) {
        view.findViewById(R.id.cancelButton)
                .setOnClickListener(v -> {
                    getAppContract().cancel();
                });
        view.findViewById(R.id.doneButton)
                .setOnClickListener(v -> {
                    User user = new User(
                            firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            dateOfBirth.getText().toString(),
                            checkedAnswers[position]==0?"male":checkedAnswers[position]==1?"female":"trap?"
                    );
                    if (!user.isValid()) {
                        Toast.makeText(
                                getContext(),
                                R.string.empty_fields_error,
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }
                    getAppContract().publish(user);
                    getAppContract().cancel();
                });
    }

    private User getUserArg() {
        return getArguments().getParcelable(ARG_USER);
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        dateOfBirth.setText(
                DateUtils.formatDateTime(
                        getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void onDateSet(View v) {
        new DatePickerDialog(getActivity(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}
