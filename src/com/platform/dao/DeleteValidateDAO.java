package com.platform.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;

import com.platform.util.ListHelper;

public class DeleteValidateDAO extends GenericDAO{
	private static DeleteValidateDAO instance;
	private JdbcTemplate jdbcTemplate;
	
	public DeleteValidateDAO(JdbcTemplate jdbcTemplate){
		super(jdbcTemplate);
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public static DeleteValidateDAO getInstance(JdbcTemplate jdbcTemplate) {
        if(instance == null) {
        	instance =  new DeleteValidateDAO(jdbcTemplate);
        }
        return instance;
    }
	
	public List<List<String>> findCannotDeleteList(List<String> idList, List<String> sqlList){
		// 声明Set集合
				Set<String> cannotDeleteData = new HashSet<String>();
				// 循环SQL常量类中删除用到的SQL集合
				for (String sql : sqlList) {
					// 以ID为参数循环查询数据
					for (String id : idList) {
						// 如果查找到数据,则将这个ID放入不能被删除的集合中
						if (queryForInt(sql, new String[]{id})>0)
							cannotDeleteData.add(id);
					}
				}
				// 返回不能被删除的List集合
				return ListHelper.getDifferent(idList, new ArrayList<String>(cannotDeleteData));
	}
}
