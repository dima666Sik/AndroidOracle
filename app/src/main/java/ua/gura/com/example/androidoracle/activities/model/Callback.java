package ua.gura.com.example.androidoracle.activities.model;

public interface Callback<T> {
    void onError(Throwable error);

    void onResults(T data);
}

