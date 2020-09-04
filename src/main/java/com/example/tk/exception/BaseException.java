package com.example.tk.exception;

/**
 * @Description:自定义异常
 * @author: rickycoder
 * @date: 2015年1月7日 下午6:15:19 久兴信息技术(上海)有限公司
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -3512805787120392288L;

    /**
     * 错误代码 4xx:业务缺少必要参数 5xx:业务操作异常 -1:系统错误
     */
    private String code;

    private String message;

    public BaseException() {
        super();
        this.code = "-1";
        this.message = "系统错误";
    }

    public BaseException(Throwable t) {
        super(t);
        this.code = "-1";
        this.message = "系统错误";
    }

    public BaseException(String message) {
        super(message);
        this.code = "-1";
        this.message = message;
    }

    /**
     * 废弃掉，防止吃掉exception
     * 
     * @param code
     * @param message
     */
    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException(String code, String message, Throwable t) {
        super(t);
        this.code = code;
        this.message = message;
    }

    public BaseException(String code, String message, Exception t) {
        super(t);
        this.code = code;
        this.message = message;
    }

    /**
     * 错误代码 4xx:业务缺少必要参数 5xx:业务操作异常 -1:系统错误
     */
    public String getCode() {
        return code;
    }

    /**
     * 错误代码 4xx:业务缺少必要参数 5xx:业务操作异常 -1:系统错误
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "code:" + this.code + " message:" + this.message;
    }
}
