package xxyy.unittest.generateunittestformock.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextConig implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextConig.applicationContext = applicationContext;
    }

}
