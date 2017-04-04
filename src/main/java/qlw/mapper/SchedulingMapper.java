package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Scheduling;
import qlw.model.SchedulingExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface SchedulingMapper {
    int countByExample(SchedulingExample example);

    int deleteByExample(SchedulingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Scheduling record);

    int insertSelective(Scheduling record);

    List<Scheduling> selectByExample(SchedulingExample example);

    Scheduling selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByExample(@Param("record") Scheduling record, @Param("example") SchedulingExample example);

    int updateByPrimaryKeySelective(Scheduling record);

    int updateByPrimaryKey(Scheduling record);
}