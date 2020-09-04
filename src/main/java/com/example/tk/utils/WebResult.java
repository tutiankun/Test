package com.example.tk.utils;

import java.util.Objects;

public class WebResult<T> {

    /**
     * 成功状态码
     */
    public static final String SUCCESS_CODE = "200";

    /**
     * 服务器已经理解请求，但是拒绝执行它 ，比如参数错误等
     */
    public static final String CLINT_ERROR_CODE = "403";

    /**
     * 服务器执行出错
     */
    public static final String SERVER_ERROR_CODE = "500";

    private String code;
    private String message;
    private T result;

    public WebResult() {
    }

    public WebResult(T result) {
        this.code = SUCCESS_CODE;
        this.result = result;
    }

    public WebResult(String code, T result) {
        this.code = code;
        this.result = result;
    }

    public WebResult(String code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /**
     * 请求是请否成功，即code 是否是 200
     *
     * @return 请求是请否成功
     */
    public boolean success() {
        return Objects.equals(SUCCESS_CODE, code);
    }


    public String getCode() {
        return code;
    }

    public WebResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public WebResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getResult() {
        return result;
    }

    public WebResult<T> setResult(T result) {
        this.result = result;
        return this;
    }


    public String toJson() {
        return JsonUtil.toJson(this, JsonUtil.SFeature.EMPTY);
    }

    public String toJson(JsonUtil.SFeature features) {
        return JsonUtil.toJson(this, features);
    }

    public String toWebJson() {
        if (null == this.code) {
            this.code = "";
        }
        return JsonUtil.toJson(this, JsonUtil.SFeature.EMPTY);
    }

    public static <T> WebResult<T> success(T result) {
        return new WebResult<>(SUCCESS_CODE, result);
    }

    public static <T> WebResult<T> fail(String code, String message) {
        WebResult<T> webResult = new WebResult<>();
        webResult.setCode(code);
        webResult.setMessage(message);
        return webResult;
    }

}
