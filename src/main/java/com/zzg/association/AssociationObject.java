package com.zzg.association;

/**
 * 
 * @ClassName:  AssociationObject   
 * @Description: 关联基础对象
 * @author: 世纪伟图 -zzg
 * @date:   2019年5月8日 下午2:32:03   
 *     
 * @Copyright: 2019 www.digipower.cn 
 * 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public class AssociationObject {
	// 实体对象属性
	private String property;
	// 前缀
	private String columnPrefix;
	// 实体对象属性对应java 类型
	private String javaType;
	
	// set 和 get 方法
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getColumnPrefix() {
		return columnPrefix;
	}
	public void setColumnPrefix(String columnPrefix) {
		this.columnPrefix = columnPrefix;
	}
	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	

}
