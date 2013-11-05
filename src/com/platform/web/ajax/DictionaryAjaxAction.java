package com.platform.web.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.platform.constants.StringConstant;
import com.platform.domain.Dictionary;
import com.platform.service.DictionaryService;
import com.platform.util.DateTimeHelper;
import com.platform.util.LoginBean;

@Controller
@Scope("prototype")
public class DictionaryAjaxAction {
	
	@Autowired
	private DictionaryService dictionaryService;
	private String dictId;
	private String name;
	private String superId;
	private String state;
	private String optionKey;
	private String optionText;
	
	
	/**
	 * 验证名称是否存在
	 * @return
	 * @throws Exception
	 */
	public String validateDictName() throws Exception {
		if(dictionaryService.nameExist(dictId, name, superId)) {
			state = StringConstant.FALSE;
		} else {
			state = StringConstant.TRUE;
		}
		return Action.SUCCESS;
	}
	
	/**
     * 新建页面新增联系情况分类字典项
     * superId:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx29
     * @return
     * @throws Exception
     */
    public String addLinkRecordDict() throws Exception {
        Dictionary dictionary = new Dictionary();
//        List<Dictionary> list = dictionaryService.findDictionaryBySuperId(OAStringConstant.DICTIONARY_ID_LINKRECORD_TYPE);
//        Dictionary lastDic = list.get(list.size()-1);
//        dictionary.setSuperId(OAStringConstant.DICTIONARY_ID_LINKRECORD_TYPE);
        dictionary.setName(optionText);
//        dictionary.setSortNum(lastDic.getSortNum()+1);
        dictionary.setCreateTime(DateTimeHelper.getDateTimeD());
        dictionary.setCreator(LoginBean.getLoginBean().getUser());
        dictionaryService.saveDictionary(dictionary);
        optionKey = dictionary.getId();
        
        return Action.SUCCESS;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public String getState() {
		return state;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getOptionKey() {
        return optionKey;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
	
}