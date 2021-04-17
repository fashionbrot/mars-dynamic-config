///*
//package com.github.fashionbrot.aop;
//
//import com.alibaba.fastjson.JSON;
//import com.github.fashionbrot.validated.exception.ValidatedException;
//import com.jinxing.wms.common.annotation.PersistentLog;
//import com.jinxing.wms.common.exception.HzzyException;
//import com.jinxing.wms.common.model.LoginModel;
//import com.jinxing.wms.common.util.IpUtil;
//import com.jinxing.wms.core.entity.sys.LogEntity;
//import com.jinxing.wms.core.service.LogService;
//import com.jinxing.wms.core.service.UserLoginService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.*;
//
//@Aspect
//@Component
//@Slf4j
//public class BackStageAspect implements DisposableBean {
//
//    @Autowired
//    private HttpServletRequest request;
//
//    @Pointcut("execution(public * com.jinxing.wms.console.controller..*(..))")
//    public void aspect() {
//    }
//
//    @Autowired
//    private LogService logService;
//    @Autowired
//    private UserLoginService userLoginService;
//
//    private static final int pollSize= Runtime.getRuntime().availableProcessors();
//
//    private ExecutorService executorService = new ThreadPoolExecutor(pollSize, pollSize, 0L, TimeUnit.MILLISECONDS,
//            new LinkedBlockingQueue<Runnable>(50),
//            new RejectedHandler());
//
//
//    @Around("aspect()")
//    public Object around(ProceedingJoinPoint pointcut) throws Throwable {
//        Signature signature = pointcut.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method targetMethod = methodSignature.getMethod();
//
//        Object[] params = pointcut.getArgs();
//        Object result = pointcut.proceed();
//
//        aroundMethod(targetMethod,params, 1, null);
//        return result;
//    }
//
//
//    private  String throwableToString(Throwable e){
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw, true);
//        e.printStackTrace(pw);
//        pw.flush();
//        sw.flush();
//        return sw.toString();
//    }
//
//
//    @AfterThrowing(pointcut = "aspect()", throwing = "ex")
//    public void doAfterThrowing(JoinPoint pointcut, Throwable ex) {
//        Signature signature = pointcut.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method targetMethod = methodSignature.getMethod();
//        Object[] params = pointcut.getArgs();
//        aroundMethod(targetMethod,params, 2, ex);
//    }
//
//
//    public void aroundMethod(Method targetMethod,Object[] params, int logType, Throwable exception) {
//
//        int interfaceType = 1;
//        String requestUrl = request.getRequestURI();
//        String requestMethod = request.getMethod();
//        if (requestMethod.equals("HEAD")){
//            return;
//        }
//        String requestIp = IpUtil.getIpAddress(request);
//        Long userId = null;
//        Long orgId = 0L;
//        String requestDesc = "";
//
//        ApiOperation operation = targetMethod.getDeclaredAnnotation(ApiOperation.class);
//        if (operation != null) {
//            Api api = targetMethod.getDeclaringClass().getDeclaredAnnotation(Api.class);
//            if (api != null && api.tags() != null && api.tags().length > 0) {
//                requestDesc = api.tags()[0] + "-" + operation.value();
//            } else {
//                requestDesc = operation.value();
//            }
//        }
//        if (targetMethod != null) {
//            LoginModel login = userLoginService.getSafeLogin();
//            if (login!=null){
//                userId = login.getUserId();
//                orgId  = login.getOrgId();
//            }
//        }
//        String requestParams ="";
//
//        String exceptionDesc =exception!=null?exception.getMessage():"";
//        if (exception!=null && StringUtils.isEmpty(exception.getMessage())){
//            exceptionDesc = throwableToString(exception);
//        }
//        if("POST".equals(requestMethod)) {
//            boolean isRequestBody = false;
//            Parameter[] parameters = targetMethod.getParameters();
//            if (parameters!=null && parameters.length>0){
//                for(Parameter parameter : parameters){
//                    if (parameter.getDeclaredAnnotation(RequestBody.class)!=null){
//                        isRequestBody=true;
//                        break;
//                    }
//                }
//            }
//            if (isRequestBody) {
//                requestParams = JSON.toJSONString(getParams(params));
//            }else{
//                requestParams = JSON.toJSONString(request.getParameterMap());
//            }
//        }else{
//            requestParams = JSON.toJSONString(request.getParameterMap());
//        }
//
//        if (exception==null){
//            PersistentLog persistentLog = targetMethod.getDeclaredAnnotation(PersistentLog.class);
//            if (  persistentLog == null || !persistentLog.persistent() ) {
//                log.info("syslog-start requestDesc:{} requestUrl:{} requestMethod:{} requestIp:{} requestParam:{} interfaceType:{} userId:{}  logType:{}  ",
//                        requestDesc, requestUrl, requestMethod, requestIp, requestParams, 1, userId, logType);
//                return;
//            }
//        }else{
//            if (exception!=null && (exception instanceof HzzyException || exception instanceof ValidatedException)){
//                log.info("syslog-start requestDesc:{} requestUrl:{} requestMethod:{} requestIp:{} requestParam:{} interfaceType:{} userId:{}  logType:{} exception:{} ",
//                        requestDesc, requestUrl, requestMethod, requestIp, requestParams, 1, userId, logType,exception.getMessage());
//                return;
//            }
//        }
//
//        if (requestParams!=null && requestParams.length()>1000) {
//            requestParams = requestParams.substring(0, 1000);
//        }
//        if (exceptionDesc!=null && exceptionDesc.length()>1000){
//            exceptionDesc = exceptionDesc.replaceAll("(\\r\\n|\\n|\\n\\r)"," <br/>").substring(0, 1000);
//        }
//        LogEntity build = LogEntity.builder()
//                .createDate(new Date())
//                .requestIp(requestIp)
//                .requestMethod(requestMethod)
//                .requestUrl(requestUrl)
//                .requestDesc(requestDesc)
//                .interfaceType(interfaceType)
//                .logType(logType)
//                .targetMethod(targetMethod.getName())
//                .requestParam(requestParams)
//                .createId(userId)
//                .orgId(orgId)
//                .build();
//        if (logType == 2) {
//            build.setException(exceptionDesc);
//        }
//
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                logService.insert(build);
//            }
//        });
//
//    }
//
//
//    @Override
//    public void destroy()  {
//
//    }
//
//    public List<Object> getParams(Object[] objects) {
//        List<Object> list=new ArrayList<>(objects.length);
//        if (objects != null && objects.length > 0) {
//            for (int i = 0; i < objects.length; i++) {
//                Object obj = objects[i];
//                if (obj instanceof MultipartFile) {
//                    continue;
//                }
//                if (obj instanceof HttpServletRequest){
//                    continue;
//                }
//                if (obj instanceof HttpServletResponse){
//                    continue;
//                }
//                list.add(obj);
//            }
//        }
//        return list;
//    }
//
//
//    class RejectedHandler implements RejectedExecutionHandler {
//
//        @Override
//        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//            if (!executor.isShutdown()) {
//                //再尝试入队
//                executor.execute(r);
//            }
//        }
//    }
//
//
//}
//*/
