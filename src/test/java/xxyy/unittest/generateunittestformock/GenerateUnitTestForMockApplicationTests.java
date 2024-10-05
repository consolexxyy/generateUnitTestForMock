package xxyy.unittest.generateunittestformock;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xxyy.unittest.generateunittestformock.service.TestAspectService;

import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
class GenerateUnitTestForMockApplicationTests {

    @MockBean
    private TestAspectService testAspectService;

    @Test
    void contextLoads() {
        doReturn(JSON.parseObject("\"this is a string\"", java.lang.String.class)).when(testAspectService).testString();
        doReturn(JSON.parseObject("8", java.lang.Integer.class)).when(testAspectService).testInt();
        doReturn(JSON.parseObject("16", java.lang.Long.class)).when(testAspectService).testLong();
        doReturn(JSON.parseArray("[\"1\",\"2\",\"3\"]", java.lang.String.class)).when(testAspectService).testList();
        doReturn(JSON.parseObject("[\"1\",\"2\",\"3\"]", new TypeReference<Set<String>>() {
        })).when(testAspectService).testSet();
        doReturn(JSON.parseObject("{\"1\":\"1\",\"2\":\"2\",\"3\":\"3\"}", new TypeReference<Map<java.lang.String, java.lang.String>>() {
        })).when(testAspectService).testMap();
        doReturn(JSON.parseObject("{\"address\":\"北京市\\\\东城区\\\\XX街道\\\\幸福里小区\",\"age\":20,\"birthDay\":1728139825927,\"gender\":false,\"name\":\"张三\",\"phoneNumber\":\"12312312332\"}", xxyy.unittest.generateunittestformock.dto.UserInfoDto.class)).when(testAspectService).testObject();
        testAspectService.testString();
    }

}
