package xxyy.unittest.generateunittestformock.aspect;

import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Repositoryaspect {

    @Value("${repository.path}")
    private String repositoryPath;

    Logger logger = LoggerFactory.getLogger(Repositoryaspect.class);

    @Pointcut(value = "execution(public * xxyy.unittest.generateunittestformock.service..*.*(..)))")
    public void dealAfterReturning(){

    }

    @AfterReturning(pointcut = "dealAfterReturning()", returning = "returnVlu")
    public void afterReturn(JoinPoint joinPoint, Object returnVlu){
        logger.info("AfterReturning:成功");
        logger.info("repositoryPath:{}", repositoryPath);

        // 获取参数类型和参数值
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Object[] args = joinPoint.getArgs();

        // 打印每个参数的类型和 JSON
        for (int i = 0; i < args.length; i++) {
            String argJson = JSON.toJSONString(args[i]); // 转换成 JSON
            argJson = escapeJson(argJson); // 转义特殊字符
            System.out.print("参数类型: " + parameterTypes[i].getName() + ", 参数 JSON: " + argJson + " | ");
        }

        System.out.println();

        // 获取返回值类型并转换为 JSON
        Class<?> returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
        String returnJson = JSON.toJSONString(returnVlu); // 转换成 JSON
        returnJson = escapeJson(returnJson); // 转义特殊字符

        // 打印返回值类型和 JSON
        System.out.println("返回值类型: " + returnType.getName() + ", 返回值 JSON: " + returnJson);


        logger.info("AfterReturning:结束");
    }


    /**
     *  转义特殊字符
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
