//package com.lin.security.advice;
//
//import com.lin.security.vo.ResultVo;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@ControllerAdvice
//public class ExceptionGlobalAdvice {
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResultVo globalExceptionHandle(Exception exception){
//        String message = exception.getMessage();
//        return ResultVo.failed(message);
//    }
//
//}
