package xxyy.unittest.generateunittestformock.config;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Configuration
@ConfigurationProperties(prefix = "unittestgenerate")
public class GenerateUnitTestForMockConfig implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(GenerateUnitTestForMockConfig.class);

    private boolean power_switch;

    private String aop_path;

    private String type;

    private String filepath;

    public boolean isPower_switch() {
        return power_switch;
    }

    public void setPower_switch(boolean power_switch) {
        this.power_switch = power_switch;
    }

    public String getAop_path() {
        return aop_path;
    }

    public void setAop_path(String aop_path) {
        this.aop_path = aop_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("start init GenerateUnitTestForMockConfig...");

        if(this.isPower_switch()){
            logger.info("GenerateUnitTestForMockConfig power_switch: on");
        } else {
            logger.info("GenerateUnitTestForMockConfig power_switch: off");
            return;
        }

        if(!StringUtils.hasText(this.getType())){
            this.type = "annotation";
        }
        logger.info("GenerateUnitTestForMockConfig type: {}", this.type);

        if(!StringUtils.hasText(this.getAop_path())){
            this.aop_path = "aop_path";
        }
        logger.info("GenerateUnitTestForMockConfig aop_path: {}", this.aop_path);

        if(!StringUtils.hasText(this.getFilepath())) {
            this.filepath = System.getProperty("user.dir");
        }
        logger.info("GenerateUnitTestForMockConfig filepath: {}", this.filepath);

        logger.info("end init GenerateUnitTestForMockConfig");
    }
}
