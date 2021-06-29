package com.zcy.blog.config.handler;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 */
//@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 处理Exception的异常类
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        logger.error("Exception异常处理："+e.getMessage());
        return "Exception异常";
    }

}
