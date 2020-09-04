package com.example.tk.exception;

import com.example.tk.utils.IpUtils;
import com.example.tk.utils.WebResult;
import com.example.tk.utils.XxHeaderConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * 全局异常增强处理
 * <p>
 * 该类之后需要在类名上加上 以下注解，basePackages 是自己需要的增强的controller包，下面以carmodel举例
 * //@ControllerAdvice(basePackages = "com.ttpai.microservice.carmodel.server.controllers")
 */
@ResponseBody
public abstract class BaseExceptionAdvice {


    private static Logger logger = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    private static final String REQ_ARRT_BEST_MATCHING_PATTERN = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";

    // region 参数校验错误

    /**
     * 对参数进行校验
     * <p>
     * Resin 4.0.55 默认会加载 Hibernate 4.3.0.Final（版本冲突时貌似以自己默认的版本能为主），校验异常抛出的是
     * org.hibernate.validator.method.MethodConstraintViolationException
     * <p>
     * Hibernate 5 之后 校验异常抛出的是 javax.validation.ConstraintViolationException
     * <p>
     * MethodConstraintViolationException 和 ConstraintViolationException 都继承自 javax.validation.ValidationException
     */
    @SuppressWarnings(value = "all")
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public WebResult<String> handleConstraintViolationException(ServletWebRequest request, ValidationException ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.FORBIDDEN.value());

        if (logger.isErrorEnabled()) {
            logger.error("缺少必要参数，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }

        WebResult<String> webResult = new WebResult<>(HttpStatus.FORBIDDEN.toString(), "缺少必要参数", null);
        try {
            Method getConstraintViolations = ex.getClass().getMethod("getConstraintViolations");
            Set<ConstraintViolation<?>> constraintViolations = (Set) getConstraintViolations.invoke(ex);
            if (null != constraintViolations && !constraintViolations.isEmpty()) {
                String errorMessage = constraintViolations.iterator().next().getMessage();
                webResult = WebResult.fail(HttpStatus.FORBIDDEN.toString(), errorMessage);
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            logger.error("参数校验增强出错", e);
        }

        return webResult;
    }

    /**
     * Exception to be thrown when validation on an argument annotated with {@code @Valid} fails.
     *
     * @see javax.validation.Valid
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResult<String> assertException(ServletWebRequest request, MethodArgumentNotValidException ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.BAD_REQUEST.value());

        if (logger.isErrorEnabled()) {
            logger.error("缺少必填参数 或 参数错误，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }

        BindingResult bindingResult = ex.getBindingResult();
        if (null != bindingResult) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            if (!allErrors.isEmpty() && null != allErrors.get(0)) {
                return new WebResult<>(HttpStatus.BAD_REQUEST.toString(), allErrors.get(0).getDefaultMessage(), null);
            }
        }

        return new WebResult<>(HttpStatus.BAD_REQUEST.toString(), "缺少必填参数 或 参数错误", null);
    }

    /**
     * @see org.springframework.web.bind.MissingServletRequestParameterException children
     * @see org.springframework.web.bind.UnsatisfiedServletRequestParameterException children
     * @see org.springframework.web.bind.MissingPathVariableException children
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServletRequestBindingException.class)
    public WebResult<String> handleServletRequestBindingException(ServletWebRequest request,
                                                                  ServletRequestBindingException ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.FORBIDDEN.value());

        if (logger.isErrorEnabled()) {
            logger.error("缺少必填参数，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }

        return new WebResult<>(HttpStatus.FORBIDDEN.toString(), "缺少必填参数[" + ex.getMessage() + "]", null);
    }

    /**
     * 参数格式错误校验，如要求 int 参数 ，传的是 String 等
     *
     * @see org.springframework.beans.TypeMismatchException parent
     * @see org.springframework.beans.PropertyAccessException parent
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public WebResult<String> handleMethodArgumentTypeMismatchException(ServletWebRequest request,
                                                                       MethodArgumentTypeMismatchException ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.FORBIDDEN.value());

        if (logger.isErrorEnabled()) {
            logger.error("参数格式错误，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }

        return new WebResult<>(HttpStatus.FORBIDDEN.toString(), "参数格式错误", null);
    }

    // endregion

    /**
     * 断言校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public WebResult<String> assertException(ServletWebRequest request, IllegalArgumentException ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.BAD_REQUEST.value());

        if (logger.isErrorEnabled()) {
            logger.error("断言校验不通过，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }

        return new WebResult<>(HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), null);
    }

    /**
     * 全局异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public WebResult<String> handleException(ServletWebRequest request, Exception ex) {
        this.buildXxHeader(request.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        if (logger.isErrorEnabled()) {
            logger.error("微服务内部错误，参数 request 信息：{}", buildRequestInfo(request.getRequest()), ex);
        }
        return new WebResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value()+"", "微服务内部错误", null);
    }

    protected void buildXxHeader(HttpServletResponse response, Integer code) {
        if (null != response.getHeader(XxHeaderConstant.STATUS_CODE)) {
            return;
        }
        response.setHeader(XxHeaderConstant.STATUS_CODE, String.valueOf(null != code ? code : response.getStatus()));
    }

    protected StringBuilder buildRequestInfo(HttpServletRequest request) {
        StringBuilder reqBuilder = new StringBuilder(System.lineSeparator());
        if (null == request) {
            return reqBuilder;
        }

        reqBuilder.append("Method:[")
                .append(request.getMethod())
                .append("]").append(System.lineSeparator());

        reqBuilder.append("bestMatchingPattern:[")
                .append(request.getAttribute(REQ_ARRT_BEST_MATCHING_PATTERN))
                .append("]").append(System.lineSeparator());

        reqBuilder.append("RequestURL:[")
                .append(request.getRequestURL())
                .append("]").append(System.lineSeparator());

        reqBuilder.append("QueryString:[")
                .append(request.getQueryString())
                .append("]").append(System.lineSeparator());

        reqBuilder.append("RemoteAddr:[")
                .append(IpUtils.getIp(request))
                .append("]").append(System.lineSeparator());

        return reqBuilder;
    }
}
