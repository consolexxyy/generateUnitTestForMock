package xxyy.unittest.generateunittestformock.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/aspect")
public class TestAspectController {

    @Resource
    private TestAspectService testAspectService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String testAspect(){
        return testAspectService.testAspect();
    }

    @RequestMapping(value = "/string", method = RequestMethod.GET)
    @ResponseBody
    public String testString(){
        return testAspectService.testString();
    }

    @RequestMapping(value = "/int", method = RequestMethod.GET)
    @ResponseBody
    public int testInt(){
        return testAspectService.testInt();
    }

    @RequestMapping(value = "/long", method = RequestMethod.GET)
    @ResponseBody
    public Long testLong(){
        return testAspectService.testLong();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List testList(){
        return testAspectService.testList();
    }

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    @ResponseBody
    public Set testSet(){
        return testAspectService.testSet();
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    @ResponseBody
    public Map testMap(){
        return testAspectService.testMap();
    }

    @RequestMapping(value = "/object", method = RequestMethod.GET)
    @ResponseBody
    public Object testObject(){
        return testAspectService.testObject();
    }


}
