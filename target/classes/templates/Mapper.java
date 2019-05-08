package ${basePackage}.mapper;
 
import java.util.List;
 
import ${basePackage}.domain.${bigClassName};
import ${basePackage}.query.${bigClassName}QueryObject;
 
public interface ${bigClassName}Mapper {
	
	void insert(${bigClassName} ${smallClassName});
 
	void delete(${bigClassName} ${smallClassName});
 
	void update(${bigClassName} ${smallClassName});
	
	${bigClassName} select(${bigClassName} ${smallClassName});
	
	int queryTotalCount(${bigClassName}QueryObject qo);
 
	List<${bigClassName}> queryListData(${bigClassName}QueryObject qo);
}