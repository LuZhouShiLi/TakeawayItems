package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


// 全局异常处理
// 注解搜索所有的Controller
// 返回json对象格式的数据
@ControllerAdvice(annotations = {RestController.class,Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    // 需要将处理的结果 封装成json格式的数据对象

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        // 捕获Username 重复的错误
        if(ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
            String msg = s[2] + "已存在";
            return R.error(msg);
        }
        
        return R.error("未知错误");
    }

}


///**
// * 全局异常处理
// */
//@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
//@Slf4j
//public class GlobalExceptionHandler {
//
//    /**
//     * 异常处理方法
//     * @return
//     */
//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
//        log.error(ex.getMessage());
//        if(ex.getMessage().contains("Duplicate entry")){
//            String[] split = ex.getMessage().split(" ");
//            String msg = split[2] + "已存在";
//            return R.error(msg);
//        }
//
//        return R.error("未知错误");
//    }
//}
