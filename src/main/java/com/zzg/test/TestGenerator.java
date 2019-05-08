package com.zzg.test;

import com.zzg.generator.util.MyBatisGeneratorUtil;

public class TestGenerator {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MyBatisGeneratorUtil util = new MyBatisGeneratorUtil(BlogEntity.class);

		// 创建表
		String sql = util.createTableSQL();
		System.out.println(sql);

		// 生成xml文件
		util.createMyBatisXML( "Mapper.xml", "src/main/java/{0}/mapper/{1}Mapper.xml");

		// mapper接口
		util.createMapper("Mapper.java", "src/main/java/{0}/mapper/{1}Mapper.java");

		// 生成QueryOBject
		util.createQueryObject("QueryObject.java", "src/main/java/{0}/query/{1}QueryObject.java");

		// 生成Service接口
		util.createService("IService.java", "src/main/java/{0}/service/I{1}Service.java");

		// 生成Service实现类
		util.createServiceImpl("ServiceImpl.java", "src/main/java/{0}/service/impl/{1}ServiceImpl.java");

	}

}
