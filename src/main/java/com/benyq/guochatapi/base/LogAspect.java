package com.benyq.guochatapi.base;

import com.alibaba.fastjson.JSONObject;
import com.benyq.guochatapi.base.annotation.ApiMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
@Aspect
public class LogAspect {
    /**
     * ..表示包及子包 该方法代表controller层的所有方法
     */
    @Pointcut("execution(public * com.benyq.guochatapi.controller..*.*(..))")
    public void controllerMethod() {
    }

    /**
     * 方法执行前
     *
     * @param joinPoint
     * @throws Exception
     */
    @Before("controllerMethod()")
    public void LogRequestInfo(JoinPoint joinPoint) throws Exception {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuilder requestLog = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        requestLog.append("接口信息: ").append(((MethodSignature) signature).getMethod().getAnnotation(ApiMethod.class).value()).append("\n")
                .append("请求信息: ").append("URL = {").append(request.getRequestURI()).append("},\n")
                .append("请求方式 = {").append(request.getMethod()).append("},\n")
                .append("请求IP = {").append(request.getRemoteAddr()).append("},\n")
                .append("类方法 = {").append(signature.getDeclaringTypeName()).append(".")
                .append(signature.getName()).append("},\n");
        //处理header
        requestLog.append("header = {").append("\t");
        requestLog.append("token : ").append(request.getHeader("token"));
        requestLog.append("}").append("\n");
        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestLog.append("请求参数 = {} ");
        } else {
            requestLog.append("请求参数 = [");
            for (int i = 0; i < paramLength - 1; i++) {
                if (paramValues[i] instanceof HttpServletRequest || paramValues[i] instanceof MultipartHttpServletRequest) {
                    continue;
                }
                requestLog.append(paramNames[i]).append("=").append(JSONObject.toJSONString(paramValues[i])).append(",");
            }
            if (paramValues[paramLength - 1] instanceof HttpServletRequest || paramValues[paramLength - 1] instanceof MultipartHttpServletRequest) {
                requestLog.append("]");
            }else {
                requestLog.append(paramNames[paramLength - 1]).append("=").append(JSONObject.toJSONString(paramValues[paramLength - 1])).append("]");
            }
        }

        log.info(requestLog.toString());
    }


    /**
     * 方法执行后
     *
     * @param result
     * @throws Exception
     */
    @AfterReturning(returning = "result", pointcut = "controllerMethod()")
    public void logResultVOInfo(Object result) throws Exception {
        log.info("请求结果：" + JSONObject.toJSONString(result));
    }

}
