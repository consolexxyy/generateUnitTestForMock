package xxyy.unittest.generateunittestformock.service.impl;

import org.springframework.stereotype.Service;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

@Service
public class TestAspectServiceImpl implements TestAspectService {


    @Override
    public String testAspect(String param) {
        return "this is a test";
    }
}
