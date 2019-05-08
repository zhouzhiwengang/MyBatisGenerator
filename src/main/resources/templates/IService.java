package ${basePackage}.service;
 
import java.util.List;
 
import ${basePackage}.page.PageResult;
import ${basePackage}.domain.${bigClassName};
import ${basePackage}.query.${bigClassName}QueryObject;
 
public interface I${bigClassName}Service {
 
	void insert(${bigClassName} ${smallClassName});
 
	void delete(${bigClassName} ${smallClassName});
 
	${bigClassName} select(${bigClassName} ${smallClassName});
 
	void update(${bigClassName} ${smallClassName});
 
	PageResult query(${bigClassName}QueryObject qo);
 
	List<${bigClassName}> queryForList(${bigClassName}QueryObject qo);
 
}