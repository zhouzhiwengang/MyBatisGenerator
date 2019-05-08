package com.zzg.generator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.zzg.association.AssociationObject;
import com.zzg.converter.MySQLConverter;
import com.zzg.loader.ClassLoaderInfo;
import com.zzg.type.JavaType;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @ClassName: MyBatisGeneratorUtil
 * @Description: 基于mybatis 代码生成器
 * @author: 世纪伟图 -zzg
 * @date: 2019年5月8日 下午2:56:23
 * 
 * @param <T>
 * @Copyright: 2019 www.digipower.cn 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public class MyBatisGeneratorUtil {
	private static final int FEILDL_ENGTH = 1;// 统一定义截取字符串的长度为多少字母

	private ClassLoaderInfo loader;

	// 静态模块初始化方法
	private static Configuration config = new Configuration();
//	static {
//		try { 
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	// 构造函数
	public MyBatisGeneratorUtil(Class<?> clazz) throws Exception {
		this.loader = new ClassLoaderInfo(clazz);
		// 设置编码格式: utf-8
		config.setDefaultEncoding("utf-8");
		// 
		ClassLoader classLoader = this.getClass().getClassLoader();
		String path = classLoader.getResource("").getPath() + "templates";
		config.setDirectoryForTemplateLoading(new File(path));
	}

	public ClassLoaderInfo getLoader() {
		return loader;
	}

	public void setLoader(ClassLoaderInfo loader) {
		this.loader = loader;
	}

	// 动态数据库sql的创建
	public String createTableSQL() throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(getLoader().getBigClassName());
		LinkedHashMap<String, String> propertys = getLoader().getFieldMap();
		ListIterator<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(propertys.entrySet())
				.listIterator(propertys.size());
		sql.append(" ( ");
		while (list.hasPrevious()) {
			Map.Entry<String, String> entry = list.previous();
			if ("id".equals(entry.getKey())) {
				// 主键拼接
				sql.append("id " + "BIGINT PRIMARY KEY auto_increment ,");
			} else {
				// 非主键拼接
				MySQLConverter converter = new MySQLConverter();
				sql.append(converter.getType(entry));
			}
		}
		sql.append(" ); ");
		sql.deleteCharAt(sql.lastIndexOf(","));
		// 创建连接
		return sql.toString();
	}

	// Mybatis文件的创建
	public void createMyBatisXML(String templateFile, String targetFile) throws Exception {

		LinkedHashMap<String, String> propertys = getLoader().getFieldMap();
		LinkedHashMap<String, String> importFieldMap = getLoader().getImportFieldMap();
		List<String> genericFieldList = getLoader().getGenericFieldList();
		List<AssociationObject> association = getLoader().getAssociation();
		StringBuilder insert1 = new StringBuilder().append("insert into " + getLoader().getBigClassName() + "  (");
		StringBuilder insert2 = new StringBuilder().append("  values( ");

		StringBuilder delete = new StringBuilder()
				.append("delete from " + getLoader().getBigClassName() + " where id =#{id}");

		StringBuilder update = new StringBuilder().append("update " + getLoader().getBigClassName() + " set ");

		StringBuilder selectselectByPrimaryKey = new StringBuilder().append("select ");

		ListIterator<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(propertys.entrySet())
				.listIterator(propertys.size());
		while (list.hasPrevious()) {
			String key = list.previous().getKey();
			System.out.println(key);
			String value = propertys.get(key);
			if (JavaType.getType(value)) {
				// 插入时不需要主键
				if (!key.equals("id")) {
					genericFieldList.add(key);
					insert1.append(" " + key + " ,");
					insert2.append(" #{" + key + "} ,");
					update.append(" " + key + "=#{" + key + "}, ");
				}
				selectselectByPrimaryKey
						.append(getLoader().getSmallClassName().subSequence(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + "."
								+ key + ", ");
			} else {
				// 外键关联的相关属性
				AssociationObject associationObject = new AssociationObject();
				associationObject.setColumnPrefix(key.substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + "_");
				associationObject.setProperty(key);
				associationObject.setJavaType(value);
				association.add(associationObject);
				insert1.append(" " + key + "_id ,");
				insert2.append(" #{" + key + ".id} ,");

				update.append(" " + key + "_id=#{" + key + ".id}, ");
				int index = value.lastIndexOf(".");
				value = value.substring(index + 1);

				importFieldMap.put(key, value);

			}
		}
		int index = insert1.lastIndexOf(",");
		String str1 = insert1.substring(0, index) + " )";
		index = insert2.lastIndexOf(",");
		String str2 = insert2.substring(0, index) + " )";
		// 增加
		getLoader().setInsert(str1 + str2);
		System.out.println("增加操作  " + str1 + str2);

		// 删除
		getLoader().setDelete(delete.toString());
		System.out.println("删除操作  " + delete.toString());

		index = update.lastIndexOf(",");
		String subUpdate = update.substring(0, index);
		subUpdate = subUpdate + " where id=#{id}";
		// 更改操作
		getLoader().setUpdate(subUpdate);
		System.out.println("更改操作  " + subUpdate);
		// 判断是否有外键
		if (importFieldMap.size() <= 0) {
			index = selectselectByPrimaryKey.lastIndexOf(",");
			String str = selectselectByPrimaryKey.substring(0, index);

			// 条数的查询
			String queryForCount = "select count("
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + ".id"
					+ ") from " + getLoader().getBigClassName() + " "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH);
			getLoader().setQueryForCount(queryForCount);
			System.out.println("查询条数  " + queryForCount);

			// 查询结果集
			getLoader().setQueryListData(str + " from " + getLoader().getSmallClassName() + " "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH));
			System.out.println("查询所有  " + str + " from " + getLoader().getSmallClassName() + " "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH));
			// 分页相关
			getLoader().setLimit("limit #{currentPage},#{pageSize}");

			str = str + " from " + getLoader().getSmallClassName() + " where "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + ".id=#{id}";

			// 根据主键的查询
			getLoader().setSelectByPrimaryKey(str);
			System.out.println("主键查询  " + str);

		} else {
			Set<Entry<String, String>> entrySet = importFieldMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				selectselectByPrimaryKey.append(entry.getKey().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH)
						+ ".id as " + entry.getKey().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + "_id ,");
			}
			index = selectselectByPrimaryKey.lastIndexOf(",");
			String str = selectselectByPrimaryKey.substring(0, index);
			str = str + " from " + getLoader().getBigClassName() + "  "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH);
			for (Entry<String, String> entry : entrySet) {
				str = str + " left join " + entry.getValue() + " "
						+ entry.getKey().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + " on " + "("
						+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + "."
						+ entry.getKey() + "_id=" + entry.getKey().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH)
						+ ".id)";
			}

			String queryForCount = "select count("
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH) + ".id"
					+ ") from " + getLoader().getBigClassName() + " "
					+ getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH);
			getLoader().setQueryForCount(queryForCount);
			System.out.println("查询条数  " + queryForCount);

			// 查询结果集
			getLoader().setQueryListData(str);
			System.out.println("查询所有  " + str);
			// 分页相关
			getLoader().setLimit("limit #{start},#{pageSize}");

			str = str + " where " + getLoader().getSmallClassName().substring(0, MyBatisGeneratorUtil.FEILDL_ENGTH)
					+ ".id=#{id}";

			// 根据主键的查询
			getLoader().setSelectByPrimaryKey(str);
			System.out.println("主键查询  " + str);
		}

		Template template = config.getTemplate(templateFile);
		targetFile = MessageFormat.format(targetFile, getLoader().getBasePackage().replace(".", "/"),
				getLoader().getBigClassName());
		File file = new File(targetFile);
		File parentFile = file.getParentFile();
		// 创建文件目录
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		// 生成xml文件
		template.process(getLoader(), new FileWriter(file));
	}

	// 创建Mapper 接口文件
	public void createMapper(String templateFile, String targetFile) throws Exception {
		createFile(templateFile, targetFile);
		System.out.println("生成" + getLoader().getBigClassName() + "Mapper接口");
	}
	
	// 创建QueryObject 对象
	public void createQueryObject(String templateFile, String targetFile) throws Exception {
		createFile(templateFile, targetFile);
		System.out.println("生成" + getLoader().getBigClassName() + "QueryObject");
	}
	
	// 创建service接口
	public void createService(String templateFile, String targetFile) throws Exception {
		createFile(templateFile, targetFile);
		System.out.println("生成I" + getLoader().getBigClassName() + "Service接口");
	}
	
	// 创建serviceImpl 实现
	public void createServiceImpl(String templateFile, String targetFile) throws Exception {
		createFile(templateFile, targetFile);
		System.out.println("生成" + getLoader().getBigClassName() + "ServiceImpl实现");
	}
	
	// 其他java文件的创建
	private void createFile(String templateFile, String targetFile) throws Exception {
		// System.out.println(MessageFormat.format("你好{0},明天去{1}", "小强","打球"));
		Template template = config.getTemplate(templateFile);
		targetFile = MessageFormat.format(targetFile, getLoader().getBasePackage().replace(".", "/"),
				getLoader().getBigClassName());
		System.out.println(templateFile);
		System.out.println(targetFile);
		File file = new File(targetFile);
		// 如果文件存在则报错，不会覆盖以前的文件
		if (file.exists()) {
			throw new RuntimeException(file.getName() + "已经存在！");
		}
		File parentFile = file.getParentFile();
		// 创建文件目录
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		template.process(getLoader(), new FileWriter(file));
	}

}
