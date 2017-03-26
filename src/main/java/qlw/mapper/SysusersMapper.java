package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Sysusers;
import qlw.model.SysusersExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface SysusersMapper {
    int countByExample(SysusersExample example);

    int deleteByExample(SysusersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Sysusers record);

    int insertSelective(Sysusers record);

    List<Sysusers> selectByExample(SysusersExample example);

    Sysusers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Sysusers record, @Param("example") SysusersExample example);

    int updateByExample(@Param("record") Sysusers record, @Param("example") SysusersExample example);

    int updateByPrimaryKeySelective(Sysusers record);

    int updateByPrimaryKey(Sysusers record);
}