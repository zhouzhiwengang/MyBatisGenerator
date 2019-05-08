package com.zzg.type;

/**
 * 
 * @ClassName:  JavaType   
 * @Description: Java 类型判别   
 * @author: 世纪伟图 -zzg
 * @date:   2019年5月8日 下午2:28:23   
 *     
 * @Copyright: 2019 www.digipower.cn 
 * 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public class JavaType {
	
	/**
	 * 
	 * @Title: getType   
	 * @Description:  判断类型是否为java 基础类型   
	 * @param: @param type
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
	public static boolean getType(String type){
		String javaType = "";
		if (type.contains(".")) {
			//类型的截取
			int lastIndexOf = type.lastIndexOf(".");
			javaType = type.substring(lastIndexOf + 1);
		} else {
			javaType = type;
		}
		switch (javaType) {
		case "int":
			return true;
		case "Integer":
			return true;
		case "long":
			return true;
		case "Long":
			return true;
		case "double":
			return true;
		case "Double":
			return true;
		case "String":
			return true;
		case "BigDecimal":
			return true;
		case "Boolean":
			return true;
		case "boolean":
			return true;
		case "Date":
			return true;
		default:
			return false;
		}
	}

}
