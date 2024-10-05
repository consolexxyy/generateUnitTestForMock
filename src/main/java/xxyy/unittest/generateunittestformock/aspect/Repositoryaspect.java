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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xxyy.unittest.generateunittestformock.config.AspectPointJoinConig;
import xxyy.unittest.generateunittestformock.dto.MethodDescript;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Aspect
@Component
public class Repositoryaspect {


    @Resource
    private TestAspectService testAspectService;

    private String doReturn = "doReturn(";
    private String doReturnM = ", ";
    private String when = ")).when(";
    private String whenEnd = ").";
    private String end = "();";

    @PostConstruct
    void init() {
        logger.info("repositoryPath:{}", repositoryPath);
    }

    @Value("${generate.repository.path}")
    private String repositoryPath;

    Logger logger = LoggerFactory.getLogger(Repositoryaspect.class);


    @Pointcut(value = AspectPointJoinConig.POINT_CUT_METHOD)
    public void dealAfterReturning() {

    }

    @AfterReturning(pointcut = "dealAfterReturning()", returning = "returnVlu")
    public void afterReturn(JoinPoint joinPoint, Object returnVlu) {
//        logger.info("AfterReturning:进入");

        // init
        MethodDescript methodDescript = new MethodDescript();


        // 获取服务名称信息
        this.getServiceName(joinPoint, methodDescript);

        // 方法和参数信息
        this.getNethodNameAndArgs(joinPoint, methodDescript);

        // 获取返回值类型并转换为 JSON
        this.getReturn(joinPoint, returnVlu, methodDescript);

        StringBuilder result = new StringBuilder();
        result.append(doReturn);
        result.append(methodDescript.getReturnJson());
        result.append(doReturnM);
        result.append(methodDescript.getReturnType());
        result.append(when);
        result.append(methodDescript.getServiceName());
        result.append(whenEnd);
        result.append(methodDescript.getMethodName());
        result.append(end);

//        logger.info(result.toString());
        System.out.print(result.toString());
//        stackTrace();

//        logger.info("AfterReturning:结束");
    }

    /**
     *  获取方法和参数信息
     * @param joinPoint
     * @param methodDescript
     */
    private void getNethodNameAndArgs(JoinPoint joinPoint, MethodDescript methodDescript) {
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        String methodName = methodSignature.getMethod().getName();
        methodDescript.setMethodName(methodName);

        // 获取参数类型和参数值
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();

        // 打印每个参数的类型和 JSON
        for (int i = 0; i < args.length; i++) {
            String argJson = JSON.toJSONString(args[i]); // 转换成 JSON
            argJson = escapeJson(argJson); // 转义特殊字符
            System.out.print("参数类型: " + parameterTypes[i].getName() + ", 参数 JSON: " + argJson + " | ");
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
        String returnJsonRaw = JSON.toJSONString(returnVlu); // 转换成 JSON
        String returnJson = escapeJson(returnJsonRaw); // 转义特殊字符

        String returnTypeName = null;

        String jsonParseMethod = "JSON.parseObject(";
        if (returnVlu != null) {
            if (returnVlu instanceof List<?>) {
                jsonParseMethod = "JSON.parseArray(";
                if (((List<?>) returnVlu).iterator().hasNext()) {
                    Object tmp = ((List<?>) returnVlu).iterator().next();
                    returnTypeName = getReturnTypeName(tmp);
                }
            } else if (returnVlu instanceof Set<?>) {
                if (((Set<?>) returnVlu).iterator().hasNext()) {
                    Object tmp = ((Set<?>) returnVlu).iterator().next();
                    returnTypeName = "new TypeReference<Set<" + tmp.getClass().getName() + ">>(){}";
                }
            } else if (returnVlu instanceof Map<?, ?>) {
                if (((Map<?, ?>) returnVlu).entrySet().iterator().hasNext()) {
                    Map.Entry<?, ?> tmp = (Map.Entry<?, ?>) ((Map<?, ?>) returnVlu).entrySet().iterator().next();
                    returnTypeName = "new TypeReference<Map<" + tmp.getKey().getClass().getName() + ", " + tmp.getValue().getClass().getName() + ">>(){}";
                }

            } else {
                returnTypeName = getReturnTypeName(returnVlu);
            }

        }

        methodDescript.setReturnJson(jsonParseMethod + "\"" + returnJson + "\"");
        methodDescript.setReturnType(returnTypeName);
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
