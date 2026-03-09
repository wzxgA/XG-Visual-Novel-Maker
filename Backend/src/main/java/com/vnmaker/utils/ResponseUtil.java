package com.vnmaker.utils;

import lombok.Data;

@Data
public class ResponseUtil<T> {
    private int code;
    private String message;
    private T data;

    public ResponseUtil(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseUtil<T> success(T data) {
        return new ResponseUtil<>(200, "success", data);
    }

    public static <T> ResponseUtil<T> error(String message) {
        return new ResponseUtil<>(500, message, null);
    }
}