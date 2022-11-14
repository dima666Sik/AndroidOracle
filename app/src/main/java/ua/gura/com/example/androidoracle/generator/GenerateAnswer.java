package ua.gura.com.example.androidoracle.generator;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


import ua.gura.com.example.androidoracle.model.User;

public class GenerateAnswer {

    //info about user
    private User user;

    private String currentDate;

    private String question;
    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public static GenerateAnswer newInstance(@Nullable String question, @Nullable User user) {
        return new GenerateAnswer(question, user);
    }

    public GenerateAnswer(String question, User user) {
        if (question != null) {
            this.question = question.trim();
            this.user = user;
            this.currentDate = generateCurrentDate();
            if (!this.question.isEmpty()) {
                this.answer = getGenerateAnswer();
            }
        }
    }

    private String getGenerateAnswer() {
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
            strCurrentDate = URLEncoder.encode(this.currentDate, "UTF_8");
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
        return ARRAY_ANSWERS[i];
    }

    private String generateCurrentDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

}