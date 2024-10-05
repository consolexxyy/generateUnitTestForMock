package xxyy.unittest.generateunittestformock.dto;

import java.io.Serializable;

public class ParamDescript implements Serializable {


    private String json;

    private String type;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParamDescript{" +
                "json='" + json + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
