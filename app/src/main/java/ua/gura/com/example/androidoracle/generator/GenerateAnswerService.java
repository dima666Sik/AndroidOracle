package ua.gura.com.example.androidoracle.generator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.gura.com.example.androidoracle.model.User;

public class GenerateAnswerService extends Service {
    //info about user
    private User user;
    private String question;
    private String answer;
    public static final String TAG = GenerateAnswerService.class.getSimpleName();
    private TestBinder binder = new TestBinder();

    private final static String ARRAY_ANSWERS[] = {
            "Yes",
            "No",
            "Of course",
            "Maybe",
            "Are you sure?",
            "Great",
            "Wonderful",
            "Right?",
            "Maybe we should not?",
            "Please, repeat",
            "No more words, silence",
            "Are you sleeping now?",
            "Don't know",
            "Who cares"

    };

    public String generateAnswerBndg(User user, String question) {
        this.user = user;
        this.question = question;
        getGenerateAnswer();
        return answer;
    }

    private void getGenerateAnswer() {
//        new Thread(()-> {
            String strQuestion = null;
            String strUserName = null;
            String strUserLastName = null;
            String strUserDateOfBirth = null;
            String strUserGender = null;
            String strCurrentDate = null;
            try {
                strQuestion = URLEncoder.encode(this.question, "UTF_8");
                strUserName = URLEncoder.encode(this.user.getFirstName(), "UTF_8");
                strUserLastName = URLEncoder.encode(this.user.getLastName(), "UTF_8");
                strUserDateOfBirth = URLEncoder.encode(this.user.getDateOfBirth(), "UTF_8");
                strUserGender = URLEncoder.encode(this.user.getGender(), "UTF_8");
                strCurrentDate = URLEncoder.encode(generateCurrentDate(), "UTF_8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String str = strQuestion + ":" + strUserName + ":" + strUserLastName + ":" + strUserDateOfBirth + ":" + strUserGender + ":" + strCurrentDate;

            int i = 4690;
            char c1 = Character.MIN_VALUE;
            while (c1 < str.length()) {
                i = (str.charAt(c1) | (~str.charAt(c1)) << 8) ^ i;
                c1++;
            }
            i &= 0xFFFF;
            c1 = 'a';
            char c2 = 'c', c3 = 'w';
            byte b = 0;
            while (b < str.length()) {
                if (i <= 13) break;
                i = ((c1 * i + c2) % c3) % 26;
                b++;
            }
            Log.d(TAG, "Generate answer!" + ARRAY_ANSWERS[i]);
            answer = ARRAY_ANSWERS[i];
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
    }

    private String generateCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class TestBinder extends Binder {
        public GenerateAnswerService generateAnswerService() {
            return GenerateAnswerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Service start...");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Service end...");
    }
}