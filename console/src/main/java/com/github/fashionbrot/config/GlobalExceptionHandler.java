package com.github.fashionbrot.config;


import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.exception.CurdException;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.vo.RespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MarsException.class)
    @ResponseStatus(HttpStatus.OK)
    public RespVo marsException(MarsException e) {
        return RespVo.fail(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(CurdException.class)
    @ResponseStatus(HttpStatus.OK)
    public RespVo curdException(CurdException e) {
        return RespVo.fail(e.getMsg(), e.getCode());
    }

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public RespVo ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        return RespVo.fail(violations.get(0).getMsg(),RespVo.FAILED);
    }

    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, HandlerMethod handlerMethod, Exception ex) {
        log.error("exception error:",ex);
        if(isAjax(request)){
            return RespVo.fail(RespEnum.FAIL.getMsg());
        }else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/error/500");
            modelAndView.addObject("errorMsg",ex.getMessage());
            return modelAndView;
        }
    }


    public boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }




}
