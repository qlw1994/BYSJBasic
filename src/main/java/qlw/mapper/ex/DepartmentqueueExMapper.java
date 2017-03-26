package qlw.mapper.ex;

import qlw.mapper.DepartmentqueueMapper;
import qlw.model.Departmentqueue;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/22.
 */
@BatisRepository
public interface DepartmentqueueExMapper extends DepartmentqueueMapper {
    long insertBackId(Departmentqueue departmentqueue);
}
