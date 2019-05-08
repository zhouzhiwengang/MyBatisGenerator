package ${basePackage}.service.impl;
 
import java.util.List;
 
import ${basePackage}.page.PageResult;
 
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${basePackage}.domain.${bigClassName};
import ${basePackage}.mapper.${bigClassName}Mapper;
import ${basePackage}.query.${bigClassName}QueryObject;
import ${basePackage}.service.I${bigClassName}Service;
 
 
@Service
public class ${bigClassName}ServiceImpl implements I${bigClassName}Service {
 
	@Autowired
	private ${bigClassName}Mapper ${smallClassName}Mapper;
 
	@Override
	public void insert(${bigClassName} ${smallClassName}) {
 
		${smallClassName}Mapper.insert(${smallClassName});
	}
	
	@Override
	public void delete(${bigClassName} ${smallClassName}){
		
		${smallClassName}Mapper.delete(${smallClassName});
	}
	
	@Override
	public void update(${bigClassName} ${smallClassName}) {
		this.${smallClassName}Mapper.update(${smallClassName});
	}
    
 
	@Override
	public ${bigClassName} select(${bigClassName} ${smallClassName}) {
		return this.${smallClassName}Mapper.select(${smallClassName});
	}
 
	@Override
	public PageResult query(${bigClassName}QueryObject qo) {
		
		int count = ${smallClassName}Mapper.queryTotalCount(qo);
		
		if (count > 0) {
			List<${bigClassName}> list = ${smallClassName}Mapper.queryListData(qo);
			return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
		}
		return PageResult.empty(qo.getPageSize());
	}
 
	@Override
	public List<${bigClassName}> queryForList(${bigClassName}QueryObject qo) {
		return ${smallClassName}Mapper.queryListData(qo);
	}
}