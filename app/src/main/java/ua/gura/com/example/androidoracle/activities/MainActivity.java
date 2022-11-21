package ua.gura.com.example.androidoracle.activities;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;

import ua.gura.com.example.androidoracle.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливается разметка
        if (savedInstanceState == null) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        }
        cancel();
    }

}