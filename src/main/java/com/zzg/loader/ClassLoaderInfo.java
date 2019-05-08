package com.zzg.loader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.zzg.association.AssociationObject;
import com.zzg.common.BaseEntity;

/**
 * 
 * @ClassName:  ClassLoader   
 * @Description: 类加载器信息  
 * @author: 世纪伟图 -zzg
 * @date:   2019年5月8日 下午2:30:29   
 *     
 * @Copyright: 2019 www.digipower.cn 
 * 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public class ClassLoaderInfo {
	private String basePackage; // 基本包
	private String bigClassName; // 大写类名
	private String smallClassName;// 首字母小写的类名
	private LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>(); // 实体对象属性Map
	private List<String> genericFieldList = new ArrayList<>(); // 生成实体对象属性List
	private LinkedHashMap<String, String> importFieldMap = new LinkedHashMap<>(); // 导入实体对象属性Map
	private List<AssociationObject> association = new ArrayList<>(); // 关联对象List
 
	private String insert; // insert 方法
	private String delete; // delete 方法
	private String update; // update 方法
	private String selectByPrimaryKey; // 基于主键查询方法
	private String selectAll;  // 查询方法全部
	private String queryForCount; // 总数统计方法
	private String queryListData; // 查询List
	private String limit; // 分页方法
	
	// set 和   get 方法
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	public String getBigClassName() {
		return bigClassName;
	}
	public void setBigClassName(String bigClassName) {
		this.bigClassName = bigClassName;
	}
	public String getSmallClassName() {
		return smallClassName;
	}
	public void setSmallClassName(String smallClassName) {
		this.smallClassName = smallClassName;
	}
	public LinkedHashMap<String, String> getFieldMap() {
		return fieldMap;
	}
	public void setFieldMap(LinkedHashMap<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}
	public List<String> getGenericFieldList() {
		return genericFieldList;
	}
	public void setGenericFieldList(List<String> genericFieldList) {
		this.genericFieldList = genericFieldList;
	}
	public LinkedHashMap<String, String> getImportFieldMap() {
		return importFieldMap;
	}
	public void setImportFieldMap(LinkedHashMap<String, String> importFieldMap) {
		this.importFieldMap = importFieldMap;
	}
	public List<AssociationObject> getAssociation() {
		return association;
	}
	public void setAssociation(List<AssociationObject> association) {
		this.association = association;
	}
	public String getInsert() {
		return insert;
	}
	public void setInsert(String insert) {
		this.insert = insert;
	}
	public String getDelete() {
		return delete;
	}
	public void setDelete(String delete) {
		this.delete = delete;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getSelectByPrimaryKey() {
		return selectByPrimaryKey;
	}
	public void setSelectByPrimaryKey(String selectByPrimaryKey) {
		this.selectByPrimaryKey = selectByPrimaryKey;
	}
	public String getSelectAll() {
		return selectAll;
	}
	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}
	public String getQueryForCount() {
		return queryForCount;
	}
	public void setQueryForCount(String queryForCount) {
		this.queryForCount = queryForCount;
	}
	public String getQueryListData() {
		return queryListData;
	}
	public void setQueryListData(String queryListData) {
		this.queryListData = queryListData;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	// 构造方法
	public ClassLoaderInfo(Class<?> clazz) throws Exception{
		// 获取包名
		String pkgName = clazz.getPackage().getName();
		this.basePackage = pkgName.substring(0, pkgName.lastIndexOf("."));
		this.bigClassName = clazz.getSimpleName();
		this.smallClassName = this.bigClassName.substring(0, 1).toLowerCase() + this.bigClassName.substring(1);
		this.getObjectFiled(clazz);
	}
	
	public void getObjectFiled(Class<?> clazz) throws Exception {
		// 创建父类对象
		Object currentObject = clazz.getDeclaredConstructor().newInstance();
		if (currentObject instanceof BaseEntity) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				// java中的修饰码 如public 1 private 2 protect 4 static 8
				if (field.getModifiers() < 8) {
					// 集合类型的不添加
					if (!field.getType().getName().endsWith("List") && !field.getType().getName().endsWith("Map")) {
						this.fieldMap.put(field.getName(), field.getType().getName());
					}
				}
			}
			// 递归调用父类中的方法
			getObjectFiled(clazz.getSuperclass());
		}
	}

	

}
