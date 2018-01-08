package com.gdtech.core.base.controller;

import com.gdtech.core.base.constant.StatusCode;
import com.gdtech.core.base.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理
 */
@RestController
@ControllerAdvice
public class GdExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GdExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        ResultDto errResult = new ResultDto(Boolean.FALSE, e.getMessage(), e);
        errResult.setCode(StatusCode.ERROR);
        return errResult;
    }
}

