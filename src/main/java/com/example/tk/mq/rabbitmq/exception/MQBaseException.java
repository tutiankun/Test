package com.example.tk.mq.rabbitmq.exception;

public class MQBaseException extends RuntimeException {

    private String errorCode;

    public MQBaseException(String errorCode, String defaultMessage) {
        super(defaultMessage);
        this.setErrorCode(errorCode);
    }

    public MQBaseException(String errorCode, String defaultMessage, Throwable cause) {
        super(defaultMessage, cause);
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
