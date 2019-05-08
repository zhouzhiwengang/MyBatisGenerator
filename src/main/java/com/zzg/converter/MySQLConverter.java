package com.zzg.converter;

import java.util.Map.Entry;

/**
 * 
 * @ClassName:  MySQLConverter   
 * @Description: MySQL 数据类型转换  
 * @author: 世纪伟图 -zzg
 * @date:   2019年5月8日 下午2:27:56   
 *     
 * @Copyright: 2019 www.digipower.cn 
 * 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public class MySQLConverter implements TypeConverter {

	/**
	 * 
	 * <p>Title: getType</p>   
	 * <p>Description: </p>   
	 * @param entry
	 * @return   
	 * @see com.zzg.converter.TypeConverter#getType(java.util.Map.Entry)
	 */
	@Override
	public String getType(Entry<String, String> entry) {
		// TODO Auto-generated method stub
		String javaType = "";
		if (entry.getValue().contains(".")) {
			// 类型的截取
			int lastIndexOf = entry.getValue().lastIndexOf(".");
			javaType = entry.getValue().substring(lastIndexOf + 1);
		} else {
			javaType = entry.getValue();
		}
		
		switch (javaType) {
		 
		case "String":
			return entry.getKey() + " VARCHAR(255) ,";
 
		case "byte[]":
			return entry.getKey() + " BLOB ,";
 
		case "int":
			return entry.getKey() + " INTEGER ,";
 
		case "Integer":
			return entry.getKey() + " INTEGER ,";
 
		case "Boolean":
			return entry.getKey() + " BIT ,";
 
		case "boolean":
			return entry.getKey() + " BIT ,";
 
		case "Long":
			return entry.getKey() + " BIGINT ,";
 
		case "long":
			return entry.getKey() + " BIGINT ,";
 
		case "float":
			return entry.getKey() + " FLOAT ,";
 
		case "Float":
			return entry.getKey() + " FLOAT ,";
 
		case "double":
			return entry.getKey() + " DOUBLE ,";
 
		case "Double":
			return entry.getKey() + " DOUBLE ,";
 
		case "BigDecimal":
			return entry.getKey() + " DECIMAL ,";
 
		case "Date":
			return entry.getKey() + " DATETIME,";
 
		default:
			return entry.getKey() + "_id Long,";
		}
	}

}
