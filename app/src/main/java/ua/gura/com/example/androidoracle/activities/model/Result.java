package ua.gura.com.example.androidoracle.activities.model;

public class Result<T> {
    public enum Status {
        EMPTY,
        LOADING,
        SUCCESS,
        ERROR
    }

    private Status status;
    private T data;
    private Throwable error;

    private Result(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Result<T> error(Throwable error) {
        return new Result<>(Status.ERROR, null, error);
    }

    public static <T> Result<T> empty() {
        return new Result<>(Status.EMPTY, null, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.SUCCESS, data, null);
    }

    public static <T> Result<T> loading() {
        return new Result<>(Status.LOADING, null, null);
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }
}
