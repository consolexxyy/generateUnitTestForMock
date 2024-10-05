package xxyy.unittest.generateunittestformock.dto;

import java.util.ArrayList;
import java.util.List;

public class MethodDescript {

    private String serviceName;

    private String methodName;

    private List<ParamDescript> args;

    private ParamDescript retrun;

    private String paramJson;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public List<ParamDescript> getArgs() {
        return args;
    }

    public void setArgs(List<ParamDescript> args) {
        this.args = args;
    }

    public ParamDescript getRetrun() {
        return retrun;
    }

    public void setRetrun(ParamDescript retrun) {
        this.retrun = retrun;
    }

    public MethodDescript() {
        this.retrun = new ParamDescript();
        this.args = new ArrayList<ParamDescript>();
    }
}
