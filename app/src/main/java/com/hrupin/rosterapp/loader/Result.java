package com.hrupin.rosterapp.loader;

/**
 * Created by Igor Khrupin www.hrupin.com on 5/5/16.
 */
public class Result<T> {
    private DataStatus status;
    private T data;

    public Result(DataStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public DataStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
