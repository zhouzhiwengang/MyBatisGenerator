package com.zzg.converter;

import java.util.Map.Entry;

/**
 * 
 * @ClassName:  TypeConverter   
 * @Description: 数据库类型转换公共接口 
 * @author: 世纪伟图 -zzg
 * @date:   2019年5月8日 下午2:19:47   
 *     
 * @Copyright: 2019 www.digipower.cn 
 * 注意：本内容仅限于深圳市世纪伟图科技开发有限公司内部使用，禁止用于其他的商业目的
 */
public interface TypeConverter {
	public String getType(Entry<String, String> entry);
}
