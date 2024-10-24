package xxyy.unittest.generateunittestformock.aspect;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xxyy.unittest.generateunittestformock.annotation.MockTestMark;
import xxyy.unittest.generateunittestformock.config.GenerateUnitTestForMockConfig;
import xxyy.unittest.generateunittestformock.dto.MethodDescript;
import xxyy.unittest.generateunittestformock.dto.ParamDescript;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class Repositoryaspect {

    Logger logger = LoggerFactory.getLogger(Repositoryaspect.class);

    @Autowired
    private GenerateUnitTestForMockConfig generateUnitTestForMockConfig;

//    private static final String POINT_CUT_METHOD = AspectPointJoinConig.POINT_CUT_METHOD;

    private String doReturn = "doReturn(";
    private String doReturnM = ", ";
    private String when = ")).when(";
    private String whenEnd = ").";
    private String end = "(";
    private String endEnd = ");";

//    @Pointcut(value =  AspectPointJoinConig.POINT_CUT_METHOD)
//    public void dealAfterReturning() {
//
//    }

//    @AfterReturning(pointcut = "dealAfterReturning()", returning = "returnVlu")
//    public void afterReturn(JoinPoint joinPoint, Object returnVlu) {
//
//        this.dealParamAndReturn(joinPoint, returnVlu);
//
//    }

    @Pointcut(value = "@annotation(mockTestMark)")
    public void dealAfterReturningForAnnotation(MockTestMark mockTestMark) {

    }

//    @AfterReturning(pointcut = "@annotation(mockTestMark)", returning = "returnVlu")
//    public void afterReturnForAnnotion(JoinPoint joinPoint, Object returnVlu, MockTestMark mockTestMark) {
//
//        this.dealParamAndReturn(joinPoint, returnVlu);
//
//    }

    @AfterReturning(pointcut = "dealAfterReturningForAnnotation(mockTestMark)", returning = "returnVlu")
    public void afterReturnForAnnotion(JoinPoint joinPoint, Object returnVlu, MockTestMark mockTestMark) {

        if(!generateUnitTestForMockConfig.isPower_switch()){
            return;
        }


        this.dealParamAndReturn(joinPoint, returnVlu);

    }


    /**
     *  处理参数和返回信息
     * @param joinPoint
     * @param returnVlu
     */
    private void dealParamAndReturn(JoinPoint joinPoint, Object returnVlu) {
        String className = joinPoint.getTarget().getClass().getName();
        if(!className.equals("repositoryPath")){
            logger.info("切面生效：");
            logger.info(className);
        }

        // init
        MethodDescript methodDescript = new MethodDescript();


        // 获取服务名称信息
        this.getServiceName(joinPoint, methodDescript);

        // 方法和参数信息
        this.getMethodNameAndArgs(joinPoint, methodDescript);

        // 获取返回值类型并转换为 JSON
        this.getReturn(joinPoint, returnVlu, methodDescript);

        StringBuilder result = new StringBuilder();
        result.append(doReturn);
        result.append(methodDescript.getRetrun().getJson());
        result.append(doReturnM);
        result.append(methodDescript.getRetrun().getType());
        result.append(when);
        result.append(methodDescript.getServiceName());
        result.append(whenEnd);
        result.append(methodDescript.getMethodName());
        result.append(end);
        if(!CollectionUtils.isEmpty(methodDescript.getArgs())) {
            StringBuilder allArgsJson = new StringBuilder();
            for(ParamDescript paramDescript : methodDescript.getArgs()){
                allArgsJson.append(paramDescript.getJson());
                allArgsJson.append(",");
                allArgsJson.append(paramDescript.getType());
                allArgsJson.append(")");
                allArgsJson.append(",");
            }
            result.append(allArgsJson.substring(0,allArgsJson.length()-1));
        }
        result.append(endEnd);

//        logger.info(result.toString());
        System.out.println(result.toString());
//        stackTrace();

//        logger.info("AfterReturning:结束");
    }

    /**
     *  获取方法和参数信息
     * @param joinPoint
     * @param methodDescript
     */
    private void getMethodNameAndArgs(JoinPoint joinPoint, MethodDescript methodDescript) {
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        String methodName = methodSignature.getMethod().getName();
        methodDescript.setMethodName(methodName);

        // 获取参数类型和参数值
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();

        // 打印每个参数的类型和 JSON
        for (int i = 0; i < args.length; i++) {
            ParamDescript paramDescript = methodDescript.getRetrun();
            this.dealParamToJson(joinPoint, args[i], paramDescript);
            methodDescript.getArgs().add(paramDescript);
        }
    }


    /**
     *  获取service名称
     * @param joinPoint
     * @param methodDescript
     */
    private static void getServiceName(JoinPoint joinPoint, MethodDescript methodDescript) {
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        if(interfaces.length == 0) {
            String className = joinPoint.getTarget().getClass().getName();
            className = className.substring(className.lastIndexOf(".")+1);
            className = StringUtils.uncapitalize(className);
            methodDescript.setServiceName(className);
        } else if(interfaces.length == 1){
            String className = interfaces[0].getName();
            className = className.substring(className.lastIndexOf(".")+1);
            className = StringUtils.uncapitalize(className);
            methodDescript.setServiceName(className);
        }
    }

    /**
     * 打印调用栈信息
     */
    private static void stackTrace() {
        // 调用栈
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 打印调用栈信息
        for (StackTraceElement element : stackTrace) {
            System.out.println("类名: " + element.getClassName() +
                    ", 方法名: " + element.getMethodName() +
                    ", 文件名: " + element.getFileName() +
                    ", 行号: " + element.getLineNumber());
        }
    }

    /**
     * 获取返回值信息
     *
     * @param joinPoint
     * @param returnVlu
     * @return
     */
    private void getReturn(JoinPoint joinPoint, Object returnVlu, MethodDescript methodDescript) {

        ParamDescript paramDescript = methodDescript.getRetrun();

        this.dealParamToJson(joinPoint, returnVlu, paramDescript);
    }

    /**
     *  将参数处理为JSON
     * @param joinPoint
     * @param paramValue
     * @param paramDescript
     */
    private void dealParamToJson(JoinPoint joinPoint, Object paramValue, ParamDescript paramDescript) {
        String paramJsonRaw = JSON.toJSONString(paramValue); // 转换成 JSON
        String paramJson = escapeJson(paramJsonRaw); // 转义特殊字符

        String paramTypeName = null;

        String jsonParseMethod = "JSON.parseObject(";
        if (paramValue != null) {
            if (paramValue instanceof List<?>) {
                jsonParseMethod = "JSON.parseArray(";
                if (((List<?>) paramValue).iterator().hasNext()) {
                    Object tmp = ((List<?>) paramValue).iterator().next();
                    paramTypeName = getReturnTypeName(tmp);
                }
            } else if (paramValue instanceof Set<?>) {
                if (((Set<?>) paramValue).iterator().hasNext()) {
                    Object tmp = ((Set<?>) paramValue).iterator().next();
                    paramTypeName = "new TypeReference<Set<" + tmp.getClass().getName() + ">>(){}";
                }
            } else if (paramValue instanceof Map<?, ?>) {
                if (((Map<?, ?>) paramValue).entrySet().iterator().hasNext()) {
                    Map.Entry<?, ?> tmp = (Map.Entry<?, ?>) ((Map<?, ?>) paramValue).entrySet().iterator().next();
                    paramTypeName = "new TypeReference<Map<" + tmp.getKey().getClass().getName() + ", " + tmp.getValue().getClass().getName() + ">>(){}";
                }

            } else {
                paramTypeName = getReturnTypeName(paramValue);
            }

        } else {
            MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
            Class paramTypeClass = methodSignature.getMethod().getReturnType();
            paramTypeName = getReturnTypeNameByType(paramTypeClass.getName());
        }

        paramDescript.setJson(jsonParseMethod + "\"" + paramJson + "\"");
        paramDescript.setType(paramTypeName);
    }

    /**
     * 获取类名称
     *
     * @param typeName
     * @return
     */
    private static String getReturnTypeNameByType(String typeName) {
        return typeName + ".class";
    }

    /**
     * 获取类名称
     *
     * @param returnVlu
     * @return
     */
    private static String getReturnTypeName(Object returnVlu) {
        return returnVlu.getClass().getName() + ".class";
    }


    /**
     * 转义特殊字符
     *
     * @param json
     * @return
     */
    private String escapeJson(String json) {
        if (json == null) {
            return null;
        }

        return json.replace("\\", "\\\\") // 转义反斜杠
                .replace("\"", "\\\""); // 转义双引号
    }

}
