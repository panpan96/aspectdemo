package com.example.aspectdemo.aop;

import com.example.aspectdemo.annotation.SysLog;
import com.example.aspectdemo.entity.SysLogBo;
import com.example.aspectdemo.service.SysLogService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author zhoutf
 * @Date 2019/3/20 9:28
 * @Description
 */
@Aspect
@Component
@Slf4j
public class SysLogAop {

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.example.aspectdemo.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public  Object around(ProceedingJoinPoint point) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("url:"+request.getRequestURI());
        log.info("ip:"+request.getRemoteAddr());
       long beginTime=System.currentTimeMillis();
        Object result = point.proceed();
       long time=System.currentTimeMillis()-beginTime;
        saveLog(point, time);
       return  result;
    }
    private  void saveLog(ProceedingJoinPoint point,Long time){
        MethodSignature signature =(MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLogBo sysLogBo = new SysLogBo();
        sysLogBo.setExeuTime(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sysLogBo.setCreateDate(simpleDateFormat.format(new Date()));
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {

            sysLogBo.setRemark(syslog.value());
        }

        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogBo.setClassName(className);
        sysLogBo.setMethodName(methodName);
        Object[] args = point.getArgs();
        ArrayList<String> list = new ArrayList<>();
        for (Object o:args
             ) {
            list.add(new Gson().toJson(o));
        }
        sysLogBo.setParams(list.toString());
        sysLogService.save(sysLogBo);

    }

}
