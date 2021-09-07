package com.tabwu.changgou.exception;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
