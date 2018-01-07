package com.gdtech.core.base.controller;

import com.gdtech.core.base.constant.StatusCode;
import com.gdtech.core.base.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;

/**
 * @author zhucy
 */
@ControllerAdvice
public class GdResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("GdResponseBodyAdvice==>supports:" + converterType);
        log.debug("GdResponseBodyAdvice==>supports:" + returnType.getClass());
        log.debug("GdResponseBodyAdvice==>supports:"
                + MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType));
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return body;
        }

        if (body instanceof ResultDto || body instanceof String) {
            return body;
        } else if (body instanceof File) {
            return body;
        } else {
            log.debug("GdResponseBodyAdvice==>beforeBodyWrite:" + returnType + "," + body);
            ResultDto result = new ResultDto(Boolean.TRUE);
            result.setCode(StatusCode.OK);
            result.setData(body);
            body = (Object) result;
            return body;
        }
    }
}
