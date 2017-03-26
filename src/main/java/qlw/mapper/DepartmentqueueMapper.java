package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Departmentqueue;
import qlw.model.DepartmentqueueExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface DepartmentqueueMapper {
    int countByExample(DepartmentqueueExample example);

    int deleteByExample(DepartmentqueueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Departmentqueue record);

    int insertSelective(Departmentqueue record);

    List<Departmentqueue> selectByExample(DepartmentqueueExample example);

    Departmentqueue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Departmentqueue record, @Param("example") DepartmentqueueExample example);

    int updateByExample(@Param("record") Departmentqueue record, @Param("example") DepartmentqueueExample example);

    int updateByPrimaryKeySelective(Departmentqueue record);

    int updateByPrimaryKey(Departmentqueue record);
}