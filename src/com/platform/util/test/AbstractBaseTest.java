package com.platform.util.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class AbstractBaseTest extends AbstractTransactionalDataSourceSpringContextTests {

    @Override
    protected String[] getConfigLocations() {
        String[] config = new String[] { "file:WebRoot/WEB-INF/applicationContext.xml" };
        return config;
    }
}