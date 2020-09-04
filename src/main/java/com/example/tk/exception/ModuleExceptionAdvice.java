package com.example.tk.exception;

import com.example.tk.utils.WebResult;
import com.example.tk.utils.XxHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
@ControllerAdvice(basePackages = "com.example.tk")
public final class ModuleExceptionAdvice extends BaseExceptionAdvice {

    /**
     * 业务统一异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public WebResult<String> handleBaseBusinessApiException(ServletWebRequest request, BaseException ex) {
        if (log.isErrorEnabled()) {
            log.error("系统错误，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }
        request.getResponse().setHeader(XxHeaderConstant.STATUS_CODE, ex.getCode());
        return WebResult.fail(ex.getCode(), ex.getMessage());
    }


}
