package ua.gura.com.example.androidoracle.contract;

public interface ResponseListener<T> {
    void onResults(T results);
}