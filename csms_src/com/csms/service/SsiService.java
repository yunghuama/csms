package com.csms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csms.dao.SsiDAO;
import com.csms.domain.SmsRule;
import com.csms.domain.Ssi;
import com.csms.util.CalendarUtils;
import com.csms.util.SmsRuleContext;
import com.platform.service.IService;

@Service
public class SsiService implements IService {

    private SsiDAO ssiDAO;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	ssiDAO = SsiDAO.getInstance(jdbcTemplate);
    }
     

    
    /**
     * 查询列表并进行短信处理
     * @param args
     * @return
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class})
    public int listSsi(Object[] args){
    	List<Ssi> list =  ssiDAO.listSsi(args);
    	if(list!=null&&list.size()>0){
    		for(Ssi ssi : list){
    			//判断该手机号有没有规则
    			SmsRule smsRule = SmsRuleContext.getSmsRule(ssi.getSourceAddress());
    			if(smsRule!=null){
    				//判断长度是否合适
    				String content = smsRule.getContent();
    				if(content!=null){
    					//获得内容长度
    					int contentLength = content.length();
    					//获得短信长度
    					int smsLength = ssi.getSmLength();
    					//如果和小于70个字符
    					if((smsLength+contentLength)<70){
    						//判断时间是否合适
    						if(smsRule.getTimeType()!=null&&smsRule.getRuleDay()!=null){
    							boolean sendAble = CalendarUtils.sendAble(smsRule.getTimeType(), smsRule.getRuleDay());
    							if(sendAble){
    								ssi.setShortMessage(ssi.getShortMessage()+content);
    								//将该条数据保存
    								ssiDAO.saveSsiRep(ssi);
    							}
    						}
    					}
    				}
    				
    			}
    		}
    	}
    	return 0;
    }
    
    
}