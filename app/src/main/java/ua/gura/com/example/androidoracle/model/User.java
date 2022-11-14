package ua.gura.com.example.androidoracle.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String gender;

    public User(String firstName, String lastName, String dateOfBirth, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public boolean isValid(){
        return !isEmpty(firstName)
                && !isEmpty(lastName)
                && !isEmpty(dateOfBirth)
                && !isEmpty(gender);
    }
    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
    }
}
