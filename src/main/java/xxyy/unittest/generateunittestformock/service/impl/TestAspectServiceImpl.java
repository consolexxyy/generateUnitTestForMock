package xxyy.unittest.generateunittestformock.service.impl;

import org.springframework.stereotype.Service;
import xxyy.unittest.generateunittestformock.annotation.MockTestMark;
import xxyy.unittest.generateunittestformock.dto.UserInfoDto;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

import java.util.*;

@Service
public class TestAspectServiceImpl implements TestAspectService {


    @Override
    public String testAspect() {
        return "this is a test";
    }

    @Override
    public String testString() {
        return "this is a string";
    }

    @Override
    public int testInt() {
        return 8;
    }

    @Override
    public Long testLong() {
        return 16l;
    }

    @Override
    public List testList() {
        List list = new ArrayList();

        list.add("1");
        list.add("2");
        list.add("3");

        return list;
    }

    @Override
    public Set testSet() {
        Set set = new HashSet();

        set.add("1");
        set.add("2");
        set.add("3");

        return set;
    }

    @Override
    public Map testMap() {

        Map map = new HashMap();

        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");

        return map;
    }

    @MockTestMark
    @Override
    public Object testObject() {

        UserInfoDto  userInfoDto = new UserInfoDto();

        userInfoDto.setName("张三");
        userInfoDto.setAge(20);
        userInfoDto.setGender(false);
        userInfoDto.setPhoneNumber("12312312332");
        userInfoDto.setBirthDay(new Date());
        userInfoDto.setAddress("北京市\\东城区\\XX街道\\幸福里小区");

        return userInfoDto;
    }
}
