package qlw.mapper.ex;

import qlw.mapper.DepartmentMapper;
import qlw.model.Department;
import qlw.model.DepartmentExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

/**
 * Created by wiki on 2017/3/14.
 */
@BatisRepository
public interface DepartmentExMapper extends DepartmentMapper{
    int countByExampleEx(DepartmentExample example);
    List<Department> selectByExampleEx(DepartmentExample example);
    long insertBackId(Department department);
}
