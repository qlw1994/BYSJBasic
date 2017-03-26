package qlw.mapper;

import org.apache.ibatis.annotations.Param;
import qlw.model.Numbers;
import qlw.model.NumbersExample;
import qlw.util.annotation.BatisRepository;

import java.util.List;

@BatisRepository
public interface NumbersMapper {
    int countByExample(NumbersExample example);

    int deleteByExample(NumbersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Numbers record);

    int insertSelective(Numbers record);

    List<Numbers> selectByExample(NumbersExample example);

    Numbers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Numbers record, @Param("example") NumbersExample example);

    int updateByExample(@Param("record") Numbers record, @Param("example") NumbersExample example);

    int updateByPrimaryKeySelective(Numbers record);

    int updateByPrimaryKey(Numbers record);
}