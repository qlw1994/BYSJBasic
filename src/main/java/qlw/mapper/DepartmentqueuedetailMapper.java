package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Departmentqueuedetail;
import qlw.model.DepartmentqueuedetailExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface DepartmentqueuedetailMapper {
    int countByExample(DepartmentqueuedetailExample example);

    int deleteByExample(DepartmentqueuedetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Departmentqueuedetail record);

    int insertSelective(Departmentqueuedetail record);

    List<Departmentqueuedetail> selectByExample(DepartmentqueuedetailExample example);

    Departmentqueuedetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Departmentqueuedetail record, @Param("example") DepartmentqueuedetailExample example);

    int updateByExample(@Param("record") Departmentqueuedetail record, @Param("example") DepartmentqueuedetailExample example);

    int updateByPrimaryKeySelective(Departmentqueuedetail record);

    int updateByPrimaryKey(Departmentqueuedetail record);
}