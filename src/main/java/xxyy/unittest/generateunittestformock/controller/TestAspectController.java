package xxyy.unittest.generateunittestformock.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

@Controller
@RequestMapping("/test")
public class TestAspectController {

    @Resource
    private TestAspectService testAspectService;


    @RequestMapping(value = "/aspect", method = RequestMethod.GET)
    @ResponseBody
    public String testAspect(){
        return testAspectService.testAspect("param");
    }


}
