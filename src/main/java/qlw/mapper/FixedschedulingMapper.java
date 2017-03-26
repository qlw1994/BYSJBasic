package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Fixedscheduling;
import qlw.model.FixedschedulingExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface FixedschedulingMapper {
    int countByExample(FixedschedulingExample example);

    int deleteByExample(FixedschedulingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Fixedscheduling record);

    int insertSelective(Fixedscheduling record);

    List<Fixedscheduling> selectByExample(FixedschedulingExample example);

    Fixedscheduling selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Fixedscheduling record, @Param("example") FixedschedulingExample example);

    int updateByExample(@Param("record") Fixedscheduling record, @Param("example") FixedschedulingExample example);

    int updateByPrimaryKeySelective(Fixedscheduling record);

    int updateByPrimaryKey(Fixedscheduling record);
}