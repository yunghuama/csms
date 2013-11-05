package com.vwinwork.test;

import java.util.List;

import junit.framework.Assert;

import com.platform.exception.CRUDException;
import com.platform.service.UsersService;
import com.platform.util.test.AbstractBaseTest;

public class UsersTest extends AbstractBaseTest {

    public UsersService usersService;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        usersService = (UsersService) applicationContext.getBean("usersService");
    }

    public void testFindAll() {
        try {
            List list = usersService.findAllUsers();
            Assert.assertNotNull(list);
        } catch (CRUDException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        System.out.println("--------OVER--------");
    }

}