package xxyy.unittest.generateunittestformock.config;

import org.springframework.stereotype.Component;

@Component
public class AspectPointJoinConig {

    public final static String POINT_CUT_METHOD = "execution(public * xxyy.unittest.generateunittestformock.service..*.*(..)))";

}
