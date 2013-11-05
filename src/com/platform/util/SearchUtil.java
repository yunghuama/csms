package com.platform.util;

import java.util.List;

import com.platform.util.Validate;

public class SearchUtil {
	
	public static String STRING_LIKE = "string";
	public static String STRING_LIKE_LEFT = "string_left";
	public static String STRING_LIKE_RIGHT = "string_right";
	public static String STRING_EQUAL = "string_equal";
	public static String NUMBER = "number";
	public static String BETWEEN_DATE = "between_date";
	public static String BETWEEN_TIME = "between_date";
	public static String IN = "in";

	public static String getString(String[] properties, String[] types, String searchType, List<String> searchValue) {
		boolean notFirst = false;
		if(Validate.notNull(searchType)) {
			StringBuffer sb = new StringBuffer(" and (");
			if(Validate.arrayNotNull(properties)) {
				for (int i = 0, j = 0; i < properties.length; i++, j++) {
					if(((BETWEEN_DATE.equals(types[i]) || BETWEEN_TIME.equals(types[i])) && (Validate.notNull(searchValue.get(j)) || Validate.notNull(searchValue.get(j+1)))) || Validate.notNull(searchValue.get(j))) {
						if(notFirst) {
							sb.append(" ");
							sb.append(searchType);
							sb.append(" ");
						}
						notFirst = true;
						if(STRING_LIKE.equals(types[i])) {
							sb.append(properties[i]);
							sb.append(" like '%");
							sb.append(searchValue.get(j));
							sb.append("%'");
						} else if(STRING_LIKE_LEFT.equals(types[i])) {
							sb.append(properties[i]);
							sb.append(" like '%");
							sb.append(searchValue.get(j));
							sb.append("'");
						} else if(STRING_LIKE_RIGHT.equals(types[i])) {
							sb.append(properties[i]);
							sb.append(" like '");
							sb.append(searchValue.get(j));
							sb.append("%'");
						} else if(STRING_EQUAL.equals(types[i])) {
							sb.append(properties[i]);
							sb.append(" = '");
							sb.append(searchValue.get(j));
							sb.append("'");
						} else if(NUMBER.equals(types[i])) {
							sb.append(properties[i]);
							sb.append(" = ");
							sb.append(searchValue.get(j));
						} else if(BETWEEN_DATE.equals(types[i])) {
							if(Validate.notNull(searchValue.get(j)) && Validate.notNull(searchValue.get(j+1))) {
								sb.append(properties[i]);
								sb.append(" between '");
								sb.append(searchValue.get(j));
								sb.append(" 00:00:00'");
								sb.append(" and '");
								sb.append(searchValue.get(j+1));
								sb.append(" 23:59:59'");
							} else if(Validate.notNull(searchValue.get(j))) {
								sb.append(properties[i]);
								sb.append(" >= '");
								sb.append(searchValue.get(j));
								sb.append(" 00:00:00'");
							} else if(Validate.notNull(searchValue.get(j+1))) {
								sb.append(properties[i]);
								sb.append(" <= '");
								sb.append(searchValue.get(j+1));
								sb.append(" 23:59:59'");
							}
						} else if(BETWEEN_TIME.equals(types[i])) {
							if(Validate.notNull(searchValue.get(j+1))) {
								sb.append(properties[i]);
								sb.append(" between '");
								sb.append(searchValue.get(j));
								sb.append("' and '");
								sb.append(searchValue.get(j+1));
								sb.append("'");
							} else if(Validate.notNull(searchValue.get(j))) {
								sb.append(properties[i]);
								sb.append(" >= '");
								sb.append(searchValue.get(j));
								sb.append("'");
							} else if(Validate.notNull(searchValue.get(j+1))) {
								sb.append(properties[i]);
								sb.append(" <= '");
								sb.append(searchValue.get(j+1));
								sb.append("'");
							}
						} else if(IN.equals(types[i])) {
							if(Validate.notNull(searchValue.get(j))) {
								sb.append(properties[i]);
								sb.append(" in (");
								String[] spId = searchValue.get(j).split(",");
				                for (String id : spId) {
				                    if (Validate.notNull(id)) {
				                        sb.append("'");
				                        sb.append(id);
				                        sb.append("',");
				                    }
				                }
				                sb.append("''");
								sb.append(")");
							}
						}
					}
					if(BETWEEN_DATE.equals(types[i]) || BETWEEN_TIME.equals(types[i])) {
						j++;
					}
				}
			}
			sb.append(")");
			if(!notFirst) {
				sb = new StringBuffer();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
}