package com.vwinwork.test;

import com.platform.domain.Dictionary;
import com.platform.exception.CRUDException;
import com.platform.service.DictionaryService;
import com.platform.util.test.AbstractBaseTest;

public class DictionaryTest extends AbstractBaseTest {

    public DictionaryService dictionaryService;

    @Override
    protected void onSetUpBeforeTransaction() throws Exception {
        dictionaryService = (DictionaryService) applicationContext.getBean("dictionaryService");
    }

    public void testFindAll() {
        Dictionary dic = new Dictionary();
        dic.setId("11111111111111111111111111111111");
        dic.setName("sss");
        try {
            dictionaryService.updateDictionary(dic, "T");
        } catch (CRUDException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onTearDownAfterTransaction() throws Exception {
        System.out.println("--------OVER--------");
    }
}