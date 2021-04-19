package com.dospyer.advice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo<List> handleBindException(HttpServletRequest req, BindException e) {
        log.warn("error handler invoked ...");
        List errorsList = getBindException(e.getBindingResult());
        return genErrorInfo(req.getRequestURL().toString(), "Bad Request",errorsList);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo<List> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.warn("error handler invoked ...");
        List errorsList = getBindException(e.getBindingResult());
        return genErrorInfo(req.getRequestURL().toString(), "Bad Request",errorsList);
    }

    @ExceptionHandler(value = Exception.class)
    public ErrorInfo<String> defaultErrorHandler(HttpServletRequest req, Exception e) {
        return genErrorInfo(req.getRequestURL().toString(), e.getMessage());
    }

    private List getBindException(BindingResult result) {
        List<FieldError> fieldErrorList = result.getFieldErrors();

        List errorsList = new ArrayList();
        for (FieldError fieldError : fieldErrorList) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String errorMessage = messageSource.getMessage(fieldError, currentLocale);
            BindErrorInfo info = new BindErrorInfo();
            info.setFieldName(fieldError.getField());
            info.setErrorMessage(errorMessage);
            errorsList.add(info);
        }
        return errorsList;
    }

    private ErrorInfo<String> genErrorInfo(String url, String message) {
        return genErrorInfo(url,message,"自定义的全局异常处理");
    }

    private <T> ErrorInfo<T> genErrorInfo(String url, String message,T data) {
        ErrorInfo<T> r = new ErrorInfo<>();
        r.setMessage(message);
        r.setCode(ErrorInfo.ERROR);
        r.setData(data);
        r.setUrl(url);
        return r;
    }

    @Data
    private static class BindErrorInfo<T> {
        private String fieldName;
        private String errorMessage;
    }

    @Data
    private static class ErrorInfo<T> {
        public static final Integer OK = 0;
        public static final Integer ERROR = 100;
        private Integer code;
        private String message;
        private String url;
        private T data;
    }
}