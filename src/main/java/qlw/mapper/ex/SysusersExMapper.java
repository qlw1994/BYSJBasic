package qlw.mapper.ex;

import qlw.mapper.SysusersMapper;
import qlw.model.Sysusers;
import qlw.util.annotation.BatisRepository;

/**
 * Created by wiki on 2017/3/3.
 */
@BatisRepository
public interface SysusersExMapper extends SysusersMapper{
    long insertBackId(Sysusers sysusers);
}
